package frolic.br.coriquiz;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import frolic.br.coriquiz.utils.ExtraNames;

public class WinActivity extends AppCompatActivity {
    private TextView winMessage;
    private InterstitialAd mInterstitialAd;
    private int scoreNum = 0;
    private Button backToMainButton;
    private Button seeScoreButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        this.setTitle(R.string.round_finished);

        configureViews();

        loadInterstistialAD();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        new ShowInterstitialTask().execute(500);
    }

    private void configureViews() {
        Intent intent = this.getIntent();
        scoreNum = intent.getIntExtra(ExtraNames.SCORE, 0);

        winMessage = (TextView) findViewById(R.id.textViewWinMessage);
        String message = getString(R.string.win_message);
        message = message.replace("%score%",Integer.toString(scoreNum));
        winMessage.setText(message);

        backToMainButton  = (Button)this.findViewById(R.id.buttonBackToMain);
        //Back To Main Button
        View.OnClickListener backToMainListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        };
        backToMainButton.setOnClickListener(backToMainListener);

        seeScoreButton = (Button)this.findViewById(R.id.buttonSeeRanking);
        View.OnClickListener seeRankingListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startRanking();
            }
        };
        seeScoreButton.setOnClickListener(seeRankingListener);

    }

    private void startRanking() {
        Intent intent = new Intent(WinActivity.this,RankingActivity.class);
        startActivity(intent);
    }

    private class ShowInterstitialTask extends AsyncTask<Integer,Void,Void> {

        @Override
        protected Void doInBackground(Integer... params){
            try {
                Thread.sleep(params[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            displayInterstitial();
        }
    }

    private void loadInterstistialAD(){
        try {
            mInterstitialAd = Main2Activity.getInstance().getInterstitialAd();
        } catch(NullPointerException ne){
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(this.getString(R.string.interstitial_ad_unit_id));
            AdRequest adRequestInterstitialAd = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("E112885C2D32D31690C7B60F25C89356")
                    .addTestDevice("13E7A5DDF2981F979D554ED02BC571B3")
                    .addTestDevice("6B95C2235F71E07117E929AE067BED28")
                    .build();


            mInterstitialAd.loadAd(adRequestInterstitialAd);
        }
    }

    private void displayInterstitial(){

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


}
