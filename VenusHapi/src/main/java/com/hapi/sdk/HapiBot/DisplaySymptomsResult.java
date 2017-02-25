package com.hapi.sdk.HapiBot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import data.model.Condition;

public class DisplaySymptomsResult extends AppCompatActivity {
private static final int MAX_NUMBER_OF_PROBABILITIES=3;
    RadioGroup radioGroup;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int count=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        radioGroup = (RadioGroup)findViewById(R.id.answersGrp);
        Intent intent=getIntent();
        List<String> message=intent.getStringArrayListExtra(DisplayQuestionsActivity.EXTRA_MESSAGE);
        TextView textView=(TextView) findViewById(R.id.txtAnswer);
        textView.setText("Hey!!This is what I have found for what you're suffering from:");
        for(String condition:message){
            radioButton=new RadioButton(getApplicationContext());
            radioButton.setText(condition);
            radioGroup.addView(radioButton);
        }
    }

    public void onOKBtnClicked(View view){
        Intent intent=new Intent(DisplaySymptomsResult.this,DashboardLandingActivity.class);
        startActivity(intent);
    }
    public void onCancelBtnClicked(View view){
        Intent intent=new Intent(DisplaySymptomsResult.this,DashboardLandingActivity.class);
        startActivity(intent);
    }
}
