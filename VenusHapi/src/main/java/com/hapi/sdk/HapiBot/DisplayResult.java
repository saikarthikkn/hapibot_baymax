package com.hapi.sdk.HapiBot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        Intent intent=getIntent();
        String message=intent.getStringExtra(DisplayQuestionsActivity.EXTRA_MESSAGE);
        TextView textView=(TextView) findViewById(R.id.txtAnswer);
        textView.setText(message);
    }
}
