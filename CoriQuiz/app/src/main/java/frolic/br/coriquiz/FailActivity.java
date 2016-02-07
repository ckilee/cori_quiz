package frolic.br.coriquiz;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.Utility;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import frolic.br.coriquiz.fragment.RankingFragment;
import frolic.br.coriquiz.fragment.RankingNotLogedFragment;
import frolic.br.coriquiz.model.RankingItem;
import frolic.br.coriquiz.model.User;
import frolic.br.coriquiz.utils.ExtraNames;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class FailActivity extends AppCompatActivity {
    private TextView failTextView;
    private CallbackManager callbackManager;
    private int score = 0;
    private Button publishScoreButton;
    private Button backButton;
    private InterstitialAd mInterstitialAd;
    private boolean hasLoadedRanking = false;
    Fragment rankingFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_fail);
        loadInterstistialAD();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configureViews();

        if(!hasLoadedRanking)
            requestScore();

        this.setTitle(R.string.wrong_answer);

        new ShowInterstitialTask().execute(500);

    }

    private void configureViews(){
        Intent i = getIntent();
        score = i.getIntExtra(ExtraNames.SCORE,0);
        failTextView = (TextView)findViewById(R.id.textViewFailMessage);
        failTextView.setText(getText(R.string.fail_message).toString().replace("%score%", Integer.toString(score)));

        publishScoreButton  = (Button)this.findViewById(R.id.buttonPublishScore);
        //Publish Score Button
        View.OnClickListener publishListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                granteePublishActionsPermission();
            }
        };
        publishScoreButton.setOnClickListener(publishListener);
        if(AccessToken.getCurrentAccessToken() == null) {
            publishScoreButton.setEnabled(false);
        }

        backButton = (Button)this.findViewById(R.id.buttonBackToMain);
        View.OnClickListener backListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        };
        backButton.setOnClickListener(backListener);
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

    private void requestScore(){
        try {
            if (AccessToken.getCurrentAccessToken() != null) {
                GraphRequestAsyncTask request1 = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/" + this.getString(R.string.app_id) + "/scores", new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        //response.getRawResponse());
                        try {
                            JSONArray jarray;
                            JSONObject jobject = response.getJSONObject();

                            jarray = jobject.optJSONArray("data");
                            ArrayList<RankingItem> rankingItemList = new ArrayList<RankingItem>();
                            RankingItem.numberOfItems = 0;
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject scoreObject = null;
                                JSONObject userObject = null;
                                try {
                                    scoreObject = jarray.getJSONObject(i);
                                    userObject = scoreObject.getJSONObject("user");
                                    RankingItem rankingItem = new RankingItem(userObject.getString("name"), scoreObject.getString("score"));
                                    rankingItemList.add(rankingItem);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //get your values

                            }

                            rankingFragment = RankingFragment.newInstance(rankingItemList);
                            getFragmentManager().beginTransaction().replace(R.id.rankingFragment, rankingFragment).commit();
                            hasLoadedRanking = true;
                        }catch(NullPointerException ne){
                            ne.printStackTrace();
                            loadRankingNotLogedFragment();
                        }
                    }

                }).executeAsync();
            } else {
                loadRankingNotLogedFragment();
            }
        }catch(IllegalStateException ie){
            ie.printStackTrace();
            hasLoadedRanking = false;
        }
    }

    private void loadRankingNotLogedFragment(){
        try {
            rankingFragment = RankingNotLogedFragment.newInstance();
            getFragmentManager().beginTransaction().add(R.id.rankingFragment, rankingFragment).commit();
            hasLoadedRanking = true;
        }catch (IllegalStateException ise){
            ise.printStackTrace();
            hasLoadedRanking = false;
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(!hasLoadedRanking){
            if(rankingFragment==null){
                requestScore();
            }else{
                getFragmentManager().beginTransaction().add(R.id.rankingFragment, rankingFragment).commit();
                hasLoadedRanking = true;
            }
        }
    }

    private void publishScore(){



        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("score", score);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GraphRequestAsyncTask request = GraphRequest.newPostRequest(AccessToken.getCurrentAccessToken(), "/" + User.id + "/scores", jsonObject, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                Log.i("FailActivity", "response:" + response.getRawResponse() );
                requestScore();
            }

        }).executeAsync();

        /*
        Bundle params = new Bundle();
        params.putString("score", "3444");

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+ User.id +"/scores",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.i("FailActivity", "After Publishing" + response.getError());
                    }
                }
        ).executeAsync();*/


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
            publishScore();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()==requestCode) {
            publishScore();
        }
    }

}
