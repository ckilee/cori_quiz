package frolic.br.coriquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import frolic.br.coriquiz.model.QuizDAO;
import frolic.br.coriquiz.model.User;
import frolic.br.coriquiz.utils.ExtraNames;

public class LoginActivity extends Activity {
    private CallbackManager callbackManager;
    private Button anonymousButton;
    private LoginButton loginButton;
    private QuizDAO quizDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);
        User.name = this.getString(R.string.anonymous_name);
        quizDAO = new QuizDAO(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        anonymousButton = (Button)findViewById(R.id.buttonLoginAnonymous);

        if(AccessToken.getCurrentAccessToken() != null){
            getFromSharedPreferences();
            Intent i = new Intent(LoginActivity.this, Main2Activity.class);
            startActivity(i);
            finish();
        }

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));
        onFbLogin();

        //Anonymous login Button
        View.OnClickListener anonymousButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),R.string.login_as_anonymous_message,Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, Main2Activity.class);
                startActivity(i);

            }
        };
        anonymousButton.setOnClickListener(anonymousButtonClickListener);
    }

    // Private method to handle Facebook login and callback
    private void onFbLogin()
    {
        callbackManager = CallbackManager.Factory.create();

        // Set permissions
        //LoginManager.getInstance().setReadPermissions(Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        final AccessToken accessToken = loginResult.getAccessToken();
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,picture");

                        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject userJson, GraphResponse graphResponse) {
                                User.id = userJson.optString("id");
                                User.name = userJson.optString("name");
                                User.email = userJson.optString("email");
                                addToSharedPreferences(User.name, User.email, User.id, User.pictureUrl);

                                if (userJson.has("picture")) {

                                    try {
                                        User.pictureUrl = userJson.getJSONObject("picture").getJSONObject("data").getString("url");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //User.picture = getFacebookProfilePicture(User.pictureUrl);

                                }
                                //quizDAO.addUserIfNotExist();
                                Log.i("LoginActivity", "onSucess");
                                Intent i = new Intent(LoginActivity.this, Main2Activity.class);
                                startActivity(i);
                                finish();
                            }
                        });
                        request.setParameters(parameters);

                        GraphRequestAsyncTask requestAssyncTask = new GraphRequestAsyncTask(request);
                        requestAssyncTask.execute();

                    }

                    @Override
                    public void onCancel() {
                        Log.i("LoginActivity", "onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.i("LoginActivity", "onError");
                    }
                });
    }

    public void addToSharedPreferences(String name, String email, String id, String pictureUrl){
        SharedPreferences sharedPreferences = getSharedPreferences(ExtraNames.MY_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ExtraNames.USER_NAME_PREFS,name);
        editor.putString(ExtraNames.USER_EMAIL_PREFS,email);
        editor.putString(ExtraNames.USER_ID_PREFS,id);
        editor.putString(ExtraNames.USER_PICTURE_PREFS,pictureUrl);
        editor.commit();
    }

    public void getFromSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(ExtraNames.MY_PREFS,Context.MODE_PRIVATE);
        User.email = sharedPreferences.getString(ExtraNames.USER_EMAIL_PREFS,"");
        User.name = sharedPreferences.getString(ExtraNames.USER_NAME_PREFS,"");
        User.id = sharedPreferences.getString(ExtraNames.USER_ID_PREFS,"");
        User.pictureUrl= sharedPreferences.getString(ExtraNames.USER_PICTURE_PREFS,"");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
