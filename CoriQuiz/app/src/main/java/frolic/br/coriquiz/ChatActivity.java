package frolic.br.coriquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import frolic.br.coriquiz.adapter.ChatAdapter;
import frolic.br.coriquiz.interfaces.ActivityGenericsInterface;
import frolic.br.coriquiz.model.Message;

/**
 * Chat Activity to send and receive messages
 */
public class ChatActivity extends AppCompatActivity implements ActivityGenericsInterface {

    private EditText etMessage;
    private RecyclerView mRecList;
    private ChatAdapter mChatAdapter;
    private Button btnSend;

    private Socket mSocket;
    private Handler mTypingHandler = new Handler();

    private String mIdentification;
    private String mAddress;
    private int mPort;
    private List<Message> messages = new ArrayList<Message>();
    private boolean mTyping = false;

    private static final int TYPING_TIMER_LENGTH = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        loadAdware();
        
        //Get values coming from MainActivity
        getIntentValues();

        //Get the reference to the UI components
        initializeUIComponents();

        try {
            mSocket = IO.socket("http://" + mAddress + ":" + mPort);

            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectionError);
            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectionError);
            mSocket.on("login", onLogin);
            mSocket.on("new message", onNewMessage);
            mSocket.on("user joined", onUserJoined);
            mSocket.on("user left", onUserLeft);
            mSocket.on("typing", onTyping);
            mSocket.on("stop typing", onStopTyping);

            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            setResult(RESULT_CANCELED);
            finish();
        }

        attemptLogin();
    }

    private void loadAdware() {
        //Adware Login Activity
        AdView mAdView = (AdView) findViewById(R.id.adView);

        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("E112885C2D32D31690C7B60F25C89356")
                .addTestDevice("13E7A5DDF2981F979D554ED02BC571B3")
                .addTestDevice("6B95C2235F71E07117E929AE067BED28")
                .build();
        mAdView.loadAd(adRequest);
    }

    private void attemptLogin() {
        mSocket.emit("add user", mIdentification);

    }

    /**
     * Get UI components references
     */
    public void initializeUIComponents() {
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnSend = (Button) findViewById(R.id.btnSend);
        mRecList = (RecyclerView) findViewById(R.id.recyclerView);
        mRecList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecList.setLayoutManager(llm);
        mChatAdapter = new ChatAdapter(messages, mIdentification, this);
        mRecList.setAdapter(mChatAdapter);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } catch (Exception e){
            Log.e("chat application", e.getMessage());
            setResult(RESULT_CANCELED);
            finish();
        }

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mIdentification == null) { return; }
                if (!mSocket.connected()) { return; }

                if (!mTyping && !etMessage.getText().toString().equals("")) {
                    mTyping = true;
                    mSocket.emit("typing");
                }

                mTypingHandler.removeCallbacks(onTypingTimeout);
                mTypingHandler.postDelayed(onTypingTimeout, TYPING_TIMER_LENGTH);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
                mSocket.off("login", onLogin);
            }
        });
    }

    /**
     * Retrieve values passed through intent
     */
    public void getIntentValues() {
        mIdentification = getIntent().getStringExtra("identification");
        mAddress = getIntent().getStringExtra("address");
        mPort = getIntent().getIntExtra("port", 1234);
    }


    private void notifyAdapter() {
        Handler handler = new Handler(Looper.getMainLooper());

        final Runnable r = new Runnable() {
            public void run() {
                //This is crashing due to a bug on Android. See https://code.google.com/p/android/issues/detail?id=77846
                //mChatAdapter.notifyItemInserted(messages.size() - 1);
                //Using notifyDataSetChanged instead. This kills animation and performance, but...
                mChatAdapter.notifyDataSetChanged();
            }
        };

        handler.post(r);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            int numUsers;
            try {
                numUsers = data.getInt("numUsers");

                addLog(getString(R.string.message_welcome));
                addParticipantsLog(numUsers);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onConnectionError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ChatActivity.this, getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;

                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                        playSound("FacebookSound.mp3");

                        removeTyping(username);
                        addMessage(username, message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    private Emitter.Listener onUserJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;

                    try {
                        numUsers = data.getInt("numUsers");
                        username = data.getString("username");
                        playSound("FacebookSound.mp3");

                        addLog(getString(R.string.message_user_joined, username));
                        addParticipantsLog(numUsers);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;

                    try {
                        numUsers = data.getInt("numUsers");
                        username = data.getString("username");
                        playSound("FacebookSound.mp3");

                        addLog(getString(R.string.message_user_left, username));
                        addParticipantsLog(numUsers);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;

                    try {
                        username = data.getString("username");
                        addTyping(username);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    /*
    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;

                    try {
                        numUsers = data.getInt("numUsers");
                        username = data.getString("username");
                        playSound("FacebookSound.mp3");

                        addLog(getString(R.string.message_user_left), username);
                        addParticipantsLog(numUsers);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };
    */

    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;

                    try {
                        username = data.getString("username");
                        removeTyping(username);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    private void addLog(String message) {
        messages.add(new Message.Builder(Message.TYPE_LOG).message(message).build());

        notifyAdapter();

        scrollToBottom();
    }

    private void addParticipantsLog(int numUsers) {
        addLog(getResources().getQuantityString(R.plurals.message_participants, numUsers, numUsers));
    }

    private void addMessage(String username, String message) {
        messages.add(new Message.Builder(Message.TYPE_MESSAGE).username(username).message(message).build());

        notifyAdapter();
        scrollToBottom();
    }

    private void addTyping(String username) {
        messages.add(new Message.Builder(Message.TYPE_ACTION).username(username).message(username +
                " " + getString(R.string.user_action_typing)).build());
        notifyAdapter();
        scrollToBottom();
    }

    private void removeTyping(String username) {
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message message = messages.get(i);

            if(message.getType() == Message.TYPE_ACTION && message.getUsername().equals(username)) {
                messages.remove(i);
                mChatAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    private void attemptSend() {
        if(mIdentification == null) { return; }
        if(!mSocket.connected()) { return; }

        mTyping = false;

        String message = etMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            etMessage.requestFocus();
            return;
        }

        etMessage.setText("");
        addMessage(mIdentification, message);
        mSocket.emit("new message", message);
    }

    private void scrollToBottom() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecList.scrollToPosition(mChatAdapter.getItemCount());
            }
        });
    }

    private Runnable onTypingTimeout = new Runnable() {
        @Override
        public void run() {
            if(!mTyping) { return; }
            mTyping = false;

            mSocket.emit("stop typing");
        }
    };

    private void playSound(String asset) {
        MediaPlayer mp = null;

        AssetFileDescriptor afd = null;

        try {
            afd = getAssets().openFd(asset);

            mp = new MediaPlayer();
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("login", onLogin);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectionError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectionError);
        mSocket.off("new message", onNewMessage);
        mSocket.off("user joined", onUserJoined);
        mSocket.off("user left", onUserLeft);
        mSocket.off("typing", onTyping);
        mSocket.off("stop typing", onStopTyping);

    }
}
