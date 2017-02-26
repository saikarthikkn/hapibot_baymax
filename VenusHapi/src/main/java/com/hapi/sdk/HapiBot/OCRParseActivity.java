package com.hapi.sdk.HapiBot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import Models.ScannedReport;

public class OCRParseActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    String reportId;
    String filename;
//    OCRParseActivity(){
//        databaseHelper = new DatabaseHelper(this);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocrparse);
        Intent intent = getIntent();
        filename = intent.getStringExtra("file");
        showProgress(true);
        createDBHelper();

        int numReports = databaseHelper.getNumOfEntriesInScannedReports();
        reportId = "Report_"+numReports+1;

        OCRHelper mAuthTask = new OCRHelper(this, filename, reportId);
        mAuthTask.execute((Void) null);
    }

    private void createDBHelper(){
        this.databaseHelper = new DatabaseHelper(this);
    }

    public void parseCompleted(String recognizedText) {
        TextView textView = (TextView) this.findViewById(R.id.textView3);
        textView.setText(recognizedText);
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        ScannedReport scannedReport = new ScannedReport(date,reportId,filename,"BloodReport");
        databaseHelper.createScannedReportEntry(scannedReport);
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra("path",filename);
        startActivity(intent);
        // Call the Report Activity
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);

            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
    }
}
