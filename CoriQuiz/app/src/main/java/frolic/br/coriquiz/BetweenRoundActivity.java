package frolic.br.coriquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    }

    private void configureViews(){
        Intent intent = this.getIntent();
        roundNum = intent.getIntExtra("round", 1);
        scoreNum = intent.getIntExtra("score", 0);
        gotRightAnswer = intent.getBooleanExtra("gotrightanswer", true);
        helpStatus = intent.getIntExtra("helpstatus",0);
        fromEscape = intent.getBooleanExtra("fromescape",false);
        String message = this.getString(R.string.between_round_right_message);

        if(fromEscape){
            imageViewBetweenRound.setBackground(getDrawable(R.drawable.escape));
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
                intent.putExtra("round",roundNum+1);
                intent.putExtra("score",scoreNum);
                intent.putExtra("helpstatus",helpStatus);
                startActivity(intent);
                finish();
            }
        };
        nextRoundButton.setOnClickListener(nextRoundListener);

        //Share Button
        View.OnClickListener shareListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String message = "Corinthians Quiz Game - https://play.google.com/store/apps/details?id=frolic.br.coriquiz";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Corinthians Quiz Game"));

            }
        };
        shareButton.setOnClickListener(shareListener);
    }

}
