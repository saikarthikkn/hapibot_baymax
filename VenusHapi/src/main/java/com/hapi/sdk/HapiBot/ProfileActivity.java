package com.hapi.sdk.HapiBot;

import android.app.DatePickerDialog;
import android.content.Intent;
//import android.icu.util.Calendar;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import android.text.InputType;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    //UI References
    private EditText dateOfBirth;
    private DatePickerDialog dobPickerDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewsById();
        setDateTimeField();
    }

    /*private Button.OnClickListener register
            = new Button.OnClickListener(){

        public void onClick(View v) {
            Uri uri = Uri.parse("https://www.fitbit.com/login");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    };*/

    private void findViewsById() {
        dateOfBirth = (EditText) findViewById(R.id.txtdob);
        dateOfBirth.setInputType(InputType.TYPE_NULL);
        dateOfBirth.requestFocus();
     }

    private void setDateTimeField() {
        dateOfBirth.setOnClickListener(this);

       /* Calendar newCalendar = Calendar.getInstance();
        dobPickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateOfBirth.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == dateOfBirth) {
            dobPickerDialog.show();
        }
    }

    /*FloatingActionButton btnregister = (FloatingActionButton)findViewById(R.id.btnregister);
    btnregister.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            goToUrl();
        }
    });*/
    public void goToUrl(View v)
    {
        Uri uriUrl = Uri.parse("https://www.fitbit.com/login");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
