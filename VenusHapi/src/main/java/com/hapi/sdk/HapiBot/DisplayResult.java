package com.hapi.sdk.HapiBot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import data.model.Condition;

public class DisplayResult extends AppCompatActivity {
private static final int MAX_NUMBER_OF_PROBABILITIES=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int count=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        Intent intent=getIntent();
        List<String> message=intent.getStringArrayListExtra(DisplayQuestionsActivity.EXTRA_MESSAGE);
        TextView textView=(TextView) findViewById(R.id.txtAnswer);
        textView.setText("Hey!!This is what I have found for what you're suffering from: \n");
        for(String condition:message){
            textView.setText(condition);
            textView.setText("\n");
        }
    }
}
