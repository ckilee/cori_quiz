package frolic.br.coriquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import frolic.br.coriquiz.model.Question;
import frolic.br.coriquiz.model.QuizDAO;

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
    private Question question;
    private int rightAnswer = 0;
    private int roundNum = 1;
    private int scoreNum = 0;
    private boolean gotRightAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
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
        quizDAO = new QuizDAO(getApplicationContext());
        question = quizDAO.getRandonQuestion();
        configureViews();

    }

    private void configureViews(){
        questionTV.setText(question.getQuestion());
        answer1.setText(question.getAnswer1());
        answer2.setText(question.getAnswer2());
        answer3.setText(question.getAnswer3());
        answer4.setText(question.getAnswer4());
        answer5.setText(question.getAnswer5());
        rightAnswer = question.getRightAnwser();
        Intent intent = this.getIntent();
        roundNum = intent.getIntExtra("round", 1);
        scoreNum = intent.getIntExtra("score",0);
        String round = roundNum+"/10";
        roundNuberTV.setText(round);

        scoreTV.setText(Integer.toString(scoreNum));

        //Answer Button
        View.OnClickListener answerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                calculateScore();
                Intent intent = new Intent(RoundActivity.this,BetweenRoundActivity.class);
                intent.putExtra("round", roundNum);
                intent.putExtra("score",scoreNum);
                intent.putExtra("gotrightanswer",gotRightAnswer);
                startActivity(intent);
                finish();
            }
        };
        answerButton.setOnClickListener(answerListener);


    }

    private void calculateScore(){
        gotRightAnswer = false;
        if(rightAnswer==1 && answer1.isChecked()||rightAnswer==2 && answer2.isChecked()||rightAnswer==3 && answer3.isChecked()||rightAnswer==4 && answer4.isChecked()||rightAnswer==5 && answer5.isChecked()) {
            scoreNum = scoreNum + roundNum * 100;
            gotRightAnswer = true;
        }

    }



}
