package com.hapi.sdk.HapiBot;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TalkToMeActivity extends AppCompatActivity {
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    static String getUserVoiceInput = "";
    ArrayList<String> keywords = new ArrayList<>();
    HashMap<String, String> h1;
//    HashMap<String, String> h2;

    LinearLayout ll ;


    protected ArrayList<String> getfinalList()
    {
        ArrayList<CheckBox> tempCheck = new ArrayList<>();
        tempCheck.addAll(allcheckboxAbove);
//        tempCheck.addAll(allcheckboxBelow);
        ArrayList<String> finalListSymptoms = new ArrayList<>();
        HashMap<String ,String> finalSelectedHash = new HashMap<>();

        for(CheckBox ch : tempCheck)
        {
            if(ch.isChecked())
            {
                for(Map.Entry<String, String> tempmap : h1.entrySet())
                {
                    String a = ch.getText().toString().toLowerCase();

                    if(ch.getText().toString().compareToIgnoreCase(tempmap.getValue().toString())==0)
                    {
                        finalSelectedHash.put(tempmap.getKey(),tempmap.getValue());
                    }
                }
            }
        }

        for(Map.Entry<String, String> tempmap : finalSelectedHash.entrySet()) {
            finalListSymptoms.add(tempmap.getKey());
        }
        return finalListSymptoms;
    }

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

        return h1;
    }

    public ArrayList<String> getSymptomsSpoken() {
        return getfinalList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_to_me);
        ll = (LinearLayout)findViewById(R.id.linearLayout2);


        populateKeywords();
        //createCheckBoxesUnchecked(h1);
    }

    ScrollView detectedKeywordsScroller;
    public void onTalkBtnClicked(View view)
    {
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

        createCheckBoxesChecked(getKeywords());
    }

    HashMap<String, String> map;

    List<CheckBox> allcheckboxAbove = new ArrayList<>();
    //    List<CheckBox> allcheckboxBelow = new ArrayList<>();
    protected void createCheckBoxesChecked(HashMap<String, String> mp)
    {
        ll.removeAllViews();
        map=mp;
        detectedKeywordsScroller = (ScrollView) findViewById(R.id.scrollView1);
        detectedKeywordsScroller.refreshDrawableState();
//        Toast.makeText(getApplicationContext(),mp.size()+"",Toast.LENGTH_LONG).show();

        allcheckboxAbove.clear();
        for(HashMap.Entry<String, String> m : mp.entrySet())
        {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(m.getValue());
            cb.setChecked(true);
            ll.addView(cb);
            allcheckboxAbove.add(cb);
        }
    }

//    protected void createCheckBoxesUnchecked(HashMap<String, String> mp)
//    {
//        map=mp;
//        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout4);
//        ll.removeAllViews();
//        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView2);
//        scrollView.refreshDrawableState();
//        for(HashMap.Entry<String, String> m : map.entrySet())
//        {
//            CheckBox cb = new CheckBox(getApplicationContext());
//            cb.setText(m.getValue());
//            ll.addView(cb);
//            allcheckboxBelow.add(cb);
//        }
//
//    }


    public String getUserVoiceInput()
    {
        return getUserVoiceInput;
    }

    // Stop UserVoiceInput Activity
    // Get the results in a "getUserVoiceInput"


    public HashMap<String, String> getKeywords()
    {
        String str = getUserVoiceInput();

        str = str.toLowerCase();
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        HashMap<String, String> result = new HashMap<>();

        for(HashMap.Entry<String, String> entry : h1.entrySet()) {
            if(str.toLowerCase().contains(entry.getValue().toLowerCase()))
            {
                keywords.add(entry.getKey());
                result.put(entry.getKey(),entry.getValue());
            }
        }

        return result;
    }

    public void onOkBtnClicked(View view){
        Intent intent = new Intent(this, DisplayQuestionsActivity.class);
        intent.putStringArrayListExtra("keywords",getSymptomsSpoken());
//        Toast.makeText(getApplicationContext(),getfinalList().toString(),Toast.LENGTH_LONG).show();

        startActivity(intent);


    }

}
