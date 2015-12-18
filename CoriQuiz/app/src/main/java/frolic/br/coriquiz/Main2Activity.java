package frolic.br.coriquiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import frolic.br.coriquiz.model.QuizDAO;
import frolic.br.coriquiz.model.User;
import frolic.br.coriquiz.utils.ExtraNames;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button chatButton;
    private ImageButton round1Button;
    private ImageButton round2Button;
    private ImageButton round3Button;
    private ImageButton round4Button;
    private ImageButton round5Button;
    private TextView textViewName;
    private QuizDAO quizDAO;
    private String userName;
    private CallbackManager callbackManager;
    private TextView profileNameTextView;
    private TextView profileEmailTextView;
    private ImageView profilePictureImageView;
    private static final int CHAT_REQUEST_CODE = 0;
    private final static int CHAT_CONNECTION_ERROR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);

        FacebookSdk.sdkInitialize(getApplicationContext());
        chatButton = (Button) findViewById(R.id.button_chat);
        round1Button = (ImageButton) findViewById(R.id.imageButtonround1);
        round2Button = (ImageButton) findViewById(R.id.imageButtonround2);
        round3Button = (ImageButton) findViewById(R.id.imageButtonround3);
        round4Button = (ImageButton) findViewById(R.id.imageButtonround4);
        round5Button = (ImageButton) findViewById(R.id.imageButtonround5);
        profileNameTextView = (TextView) navHeader.findViewById(R.id.textViewProfileName);
        profileEmailTextView = (TextView) navHeader.findViewById(R.id.textViewProfileEmail);
        profilePictureImageView = (ImageView) navHeader.findViewById(R.id.imageViewProfilePicture);
        if(User.name!=null){
            profileNameTextView.setText(User.name);
        }
        if(User.email!=null){
            profileEmailTextView.setText(User.email);
        }
        profilePictureImageView.setImageResource(R.drawable.cori_logo);
        new GetFacebookImageProfileTask().execute(User.pictureUrl);


        configureViews();
        quizDAO = new QuizDAO(getApplicationContext());
        quizDAO.addDefaultValues();

        userName = User.name;

        this.setTitle(R.string.choose_round);


    }

    private void configureViews() {

        //Chat Button
        View.OnClickListener chatListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                attemptToConnect();

            }
        };
        chatButton.setOnClickListener(chatListener);

        //round 1 Button
        View.OnClickListener round1Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                QuizDAO.resetNotInString();
                Intent intent = new Intent(Main2Activity.this,RoundActivity.class);
                intent.putExtra(ExtraNames.ROUND, 1);
                intent.putExtra(ExtraNames.SCORE,0);
                startActivity(intent);

            }
        };
        round1Button.setOnClickListener(round1Listener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main2, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            Toast.makeText(getApplicationContext(),R.string.have_logged_out,Toast.LENGTH_LONG).show();
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(Main2Activity.this,LoginActivity.class);
            startActivity(intent);
            finish();

        }
        else if(id==R.id.nav_share){
            String message = getString(R.string.app_name)+" - https://play.google.com/store/apps/details?id=frolic.br.coriquiz";
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);

            startActivity(Intent.createChooser(share, getString(R.string.app_name)));
        }
        else if(id==R.id.nav_ranking){
            Intent intent = new Intent(Main2Activity.this,RankingActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHAT_REQUEST_CODE && resultCode == CHAT_CONNECTION_ERROR){
            Toast.makeText(this, getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getFacebookImage(){
        if(User.pictureUrl!=null){

        }
    }

    public static Bitmap getFacebookProfilePicture(String urlString){
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException ne){
            ne.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }

    private class GetFacebookImageProfileTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            String urlPath;
            if(urls!=null && urls.length>0)
                urlPath = urls[0];
            else
                urlPath = null;
            return getFacebookProfilePicture(urls[0]);
        }

        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap!=null)
                profilePictureImageView.setImageBitmap(bitmap);
        }
    }

    private void attemptToConnect() {
        if(User.name==null){
            Toast.makeText(getApplicationContext(),R.string.must_be_loged_to_enter_in_chat,Toast.LENGTH_LONG).show();
        }else {
            Intent connectIntent = new Intent(this, ChatActivity.class);
            connectIntent.putExtra("identification", User.name);
            connectIntent.putExtra("address", "nodejs-ckilee.rhcloud.com");
            connectIntent.putExtra("port", 8000);

            //startActivityForResult(connectIntent, CHAT_REQUEST_CODE);
            startActivity(connectIntent);
        }
    }
}
