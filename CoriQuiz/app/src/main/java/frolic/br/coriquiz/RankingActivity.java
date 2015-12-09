package frolic.br.coriquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import frolic.br.coriquiz.fragment.RankingFragment;
import frolic.br.coriquiz.fragment.RankingNotLogedFragment;
import frolic.br.coriquiz.model.RankingItem;
import frolic.br.coriquiz.model.User;

public class RankingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestScore();
    }

    private void requestScore(){
        if(AccessToken.getCurrentAccessToken() != null){
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

                    getFragmentManager().beginTransaction().add(R.id.rankingFragmentRankingActivity, rankingFragment).commit();
                }

            }).executeAsync();
        }else{
            RankingNotLogedFragment rankingNotLogedFragment = RankingNotLogedFragment.newInstance();
            getFragmentManager().beginTransaction().add(R.id.rankingFragmentRankingActivity, rankingNotLogedFragment).commit();
        }

    }

}
