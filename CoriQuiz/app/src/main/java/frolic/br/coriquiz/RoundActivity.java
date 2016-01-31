package frolic.br.coriquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import frolic.br.coriquiz.model.Question;
import frolic.br.coriquiz.model.QuizDAO;
import frolic.br.coriquiz.model.User;
import frolic.br.coriquiz.utils.Constants;
import frolic.br.coriquiz.utils.ExtraNames;
import frolic.br.coriquiz.utils.Utils;

public class RoundActivity extends AppCompatActivity {
    private QuizDAO quizDAO;
    private TextView questionTV;
    private TextView roundNuberTV;
    private TextView scoreTV;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private RadioButton answer4;
    private RadioButton answer5;
    private Button answerButton;
    private Button cheeringButton;
    private Button coachButton;
    private Button escapeButton;
    private Question question;
    private int helpStatus = 0;
    private int rightAnswer = 0;
    private int roundNum = 1;
    private int scoreNum = 0;
    private boolean gotRightAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
        loadAdware();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        questionTV = (TextView) findViewById(R.id.text_view_question);
        roundNuberTV = (TextView) findViewById(R.id.textViewRoundNumber);
        scoreTV = (TextView) findViewById(R.id.textViewScore);
        answer1 = (RadioButton) findViewById(R.id.rbQuestion1);
        answer2 = (RadioButton) findViewById(R.id.rbQuestion2);
        answer3 = (RadioButton) findViewById(R.id.rbQuestion3);
        answer4 = (RadioButton) findViewById(R.id.rbQuestion4);
        answer5 = (RadioButton) findViewById(R.id.rbQuestion5);
        answerButton = (Button) findViewById(R.id.button_answer);
        cheeringButton = (Button) findViewById(R.id.buttonCheering);
        coachButton = (Button) findViewById(R.id.buttonCoach);
        escapeButton = (Button) findViewById(R.id.buttonEscape);
        quizDAO = new QuizDAO(getApplicationContext());
        question = quizDAO.getRandonQuestion();
        configureViews();

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

