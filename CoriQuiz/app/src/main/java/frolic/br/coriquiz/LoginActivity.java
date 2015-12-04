package frolic.br.coriquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import org.json.JSONObject;

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

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        onFbLogin();

        //Anonymous login Button
        View.OnClickListener anonymousButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),R.string.login_as_anonymous_message,Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
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

                        GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject userJson, GraphResponse graphResponse) {
                                User.fbTokenRequest = accessToken;
                                User.id = userJson.optString("id");
                                User.name = userJson.optString("name");
                                //quizDAO.addUserIfNotExist();
                                Log.i("LoginActivity", "onSucess");
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                        }).executeAsync();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
