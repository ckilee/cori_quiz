package frolic.br.coriquiz;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class WinActivity extends AppCompatActivity {
    private TextView winMessage;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        loadInterstistialAD();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        winMessage = (TextView) findViewById(R.id.textViewWinMessage);
        winMessage.setText(getText(R.string.win_message));

        new ShowInterstitialTask().execute(500);
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
