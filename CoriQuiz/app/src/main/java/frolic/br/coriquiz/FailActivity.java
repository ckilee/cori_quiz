package frolic.br.coriquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import frolic.br.coriquiz.model.RankingItem;
import frolic.br.coriquiz.model.User;
import frolic.br.coriquiz.utils.ExtraNames;

public class FailActivity extends AppCompatActivity {
    private TextView failTextView;
    private CallbackManager callbackManager;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        failTextView = (TextView)findViewById(R.id.textViewFailMessage);
        failTextView.setText(getText(R.string.fail_message));

        Intent i = getIntent();
        score = i.getIntExtra(ExtraNames.SCORE,0);

        granteePublishActionsPermission();




    }

    private void requestScore(){
        GraphRequestAsyncTask request1 = GraphRequest.newGraphPathRequest(User.fbTokenRequest, "/" + this.getString(R.string.app_id) + "/scores", new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                //response.getRawResponse());
                Log.i("FailActivity","TESTE"+response.getRawResponse());
                JSONArray jarray;
                JSONObject jobject = response.getJSONObject();
                jarray = jobject.optJSONArray("data");
                ArrayList<RankingItem> rankingItemList = new ArrayList<RankingItem>();
                for(int i = 0; i < jarray.length(); i++){
                    JSONObject scoreObject = null;
                    JSONObject userObject = null;
                    try {
                        scoreObject = jarray.getJSONObject(i);
                        userObject = scoreObject.getJSONObject("user");
                        RankingItem rankingItem = new RankingItem(userObject.getString("name"),scoreObject.getString("score"));
                        rankingItemList.add(rankingItem);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //get your values

                }

                RankingFragment rankingFragment = RankingFragment.newInstance(rankingItemList);

                getFragmentManager().beginTransaction().add(R.id.rankingFragment, rankingFragment).commit();
            }

        }).executeAsync();
    }

    private void publishScore(){



        JSONObject jsonObject = new JSONObject();
        try {
          /*  jsonObject.put("name", "teste2");
            jsonObject.put("caption", "caption");
            jsonObject.put("description", "teste");
            jsonObject.put("link", "www.google.comb.br");
            jsonObject.put("picture", "https://www.google.com.br/images/nav_logo242.png");*/
            jsonObject.put("score", score);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GraphRequestAsyncTask request = GraphRequest.newPostRequest(User.fbTokenRequest, "/" + User.id + "/scores", jsonObject, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                Log.i("FailActivity","ERRO:"+response.getRawResponse()+" e "+response.getError());
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
