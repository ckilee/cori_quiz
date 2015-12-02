package frolic.br.coriquiz;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONObject;

import frolic.br.coriquiz.model.QuizDAO;
import frolic.br.coriquiz.utils.ExtraNames;


public class MainActivity extends AppCompatActivity {

    private Button shareButton;
    private Button newGameButton;
    private TextView textViewName;
    private QuizDAO quizDAO;
    private String userName;
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shareButton = (Button) findViewById(R.id.button_share);
        newGameButton = (Button) findViewById(R.id.button_new_game);
        textViewName = (TextView) findViewById(R.id.textViewName);
        loginButton = (LoginButton) findViewById(R.id.button_face);
        configureViews();
        quizDAO = new QuizDAO(getApplicationContext());
        quizDAO.addDefaultValues();

        Intent i = getIntent();
        userName = i.getStringExtra(ExtraNames.NAME);
        textViewName.setText(userName);
        if(userName.equals(getString(R.string.anonymous_name)))
            loginButton.setVisibility(View.GONE);

        //Anonymous login Button
        View.OnClickListener loginButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                LoginManager.getInstance().logOut();
                finish();

            }
        };
        loginButton.setOnClickListener(loginButtonClickListener);

    }

    private void configureViews() {
        //Share Button
        View.OnClickListener shareListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String message = getString(R.string.app_name)+" - https://play.google.com/store/apps/details?id=frolic.br.coriquiz";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, getString(R.string.app_name)));

            }
        };
        shareButton.setOnClickListener(shareListener);

        //New Game Button
        View.OnClickListener newGameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,RoundActivity.class);
                intent.putExtra(ExtraNames.ROUND,1);
                intent.putExtra(ExtraNames.SCORE,0);
                startActivity(intent);

            }
        };
        newGameButton.setOnClickListener(newGameListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }




}
