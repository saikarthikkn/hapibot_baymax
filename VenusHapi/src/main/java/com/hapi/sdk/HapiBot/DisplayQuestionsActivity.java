package com.hapi.sdk.HapiBot;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.support.design.internal.ParcelableSparseArray;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import data.model.Choice;
import data.model.Condition;
import data.model.Conditions;
import data.model.Diagnosis;
import data.model.Evidence;
import data.model.Item;
import data.model.SymptomFilters;
import data.model.Symptoms;
import data.remote.ApiUtils;
import data.remote.InfermedicaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayQuestionsActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "extra_message";
    InfermedicaService service;
    List<Symptoms> allSymptoms;
    List<Conditions> allConditions;
    List<Condition> result;
    SymptomFilters symptomFilters=null;
    Diagnosis diagnosis;
    TextView textView;
    public final int MAX_QUESTIONS=5;
    static int count=1;
    int numberOfRadioButtons;
    RadioGroup radioGroup;

    // Starts Text To Speech Module
    TextToSpeech t1;
//    questionsGrp

    // Ends TextToSpeech Module


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_questions);
        textView=(TextView) findViewById(R.id.textView);
        radioGroup = (RadioGroup)findViewById(R.id.questionsGrp);
        initializeSymptoms();
        initializeConditions();
        List<String> symptomsAcquired=getIntent().getStringArrayListExtra("keywords");
        diagnoseDisease(symptomsAcquired);
    }

    public void initializeSymptoms(){
        service= ApiUtils.getService();
        service.getSymptoms().enqueue(new Callback<List<Symptoms>>() {
            @Override
            public void onResponse(Call<List<Symptoms>> call, Response<List<Symptoms>> response) {

                allSymptoms=response.body();
            }

            @Override
            public void onFailure(Call<List<Symptoms>> call, Throwable t) {

            }
        });
    }

    public void initializeConditions(){
        service= ApiUtils.getService();
        service.getConditions().enqueue(new Callback<List<Conditions>>() {
            @Override
            public void onResponse(Call<List<Conditions>> call, Response<List<Conditions>> response) {
                if (response.isSuccessful()){
                    allConditions=response.body();
                }
            }
            @Override
            public void onFailure(Call<List<Conditions>> call, Throwable t) {

            }
        });
    }

    public void okBtnClicked(View view){
        if(count<=MAX_QUESTIONS){
            int checkedRadioButtonId =radioGroup.getCheckedRadioButtonId();
            View radioButton=radioGroup.findViewById(checkedRadioButtonId);
            int indexOfChild=radioGroup.indexOfChild(radioButton);
            RadioButton rBtn = (RadioButton) radioGroup.getChildAt(indexOfChild);
            String label = rBtn.getText().toString();
            Log.d("inside okBtnClicked",label);
            Evidence evidence=new Evidence();
            if(diagnosis.getQuestion().getItems().size()==1){
                evidence.setId(diagnosis.getQuestion().getItems().get(0).getId());
                evidence.setChoiceId(getChoiceIdForGivenLabel(diagnosis,label));
            }else{
                evidence.setId(diagnosis.getQuestion().getItems().get(indexOfChild).getId());
                evidence.setChoiceId("present");
            }

            symptomFilters.getEvidence().add(evidence);
            service.diagnose(symptomFilters).enqueue(new Callback<Diagnosis>() {
                @Override
                public void onResponse(Call<Diagnosis> call, Response<Diagnosis> response) {
                    String result;
                    if(response.isSuccessful()){
                        diagnosis=response.body();
                        displayQuestions(diagnosis);
                    }
                }

                @Override
                public void onFailure(Call<Diagnosis> call, Throwable t) {
                }
            });
        }else{
            String result=diagnosis.getConditions().get(0).getName();
//            say("You are suffering from "+result);
            Log.d("RESULT :", "okBtnClicked: "+result);
            Intent intent = new Intent(this, DisplayResult.class);
            intent.putExtra(EXTRA_MESSAGE, result);
            startActivity(intent);
        }
        count++;
    }

    private void displayResult(List<Condition> results){
        Intent intent = new Intent(this, DisplayResult.class);
//        intent.putParcelableArrayListExtra(EXTRA_MESSAGE,(ArrayList<? extends Parcelable> re));
//        intent.putStringArrayListExtra(EXTRA_MESSAGE,results);
//        intent.putExtra(EXTRA_MESSAGE, result);
        startActivity(intent);
    }
    private String getChoiceIdForGivenLabel(Diagnosis diagnosis, String label) {
        List<Choice> choices;
        List<Item> items=diagnosis.getQuestion().getItems();
        for (int i=0;i<items.size();i++){
            choices=items.get(i).getChoices();
            for (int j=0;j<choices.size();j++){
                if (choices.get(j).getLabel().equals(label)){
                    return choices.get(j).getId();
                }
            }
        }
        return  null;

    }

    public void diagnoseDisease(List<String> symptomsAcquired){
        service= ApiUtils.getService();
        symptomFilters=new SymptomFilters();
        symptomFilters.setAge(30);
        symptomFilters.setSex("male");
        List<Evidence> evidences=new ArrayList<>();
        Evidence evidence=new Evidence();
        evidence.setId("s_98");
        evidence.setChoiceId("present");
//        for(int i=0;i<symptomsAcquired.size();i++){
//            evidence=new Evidence();
//            evidence.setId(symptomsAcquired.get(i));
//            evidence.setChoiceId("present");
//        }
        evidences.add(evidence);
        symptomFilters.setEvidence(evidences);
        service.diagnose(symptomFilters).enqueue(new Callback<Diagnosis>() {
            @Override
            public void onResponse(Call<Diagnosis> call, Response<Diagnosis> response) {
                String result;
                if(response.isSuccessful()){
                    diagnosis=response.body();
                    displayQuestions(diagnosis);
                }
            }

            @Override
            public void onFailure(Call<Diagnosis> call, Throwable t) {
            }
        });
    }

    public void displayQuestions(Diagnosis diagnosis){
        radioGroup.removeAllViews();
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{

                        Color.DKGRAY
                        , Color.rgb (242,81,112),
                }
        );
        RadioButton radioButton;
        int numberOfItems=diagnosis.getQuestion().getItems().size();
        textView.setText(diagnosis.getQuestion().getText());
