package com.hapi.sdk.HapiBot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.Window;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import Models.ScannedReport;


public class DetailViewActivity extends AppCompatActivity {

private final Context context = this;
    DetailListViewAdapter LAdapter;
    ExpandableListView list;
    Activity a;
    int height;
    int width;
    DatabaseHelper databaseHelper;
    HashMap<String, List<ScannedReport>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.databaseHelper  = new DatabaseHelper(this);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        ArrayList<String> listDataHeader = new ArrayList<String>();
        this.listDataChild = new HashMap<String, List<ScannedReport>>();
        String path = getIntent().getStringExtra("path");

        ScannedReport[] scannedReports = databaseHelper.getScannedReports();
        populateMap(scannedReports);

        // Adding child data
//        listDataHeader.add("Date 01");
//        listDataHeader.add("Date 02");
//        listDataHeader.add("Date 03");
//
//        // Adding child data
//        List<String> Date_1 = new ArrayList<String>();
//        Date_1.add("Blood report");
//        Date_1.add("Urine test");
//        Date_1.add("MRI report");
//        Date_1.add("Prescription 1");
//        Date_1.add("Prescription 2");
//
//        List<String> Date_2 = new ArrayList<String>();
//        Date_2.add("Blood report");
//        Date_2.add("Urine test");
//        Date_2.add("MRI report");
//        Date_2.add("Prescription 1");
//        Date_2.add("Prescription 2");
//
//        List<String> Date_3 = new ArrayList<String>();
//        Date_3.add("Blood report");
//        Date_3.add("Urine test");
//        Date_3.add("MRI report");
//        Date_3.add("Prescription 1");
//        Date_3.add("Prescription 2");

//        listDataChild.put(listDataHeader.get(0), Date_1); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), Date_2);
//        listDataChild.put(listDataHeader.get(2), Date_3);

        list=(ExpandableListView) findViewById(R.id.exp_listview);
        LAdapter = new DetailListViewAdapter(this,listDataHeader,listDataChild);
        list.setAdapter((ExpandableListAdapter) LAdapter);
    }
    public void showList(){
        LAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_my_local, menu);
        return true;
    }

    public ScannedReport[] getScannedReports()
    {
        return databaseHelper.getScannedReports();
    }

    private void populateMap(ScannedReport[] scannedReports) {
        for(ScannedReport report: scannedReports) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String date =  dateFormat.format(report.getDate());
            List<ScannedReport> list = null;
            if(listDataChild.get(date)!=null) {
                list = listDataChild.get(date);
            } else
            {
                list = new ArrayList<ScannedReport>();
            }

            list.add(report);
        }
    }
}
