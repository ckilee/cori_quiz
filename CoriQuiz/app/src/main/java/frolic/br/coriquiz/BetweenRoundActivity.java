package frolic.br.coriquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import frolic.br.coriquiz.model.User;
import frolic.br.coriquiz.utils.Constants;
import frolic.br.coriquiz.utils.ExtraNames;
import frolic.br.coriquiz.utils.Utils;

public class BetweenRoundActivity extends AppCompatActivity {
    private TextView messageTextView;
    private int roundNum = 1;
    private int scoreNum = 0;
    private int helpStatus = 0;
    private boolean gotRightAnswer = true;
    private boolean fromEscape = false;
    private Button shareButton;
    private Button nextRoundButton;
    private ImageView imageViewBetweenRound;
    private String curQuestion = "";
    private int curMaxRound = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_between_round);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences(ExtraNames.MY_PREFS, Context.MODE_PRIVATE);
        curMaxRound = Utils.getCurrentMaxRound(sharedPreferences.getInt(ExtraNames.CURRENT_LEVEL, 0));

        messageTextView = (TextView)findViewById(R.id.textViewMessage);
        shareButton = (Button)findViewById(R.id.buttonShare);
        nextRoundButton = (Button)findViewById(R.id.buttonNext);
        imageViewBetweenRound = (ImageView)findViewById(R.id.imageViewBetweenRound);
        configureViews();

        if(gotRightAnswer){
            this.setTitle(R.string.right_answer_title);
        }else{
            this.setTitle(R.string.escape_title);
        }
    }

    private void configureViews(){
        Intent intent = this.getIntent();
        roundNum = intent.getIntExtra(ExtraNames.ROUND, 1);
        scoreNum = intent.getIntExtra(ExtraNames.SCORE, 0);
        curQuestion = intent.getStringExtra(ExtraNames.CUR_QUESTION);
        gotRightAnswer = intent.getBooleanExtra(ExtraNames.GOT_RIGHT_ANSWER, true);
        helpStatus = intent.getIntExtra(ExtraNames.HELP_STATUS,0);
        fromEscape = intent.getBooleanExtra(ExtraNames.FROM_ESCAPE,false);
        String message = this.getString(R.string.between_round_right_message);

        if(fromEscape){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageViewBetweenRound.setBackground(getDrawable(R.drawable.escape));
            }
            message = this.getString(R.string.using_escape_help);
        }

        message = message.replace("%round%",Integer.toString(roundNum));
        message = message.replace("%missing%",Integer.toString(curMaxRound - roundNum));
        message = message.replace("%score%",Integer.toString(scoreNum));
        messageTextView.setText(message);

        //New Game Button
        View.OnClickListener nextRoundListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BetweenRoundActivity.this,RoundActivity.class);
                intent.putExtra(ExtraNames.ROUND,roundNum+1);
                intent.putExtra(ExtraNames.SCORE,scoreNum);
                intent.putExtra(ExtraNames.HELP_STATUS,helpStatus);
                startActivity(intent);
                finish();
            }
        };
        nextRoundButton.setOnClickListener(nextRoundListener);

        //Share Button
        View.OnClickListener shareListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                granteePublishActionsPermission();
            }
        };
        shareButton.setOnClickListener(shareListener);

        if(AccessToken.getCurrentAccessToken() == null){
            //Share Button
            View.OnClickListener cannotShareListener = new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Toast.makeText(BetweenRoundActivity.this,R.string.must_be_loged_to_publish,Toast.LENGTH_LONG).show();
                }
            };
            shareButton.setOnClickListener(cannotShareListener);
        }
    }

    private void publishShare(){
        JSONObject jsonObject = new JSONObject();
        try {
            int messageId = 0;
            if(fromEscape){
                messageId = R.string.share_scape_question_in_between_round;
            }else{
                messageId = R.string.share_hit_question_in_between_round;
            }
            jsonObject.put("name", "Acertou a resposta no Corinthians Quiz Game!");
            jsonObject.put("caption", "Corinthians Quiz Game");
            jsonObject.put("description", User.name+" " +this.getString(messageId).replace("%question%",curQuestion).replace("%pontos%",Integer.toString(scoreNum)));
            jsonObject.put("link", "https://play.google.com/store/apps/details?id=frolic.br.coriquiz");
            jsonObject.put("picture", "http://ckilee.esy.es/uploads/CorinthiansQuizGame.png");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GraphRequestAsyncTask request = GraphRequest.newPostRequest(AccessToken.getCurrentAccessToken(), "/" + User.id + "/feed", jsonObject, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                String rawResponse = "";
                if(response.getRawResponse()!=null){
                    rawResponse = response.getRawResponse();
                }
                Log.i("BetweenRoundActivity", rawResponse);
                shareButton.setEnabled(false);
                Toast.makeText(getApplicationContext(), R.string.have_shared, Toast.LENGTH_LONG).show();
            }

        }).executeAsync();

    }

    private void granteePublishActionsPermission(){
        Set<String> permissions = AccessToken.getCurrentAccessToken()
                .getPermissions();

        final List<String> PUBLISH_PERMISSIONS = Arrays
                .asList("publish_actions");
        if (!permissions.containsAll(PUBLISH_PERMISSIONS)) {

            // pendingPublishReauthorization = true;
            LoginManager.getInstance().logInWithPublishPermissions(this,
                    PUBLISH_PERMISSIONS);

        }else{
            publishShare();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()==requestCode) {
            publishShare();
        }
    }

}