//        say(diagnosis.getQuestion().getText());
        if(numberOfItems>1){
            numberOfRadioButtons=numberOfItems;
            for(int i=0;i<numberOfRadioButtons;i++){
                radioButton=new RadioButton(getApplicationContext());
               // radioButton.setGravity(Gravity.CENTER_HORIZONTAL);
                radioButton.setButtonTintList(colorStateList);
                radioButton.setTextColor(R.color.colorAccent);
                radioButton.setText( diagnosis.getQuestion().getItems().get(i).getName());
                radioGroup.addView(radioButton);
//                say(i+1+diagnosis.getQuestion().getItems().get(i).getName());
            }
        }else{
            numberOfRadioButtons=diagnosis.getQuestion().getItems().get(0).getChoices().size();
            //check if probability >95%
//            List<Condition> conditions=diagnosis.getConditions();
//            for(int i=0;i<conditions.size();i++){
//                if(conditions.get(i).getProbability()>0.90f){
//                    String result=conditions.get(i).getName();
//                    break;
//                }
//            }
            for (int i=0;i<numberOfRadioButtons;i++){
                radioButton=new RadioButton(getApplicationContext());
                radioButton.setText(diagnosis.getQuestion().getItems().get(0).getChoices().get(i).getLabel());
                 radioGroup.addView(radioButton);
//                say(i+1+diagnosis.getQuestion().getItems().get(0).getChoices().get(i).getLabel());

            }
        }
    }

}
