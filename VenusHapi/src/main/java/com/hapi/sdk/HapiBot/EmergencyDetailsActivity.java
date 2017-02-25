package com.hapi.sdk.HapiBot;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

public class EmergencyDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emergency_configuration);

        SharedPreferences sharedPref =  getSharedPreferences("com.hapi.botuserpreference",MODE_PRIVATE);


//        EditText nameView = (EditText)findViewById(R.id.fragproconftxtfullname);
//        nameView.setText( sharedPref.getString("userName", ""));

        EditText emNameView = (EditText)findViewById(R.id.emegconftxtfullname);
        emNameView.setText( sharedPref.getString("emerName", ""));

        EditText emphoneView = (EditText)findViewById(R.id.emegconftxtphone);
        emphoneView.setText( sharedPref.getString("emerPhone", ""));



//
//        EditText htView = (EditText)findViewById(R.id.fragproconftxtheight);
//        htView.setText( sharedPref.getString("userHeight", ""));
//
//        EditText wtView = (EditText)findViewById(R.id.fragproconftxtweight);
//        wtView.setText( sharedPref.getString("userWeight", ""));


    }

}
