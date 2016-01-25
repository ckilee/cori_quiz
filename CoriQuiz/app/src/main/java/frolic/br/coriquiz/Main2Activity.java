package frolic.br.coriquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import frolic.br.coriquiz.model.QuizDAO;
import frolic.br.coriquiz.model.User;
import frolic.br.coriquiz.utils.ExtraNames;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
    private static final int ROUND_REQUEST_CODE = 1;
    private final static int CHAT_CONNECTION_ERROR = 1;
    private int curScore = 0;
    private int curLevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializing();

    }

    private void initializing(){
        setContentView(R.layout.activity_main2);
        loadAdware();
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

        curScore = getScoreFromSharedPreferences();
        curLevel = getLevelSharedPreferences();

        setGameLevel();

        this.setTitle(R.string.choose_round);

    }

    private void loadAdware() {
        //Adware Login Activity
        AdView mAdView = (AdView) findViewById(R.id.adView);

        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("E112885C2D32D31690C7B60F25C89356")
                .addTestDevice("13E7A5DDF2981F979D554ED02BC571B3")
                .addTestDevice("6B95C2235F71E07117E929AE067BED28")
                .build();
        mAdView.loadAd(adRequest);
    }

    private void setGameLevel() {
        if(curLevel==0){
            round1Button.setEnabled(true);
            round2Button.setBackgroundResource(R.drawable.rodada_bloqueada);
            round2Button.setEnabled(false);
            round3Button.setBackgroundResource(R.drawable.rodada_bloqueada);
            round3Button.setEnabled(false);
            round4Button.setBackgroundResource(R.drawable.rodada_bloqueada);
            round4Button.setEnabled(false);
            round5Button.setBackgroundResource(R.drawable.rodada_bloqueada);
            round5Button.setEnabled(false);
        }else if(curLevel==1){
            round1Button.setEnabled(false);
            round1Button.setBackgroundResource(R.drawable.rodada_completa1);
            round2Button.setEnabled(true);
            round3Button.setBackgroundResource(R.drawable.rodada_bloqueada);
            round3Button.setEnabled(false);
            round4Button.setBackgroundResource(R.drawable.rodada_bloqueada);
            round4Button.setEnabled(false);
            round5Button.setBackgroundResource(R.drawable.rodada_bloqueada);
            round5Button.setEnabled(false);
        }
        else if(curLevel==2){
            round1Button.setEnabled(false);
            round1Button.setBackgroundResource(R.drawable.rodada_completa1);
            round2Button.setEnabled(false);
            round2Button.setBackgroundResource(R.drawable.rodada_completa2);
            round3Button.setEnabled(true);
            round4Button.setBackgroundResource(R.drawable.rodada_bloqueada);
            round4Button.setEnabled(false);
            round5Button.setBackgroundResource(R.drawable.rodada_bloqueada);
            round5Button.setEnabled(false);
        }
        else if(curLevel==3){
            round1Button.setEnabled(false);
            round1Button.setBackgroundResource(R.drawable.rodada_completa1);
            round2Button.setEnabled(false);
            round2Button.setBackgroundResource(R.drawable.rodada_completa2);
            round3Button.setEnabled(false);
            round3Button.setBackgroundResource(R.drawable.rodada_completa3);
            round4Button.setEnabled(true);
            round5Button.setEnabled(false);
            round5Button.setBackgroundResource(R.drawable.rodada_bloqueada);
        }
        else if(curLevel==4){
            round1Button.setEnabled(false);
            round1Button.setBackgroundResource(R.drawable.rodada_completa1);
            round2Button.setEnabled(false);
            round2Button.setBackgroundResource(R.drawable.rodada_completa2);
            round3Button.setEnabled(false);
            round3Button.setBackgroundResource(R.drawable.rodada_completa3);
            round4Button.setEnabled(false);
            round4Button.setBackgroundResource(R.drawable.rodada_completa4);
            round5Button.setEnabled(true);

        }
    }

    private void configureViews() {


        //round Button
        View.OnClickListener round1Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                QuizDAO.resetNotInString();
                Intent intent = new Intent(Main2Activity.this,RoundActivity.class);
                intent.putExtra(ExtraNames.ROUND, 1);
                intent.putExtra(ExtraNames.SCORE, curScore);
                startActivityForResult(intent,ROUND_REQUEST_CODE);
            }
        };
        round1Button.setOnClickListener(round1Listener);
        round2Button.setOnClickListener(round1Listener);
        round3Button.setOnClickListener(round1Listener);
        round4Button.setOnClickListener(round1Listener);
        round5Button.setOnClickListener(round1Listener);
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
        else if(id==R.id.nav_reset_score){
            SharedPreferences sharedPreferences = getSharedPreferences(ExtraNames.MY_PREFS,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ExtraNames.USER_LEVEL_PREFS);
            editor.remove(ExtraNames.USER_SCORE_PREFS);
            editor.commit();
            Toast.makeText(getApplicationContext(),R.string.score_has_been_reset,Toast.LENGTH_LONG).show();
            initializing();
        }
        else if(id==R.id.nav_chat){
            attemptToConnect();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHAT_REQUEST_CODE) {
            if (resultCode == CHAT_CONNECTION_ERROR) {
                Toast.makeText(this, getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == ROUND_REQUEST_CODE ){
            curScore = getScoreFromSharedPreferences();
            curLevel = getLevelSharedPreferences();
            setGameLevel();
        }
        else {
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

            startActivityForResult(connectIntent, CHAT_REQUEST_CODE);
            //startActivity(connectIntent);
        }
    }

    public int getScoreFromSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(ExtraNames.MY_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(ExtraNames.USER_SCORE_PREFS,0);
    }

    public int getLevelSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(ExtraNames.MY_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(ExtraNames.USER_LEVEL_PREFS,0);
    }
}
