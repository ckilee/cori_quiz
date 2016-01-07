package frolic.br.coriquiz;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
import com.facebook.internal.Utility;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

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

public class FailActivity extends AppCompatActivity {
    private TextView failTextView;
    private CallbackManager callbackManager;
    private int score = 0;
    private Button publishScoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        requestScore();

        this.setTitle(R.string.wrong_answer);

    }

    private void requestScore(){
        if(AccessToken.getCurrentAccessToken() != null) {
            GraphRequestAsyncTask request1 = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/" + this.getString(R.string.app_id) + "/scores", new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    //response.getRawResponse());

                    JSONArray jarray;
                    JSONObject jobject = response.getJSONObject();
                    jarray = jobject.optJSONArray("data");
                    ArrayList<RankingItem> rankingItemList = new ArrayList<RankingItem>();
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

                    RankingFragment rankingFragment = RankingFragment.newInstance(rankingItemList);
                    getFragmentManager().beginTransaction().replace(R.id.rankingFragment, rankingFragment).commit();
                }

            }).executeAsync();
        }else{
            RankingNotLogedFragment rankingNotLogedFragment = RankingNotLogedFragment.newInstance();
            getFragmentManager().beginTransaction().add(R.id.rankingFragment, rankingNotLogedFragment).commit();
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

        publishScore();
    }

}
