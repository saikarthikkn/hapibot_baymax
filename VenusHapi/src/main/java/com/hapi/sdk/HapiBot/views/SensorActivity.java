package com.hapi.sdk.HapiBot.views;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import com.hapi.sdk.HapiBot.HeartRateApi;
import com.hapi.sdk.HapiBot.R;

import java.util.Timer;
import java.util.TimerTask;
import data.model.HeartRate;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alok_shankar on 2/25/2017.
 */

public class SensorActivity extends AppCompatActivity {

    String url = "https://bigbang-baymax.herokuapp.com/";
    TextView text_id_value, text_id_accuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text_id_value = (TextView) findViewById(R.id.text_id_value);
        text_id_accuracy = (TextView) findViewById(R.id.text_id_accuracy);
        callAsynchronousTask();
    }

    void getHeartRateData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HeartRateApi service = retrofit.create(HeartRateApi.class);
        Call<HeartRate> call = service.getHeartRate();
        call.enqueue(new Callback<HeartRate>() {
            @Override
            public void onResponse(Call<HeartRate> call,
                                   Response<HeartRate> response) {
                try {

                    text_id_value.setText("value  :  " + response.body().getValues());
                    text_id_accuracy.setText("accuracy  :  " + response.body().getAccuracy());
                    String string = response.body().getValues()+"";
                    String s1= string.substring(2,4);
                    int heartRate =Integer.parseInt(s1);
                    if(heartRate>85){
                        onSuccess(response);

                    }


                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<HeartRate> call, Throwable t) {
                Log.e("baymax", t.getMessage());
            }
        });
    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            getHeartRateData();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 50000 ms
    }

    public  void getDialog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(SensorActivity.this);
        builder1.setMessage("Your Heart Rate is very High . You Need Medical Assistance");
        builder1.setCancelable(true);

        Uri number = Uri.parse("sms:7903866361");
        Intent intent =new Intent( Intent.ACTION_VIEW, number);
        intent.putExtra("sms_body","Hello There is an Emergency we need help");
        startActivity(intent);

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


    public void onSuccess(Response<HeartRate> response) {
        getDialog();

    }
}
