package com.hapi.sdk.HapiBot;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class TalkToMeActivity extends AppCompatActivity {
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    static String getUserVoiceInput = "";
    ArrayList<String> keywords = new ArrayList<>();
    HashMap<String, String> h1;

    public HashMap<String, String> populateKeywords()
    {
        h1 = new HashMap<>();

        h1.put("s_13","stomach");
        h1.put("s_13","abdominal");
        h1.put("s_1550","Ankle bruising");
        h1.put("s_1637","Wrist bruising");
        h1.put("s_1633","Elbow bruising");
        h1.put("s_1640","Thumb bruising");
        h1.put("s_582","anxiety");
        h1.put("s_582","anxious");
        h1.put("s_142","ulcer");
        h1.put("s_1190","Backache");
        h1.put("s_843","Behavioral changes");
        h1.put("s_111","gums");
        h1.put("s_241","Blisters");
        h1.put("s_749","Blood pressure");
        h1.put("s_81","Chills");
        h1.put("s_1456","Chorea");
        h1.put("s_1094","throat");
        h1.put("s_47","earache");
        h1.put("s_610","tonsils");
        h1.put("s_329","constipation");
        h1.put("s_102","Cough");
        h1.put("s_370","Dizziness");
        h1.put("s_131","Drowsiness");
        h1.put("s_241","skin");
        h1.put("s_121","hair loss");
        h1.put("s_121","hair fall");
        h1.put("s_493","Eye pain");
        h1.put("s_1172","fever");
        h1.put("s_21","headache");
        h1.put("s_131","Drowsiness");
        h1.put("s_543","hypertension");
        h1.put("s_7","Infertility");
        h1.put("s_44","Joint pain");
        h1.put("s_117","muscle pain");
        h1.put("s_156","Nausea");
        h1.put("s_1483","Neck pain");

        Log.d("Populated","");
        return h1;
    }

    public ArrayList<String> getSymptomsSpoken() {
        return keywords;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_to_me);
        populateKeywords();
    }

    public void onTalkBtnClicked(View view){
        setUserVoiceInput();
    }

    public void setUserVoiceInput()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak out to Doctor :P");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList matches=null;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {

            matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            getUserVoiceInput = matches.get(0).toString();
        }
        getKeywords();
    }


    public String getUserVoiceInput() {
        return getUserVoiceInput;
    }

    // Stop UserVoiceInput Activity
    // Get the results in a "getUserVoiceInput"


    public void getKeywords()
    {
        String str = getUserVoiceInput();

        str = str.toLowerCase();
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();

        int count=0;
        int s=h1.size();
        for(HashMap.Entry<String, String> entry : h1.entrySet()) {
            if(str.toLowerCase().contains(entry.getValue().toLowerCase()))
            {
                keywords.add(entry.getKey());
            }
        }

        String allKeywords = keywords.size() + "\n";
        for(String str1 : keywords)
        {
            allKeywords += str1 + "\n";
        }
        Toast.makeText(getApplicationContext(),allKeywords, Toast.LENGTH_SHORT).show();

    }
    public void onOkBtnClicked(View view){
        Intent intent = new Intent(TalkToMeActivity.this, DisplayQuestionsActivity.class);
        intent.putStringArrayListExtra("keywords",getSymptomsSpoken());
        startActivity(intent);

    }

}
