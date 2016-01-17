package frolic.br.coriquiz;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import frolic.br.coriquiz.model.User;
import frolic.br.coriquiz.utils.ExtraNames;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_between_round);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        message = message.replace("%missing%",Integer.toString(10 - roundNum));
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
                publishShare();
            }
        };
        shareButton.setOnClickListener(shareListener);

        if(AccessToken.getCurrentAccessToken() == null){
            shareButton.setEnabled(false);
        }
    }

    private void publishShare(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "Acertou a resposta no Corinthians Quiz Game!");
            jsonObject.put("caption", "Corinthians Quiz Game");
            jsonObject.put("description", User.name+ this.getString(R.string.share_hit_question_in_between_round).replace("%question%",curQuestion).replace("%pontos%",Integer.toString(scoreNum)));
            jsonObject.put("link", "https://play.google.com/store/apps/details?id=frolic.br.coriquiz");
            jsonObject.put("picture", "http://ckilee.esy.es/uploads/CorinthiansQuizGame.png");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GraphRequestAsyncTask request = GraphRequest.newPostRequest(AccessToken.getCurrentAccessToken(), "/" + User.id + "/feed", jsonObject, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                Log.i("BetweenRoundActivity",response.getRawResponse());
                shareButton.setEnabled(false);
                Toast.makeText(getApplicationContext(),R.string.have_shared,Toast.LENGTH_LONG).show();
            }

        }).executeAsync();

    }

}
