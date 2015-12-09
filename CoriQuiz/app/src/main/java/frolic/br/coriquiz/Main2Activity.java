package frolic.br.coriquiz;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import frolic.br.coriquiz.model.QuizDAO;
import frolic.br.coriquiz.model.User;
import frolic.br.coriquiz.utils.ExtraNames;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button shareButton;
    private Button newGameButton;
    private Button rankingButton;
    private TextView textViewName;
    private QuizDAO quizDAO;
    private String userName;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView profileNameTextView;
    private TextView profileEmailTextView;
    private ImageView profilePictureImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);

        FacebookSdk.sdkInitialize(getApplicationContext());
        shareButton = (Button) findViewById(R.id.button_share);
        newGameButton = (Button) findViewById(R.id.button_new_game);
        rankingButton = (Button) findViewById(R.id.button_ranking);
        textViewName = (TextView) findViewById(R.id.textViewName);
        profileNameTextView = (TextView) navHeader.findViewById(R.id.textViewProfileName);
        profileEmailTextView = (TextView) navHeader.findViewById(R.id.textViewProfileEmail);
        profilePictureImageView = (ImageView) navHeader.findViewById(R.id.imageViewProfilePicture);
        if(User.name!=null){
            profileNameTextView.setText(User.name);
        }
        if(User.email!=null){
            profileEmailTextView.setText(User.email);
        }
        if(User.picture!=null){
            profilePictureImageView.setImageBitmap(User.picture);
        }

        configureViews();
        quizDAO = new QuizDAO(getApplicationContext());
        quizDAO.addDefaultValues();

        userName = User.name;
        textViewName.setText(userName);


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
                Intent intent = new Intent(Main2Activity.this,RoundActivity.class);
                intent.putExtra(ExtraNames.ROUND, 1);
                intent.putExtra(ExtraNames.SCORE,0);
                startActivity(intent);

            }
        };
        newGameButton.setOnClickListener(newGameListener);

        //Ranking Button
        View.OnClickListener rankingListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Main2Activity.this,RankingActivity.class);
                startActivity(intent);

            }
        };
        rankingButton.setOnClickListener(rankingListener);
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
        getMenuInflater().inflate(R.menu.main2, menu);
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
        /*
        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
