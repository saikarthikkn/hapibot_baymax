package com.hapi.sdk.HapiBot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.hapi.sdk.HapiBot.R;

public class PersonalDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_configuration);

        SharedPreferences sharedPref =  getSharedPreferences("com.hapi.botuserpreference",MODE_PRIVATE);


        EditText nameView = (EditText)findViewById(R.id.fragproconftxtfullname);
        nameView.setText( sharedPref.getString("userName", ""));




        EditText ageView = (EditText)findViewById(R.id.fragproconftxtage);
        ageView.setText( sharedPref.getString("userAge", ""));

        Spinner spinView = (Spinner)findViewById(R.id.fragproconfgender);
        if(sharedPref.getString("userGender", "Male").equals("Male")) {
            spinView.setSelection(0);
        }else{
            spinView.setSelection(1);
        }


        EditText htView = (EditText)findViewById(R.id.fragproconftxtheight);
        htView.setText( sharedPref.getString("userHeight", ""));

        EditText wtView = (EditText)findViewById(R.id.fragproconftxtweight);
        wtView.setText( sharedPref.getString("userWeight", ""));


    }

    private void navigateToProfile() {
        startActivity(new Intent(this, ProfileConfiguration.class));
        finish();
    }



}
