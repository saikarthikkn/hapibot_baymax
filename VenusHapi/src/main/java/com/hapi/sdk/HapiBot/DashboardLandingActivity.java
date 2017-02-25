package com.hapi.sdk.HapiBot;

import com.hapi.sdk.Stormpath;
import com.hapi.sdk.StormpathCallback;
import com.hapi.sdk.models.Account;
import com.hapi.sdk.models.StormpathError;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

public class DashboardLandingActivity extends AppCompatActivity {
    FloatingActionButton plus,call,speak;
    Animation plus_open, plus_close,plusclockwise,plusanticlockwise;
    boolean isopen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_landing);

        final TextView welcomeTextView = (TextView) findViewById(R.id.tv_welcome);
        final TextView profileTextView = (TextView) findViewById(R.id.tv_profile);
        final EditText accessTokenEditText = (EditText) findViewById(R.id.input_access_token);
        final EditText refreshTokenEditText = (EditText) findViewById(R.id.input_refresh_token);
        plus=(FloatingActionButton)findViewById(R.id.plus);
        call=(FloatingActionButton)findViewById(R.id.call);
        speak=(FloatingActionButton)findViewById(R.id.speak);
        plus_open= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.floatbtn_open);
        plus_close= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.floatbtn_close);
        plusclockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        plusanticlockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);
        plus.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v)
            {
                if(isopen) {
                    call.startAnimation(plus_close);
                    speak.startAnimation(plus_close);
                    plus.startAnimation(plusanticlockwise);
                    call.setClickable(false);
                    speak.setClickable(false);

                    isopen=false;
                }
                else
                {
                    call.startAnimation(plus_open);
                    speak.startAnimation(plus_open);
                    plus.startAnimation(plusclockwise);
                    call.setClickable(true);
                    speak.setClickable(true);
                    call.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Uri number = Uri.parse("tel:9740604400");
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                            startActivity(callIntent);
                        }}
                    );

                    speak.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Uri number = Uri.parse("sms:9740604400");
                            Intent intent = new Intent( Intent.ACTION_VIEW, number);
                            intent.putExtra( "sms_body", "Hello" );
                            startActivity(intent);
                        }}
                    );
                    isopen=true;
                }
            }
        });

        Stormpath.getAccount(new StormpathCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                String profileInfoText = "Email: " + account.getEmail() + "\n" +
                        "Username: " + account.getUsername(); //+ "\n" +
                       // "Href: " + account.getHref();

                profileTextView.setText(profileInfoText);
                welcomeTextView.setText(getTimeFromAndroid()+ ", " + account.getGivenName());

                accessTokenEditText.setText("Hope You are sleeping Enough!");
              //  refreshTokenEditText.setText(Stormpath.getRefreshToken());
            }

            @Override
            public void onFailure(StormpathError error) {
                Toast.makeText(DashboardLandingActivity.this, error.message(), Toast.LENGTH_LONG).show();
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
        if(hours>=1 || hours<=12){
            // Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
            return "Good Morning !!";
        }else if(hours>=12 || hours<=16){
            //Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
            return "Good Afternoon !!";
        }else if(hours>=16 || hours<=21){
            //  Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
            return "Good Evening !!";
        }else if(hours>=21 || hours<=24){
            // Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
            return "Good Night !!";
        }
        else
        { return "Good Morning !!";}
    }
}
