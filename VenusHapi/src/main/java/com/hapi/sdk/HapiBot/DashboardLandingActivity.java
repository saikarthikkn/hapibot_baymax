package com.hapi.sdk.HapiBot;

import com.hapi.sdk.Stormpath;
import com.hapi.sdk.StormpathCallback;
import com.hapi.sdk.models.Account;
import com.hapi.sdk.models.StormpathError;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/*
Main Landing activity for day 2 day flow.  Will come from Login page.

Will have
1. greeting based of time of the day
2. Hapi bot gif
2. Statement based on sleep data
3. current health statement
4. Floating icons for mic, etc ....

 */

public class DashboardLandingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    FloatingActionButton plus, btnAttachments, btnTalkToMe, btnCamera,btnUploadFile;

    Animation plus_open, plus_close,plusclockwise,plusanticlockwise;
    boolean isopen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start of Nvigation
        //setContentView(R.layout.activity_dash_landing);
        setContentView(R.layout.activity_dash_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.dashtoolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //End of Navigation

        final TextView welcomeTextView = (TextView) findViewById(R.id.tv_welcome);
       // final TextView profileTextView = (TextView) findViewById(R.id.tv_profile);
        final EditText accessTokenEditText = (EditText) findViewById(R.id.input_access_token);
       // final EditText refreshTokenEditText = (EditText) findViewById(R.id.input_refresh_token);
        plus=(FloatingActionButton)findViewById(R.id.plus);
        btnAttachments =(FloatingActionButton)findViewById(R.id.call);
        btnTalkToMe =(FloatingActionButton)findViewById(R.id.speak);
        btnCamera =(FloatingActionButton)findViewById(R.id.camera);
        btnUploadFile =(FloatingActionButton)findViewById(R.id.file);
        plus_open= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.floatbtn_open);
        plus_close= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.floatbtn_close);
        plusclockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        plusanticlockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);
        plus.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v)
            {
                if(isopen) {
                    btnAttachments.startAnimation(plus_close);
                    btnTalkToMe.startAnimation(plus_close);
                    btnCamera.startAnimation(plus_close);
                    btnUploadFile.startAnimation(plus_close);
                    plus.startAnimation(plusanticlockwise);
                    btnAttachments.setClickable(false);
                    btnTalkToMe.setClickable(false);
                    btnCamera.setClickable(false);
                    btnUploadFile.setClickable(false);
                    isopen=false;
                }
                else
                {
                    btnAttachments.startAnimation(plus_open);
                    btnTalkToMe.startAnimation(plus_open);
                    btnUploadFile.startAnimation(plus_open);
                    btnCamera.startAnimation(plus_open);
                    plus.startAnimation(plusclockwise);
                    btnAttachments.setClickable(true);
                    btnTalkToMe.setClickable(true);
                    btnCamera.setClickable(true);
                    btnUploadFile.setClickable(true);
                    btnAttachments.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Uri number = Uri.parse("tel:9740604400");
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                            startActivity(callIntent);
                        }}
                    );

                    btnTalkToMe.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
//                            Uri number = Uri.parse("sms:9740604400");
//                            Intent intent=new Intent(getApplication(),TalkToMeActivity.class);
////                            startActivity(new Intent(this));
////                            Intent intent = new Intent( Intent.ACTION_VIEW, number);
////                            intent.putExtra( "sms_body", "Hello" );
//                            startActivity(intent);
//                            Intent intent=new Intent(this,TalkToMeActivity.class);
//                            startActivity(intent);
                            callTalkToMeACtivity();
                        }}
                    );
                    btnCamera.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                               callCameraActivity();
                        }
                    });
                    isopen=true;
                }
            }
        });

        Stormpath.getAccount(new StormpathCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                String profileInfoText = //"Email: " + account.getEmail() + "\n" +
                        "Username: " + account.getUsername(); //+ "\n" +
                       // "Href: " + account.getHref();

                //profileTextView.setText(profileInfoText);
                welcomeTextView.setText(getTimeFromAndroid()+ ", " + account.getGivenName());

                accessTokenEditText.setText("Hope You are sleeping Enough!");
              //  refreshTokenEditText.setText(Stormpath.getRefreshToken());
            }

            @Override
            public void onFailure(StormpathError error) {
                Toast.makeText(DashboardLandingActivity.this, error.message(), Toast.LENGTH_LONG).show();
                onLogoutClicked();
            }
        });

        findViewById(R.id.logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutClicked();
            }
        });


        findViewById(R.id.button_refresh_token).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stormpath.refreshAccessToken(new StormpathCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ((EditText) findViewById(R.id.input_access_token)).setText(Stormpath.getAccessToken());
                    }

                    @Override
                    public void onFailure(StormpathError error) {

                    }
                });
            }
        });
    }

    protected void onLogoutClicked() {
        Stormpath.logout();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
    private String getTimeFromAndroid() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        if(hours>=1 && hours<=12){
            // Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
            return "Good Morning";
        }else if(hours>=12 && hours<=16){
            //Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
            return "Good Afternoon";
        }else if(hours>=16 && hours<=21){
            //  Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
            return "Good Evening";
        }else if(hours>=21 && hours<=24){
            // Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
            return "Good Night";
        }
        else
        { return "Good Morning";}
    }

    private void callTalkToMeACtivity(){
        Intent intent=new Intent(DashboardLandingActivity.this,TalkToMeActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_navigation, menu);
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

        if (id == R.id.nav_camera) {
            startActivity(new Intent(this, PersonalDetailsActivity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this, EmergencyDetailsActivity.class));


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    private void callCameraActivity(){
        Intent intent=new Intent(DashboardLandingActivity.this,AndroidCamera2API.class);
        startActivity(intent);
    }
    private void callUploadFileActivity(){
        Intent intent=new Intent(DashboardLandingActivity.this,TalkToMeActivity.class);
        startActivity(intent);
    }
}
