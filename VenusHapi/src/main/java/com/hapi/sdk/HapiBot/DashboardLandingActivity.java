package com.hapi.sdk.HapiBot;

import com.hapi.sdk.Stormpath;
import com.hapi.sdk.StormpathCallback;
import com.hapi.sdk.models.Account;
import com.hapi.sdk.models.StormpathError;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_landing);

        final TextView welcomeTextView = (TextView) findViewById(R.id.tv_welcome);
        final TextView profileTextView = (TextView) findViewById(R.id.tv_profile);
        final EditText accessTokenEditText = (EditText) findViewById(R.id.input_access_token);
        final EditText refreshTokenEditText = (EditText) findViewById(R.id.input_refresh_token);

        Stormpath.getAccount(new StormpathCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                String profileInfoText = "Email: " + account.getEmail() + "\n" +
                        "Username: " + account.getUsername() + "\n" +
                        "Href: " + account.getHref();

                profileTextView.setText(profileInfoText);
                welcomeTextView.setText("Welcome, " + account.getGivenName());

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
}