    private void configureViews(){
        questionTV.setText(question.getQuestion().toUpperCase());
        answer1.setText(question.getAnswer1().toUpperCase());
        answer2.setText(question.getAnswer2().toUpperCase());
        answer3.setText(question.getAnswer3().toUpperCase());
        answer4.setText(question.getAnswer4().toUpperCase());
        answer5.setText(question.getAnswer5().toUpperCase());
        rightAnswer = question.getRightAnwser();
        Intent intent = this.getIntent();
        roundNum = intent.getIntExtra(ExtraNames.ROUND, 1);
        scoreNum = intent.getIntExtra(ExtraNames.SCORE, 0);
        helpStatus = intent.getIntExtra(ExtraNames.HELP_STATUS, 0);
        String round = roundNum+"/"+ Constants.MAX_ROUND_NUM+" ";
        roundNuberTV.setText(round);

        disableHelp(helpStatus);

        scoreTV.setText(Integer.toString(scoreNum));

        //Answer Button
        View.OnClickListener answerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                calculateScore();
                if(gotRightAnswer) {
                    proceedToNewActivity();
                } else{
                    Intent intent = new Intent(RoundActivity.this, FailActivity.class);
                    intent.putExtra(ExtraNames.ROUND, roundNum);
                    intent.putExtra(ExtraNames.SCORE, scoreNum);
                    intent.putExtra(ExtraNames.GOT_RIGHT_ANSWER, gotRightAnswer);
                    intent.putExtra(ExtraNames.HELP_STATUS, helpStatus);
                    startActivity(intent);
                }
                finish();
            }
        };
        answerButton.setOnClickListener(answerListener);

        //Cheering Button
        View.OnClickListener cheeringListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                applyCheeringHelp();

            }
        };
        cheeringButton.setOnClickListener(cheeringListener);

        //Coach Button
        View.OnClickListener coachListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                applyCoachHelp();
            }
        };
        coachButton.setOnClickListener(coachListener);

        //Escape Button
        View.OnClickListener escapeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),R.string.using_escape_help,Toast.LENGTH_LONG).show();
                helpStatus++;
                proceedToNewActivityFromEscape();
                finish();
            }
        };
        escapeButton.setOnClickListener(escapeListener);


    }

    private void disableHelp(int helpStatus){
        if(helpStatus==1||helpStatus==3||helpStatus==5||helpStatus==7){
            escapeButton.setEnabled(false);
            escapeButton.setBackgroundResource(R.drawable.escape_icon_disabled);
        }
        if(helpStatus==2||helpStatus==3||helpStatus==6||helpStatus==7){
            coachButton.setEnabled(false);
            coachButton.setBackgroundResource(R.drawable.coach_disabled);
        }
        if(helpStatus==4||helpStatus==5||helpStatus==6||helpStatus==7){
            cheeringButton.setEnabled(false);
            cheeringButton.setBackgroundResource(R.drawable.cheering_disabled);
        }
    }

    private void calculateScore(){
        gotRightAnswer = false;
        if(rightAnswer==1 && answer1.isChecked()||rightAnswer==2 && answer2.isChecked()||rightAnswer==3 && answer3.isChecked()||rightAnswer==4 && answer4.isChecked()||rightAnswer==5 && answer5.isChecked()) {
            scoreNum = scoreNum + roundNum * 100;

            gotRightAnswer = true;
        }

    }

    private void applyCoachHelp(){
        int nroOfRemoved = 0;
        int alreadyDisabledAnswer = 0;
        while(nroOfRemoved<2){
            int randInt = Utils.randInt(1,5);
            if(randInt==rightAnswer || alreadyDisabledAnswer == randInt)
                continue;

            if(randInt==1)

            if(randInt==1)
                answer1.setEnabled(false);
            if(randInt==2)
                answer2.setEnabled(false);
            if(randInt==3)
                answer3.setEnabled(false);
            if(randInt==4)
                answer4.setEnabled(false);
            if(randInt==5)
                answer5.setEnabled(false);
            alreadyDisabledAnswer = randInt;
            nroOfRemoved++;
        }
        helpStatus = helpStatus+2;
        coachButton.setEnabled(false);
        cheeringButton.setEnabled(false);
        coachButton.setBackgroundResource(R.drawable.coach_disabled);
        cheeringButton.setBackgroundResource(R.drawable.cheering_disabled);
        Toast.makeText(getApplicationContext(),R.string.using_coach_help,Toast.LENGTH_LONG).show();
    }

    private void applyCheeringHelp(){
        boolean removedQuestion = false;
        while(!removedQuestion){
            int randInt = Utils.randInt(1,5);
            if(randInt==rightAnswer)
                continue;

            if(randInt==1)
                answer1.setEnabled(false);
            if(randInt==2)
                answer2.setEnabled(false);
            if(randInt==3)
                answer3.setEnabled(false);
            if(randInt==4)
                answer4.setEnabled(false);
            if(randInt==5)
                answer5.setEnabled(false);
            removedQuestion = true;
        }
        Toast.makeText(getApplicationContext(),R.string.using_cheering_help,Toast.LENGTH_LONG).show();
        coachButton.setEnabled(false);
        cheeringButton.setEnabled(false);
        coachButton.setBackgroundResource(R.drawable.coach_disabled);
        cheeringButton.setBackgroundResource(R.drawable.cheering_disabled);
        helpStatus = helpStatus+4;

    }

    private void proceedToNewActivityFromEscape(){
        if( roundNum != Constants.MAX_ROUND_NUM) {
            Intent intent = new Intent(RoundActivity.this, BetweenRoundActivity.class);
            intent.putExtra(ExtraNames.ROUND, roundNum);
            intent.putExtra(ExtraNames.SCORE, scoreNum);
            intent.putExtra(ExtraNames.GOT_RIGHT_ANSWER, gotRightAnswer);
            intent.putExtra(ExtraNames.HELP_STATUS, helpStatus);
            intent.putExtra(ExtraNames.FROM_ESCAPE, true);
            intent.putExtra(ExtraNames.CUR_QUESTION,question.getQuestion());
            startActivity(intent);
        } else{
            updateScoreOnSharedPreferences(scoreNum);
            incLevelOnSharedPreferences();
            Intent intent = new Intent(RoundActivity.this, WinActivity.class);
            intent.putExtra(ExtraNames.ROUND, roundNum);
            intent.putExtra(ExtraNames.SCORE, scoreNum);
            startActivity(intent);
        }
    }

    private void proceedToNewActivity(){
        if( roundNum != Constants.MAX_ROUND_NUM) {
            Intent intent = new Intent(RoundActivity.this, BetweenRoundActivity.class);
            intent.putExtra(ExtraNames.ROUND, roundNum);
            intent.putExtra(ExtraNames.SCORE, scoreNum);
            intent.putExtra(ExtraNames.GOT_RIGHT_ANSWER, gotRightAnswer);
            intent.putExtra(ExtraNames.HELP_STATUS, helpStatus);
            intent.putExtra(ExtraNames.CUR_QUESTION,question.getQuestion());
            startActivity(intent);
        } else{
            updateScoreOnSharedPreferences(scoreNum);
            incLevelOnSharedPreferences();
            Intent intent = new Intent(RoundActivity.this, WinActivity.class);
            intent.putExtra(ExtraNames.ROUND, roundNum);
            intent.putExtra(ExtraNames.SCORE, scoreNum);
            startActivity(intent);
        }
    }

    public void updateScoreOnSharedPreferences(int newScore){
        SharedPreferences sharedPreferences = getSharedPreferences(ExtraNames.MY_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ExtraNames.USER_SCORE_PREFS,newScore);
        editor.commit();
    }

    public void incLevelOnSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(ExtraNames.MY_PREFS,Context.MODE_PRIVATE);
        int curLevel = sharedPreferences.getInt(ExtraNames.USER_LEVEL_PREFS, 0);
        curLevel++;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ExtraNames.USER_LEVEL_PREFS,curLevel);
        editor.commit();


    }




}
