package com.hapi.sdk.HapiBot;

/**
 * Created by sai_goturu on 2/22/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Models.LabReportResult;
import Models.LabTestReport;
import Models.ScannedReport;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PHA";

    // Table Names
    private static final String TABLE_LabReportMasterData = "LabReportMasterData";
    private static final String TABLE_ScannedReport = "ScannedReport";
    private static final String TABLE_LabReportResult = "LabReportResult";

    // Common column names
    // LabReportMasterData Table - column nmaes
    private static final String _testIDKey = "TestId";
    private static final String _testName = "TestName";
    private static final String _gender = "Gender";
    private static final String _testNameAlias = "TestNameAlias";
    private static final String _reportType = "ReportType";
    private static final String _units = "Units";
    private static final String _lowerVal = "LowerVal";
    private static final String _higherVal = "HigherVal";
    // TAGS Table - column names

    // ScannedReport Table - column nmaes
    private static final String _reportIDKey = "ReportId";
    private static final String _date = "ScanDate";
    private static final String _name = "Name";
    private static final String _path = "Path";
    private static final String _type = "Type";
    // TAGS Table - column names

    // LabReportResult Table - column nmaes
    private static final String _reportID = "ReportId";
    private static final String _testID = "TestId";
    private static final String _value = "Value";
    // TAGS Table - column names

    private static final String CREATE_TABLE_LabReportMasterData = "CREATE TABLE "
            + TABLE_LabReportMasterData + "(" + _testIDKey + " TEXT PRIMARY KEY," + _testName
            + " TEXT," + _gender
            + " TEXT,"+ _testNameAlias
            + " TEXT,"+ _reportType
            + " TEXT,"+ _units
            + " TEXT,"+ _lowerVal + " INTEGER," + _higherVal
            + " INTEGER" + ")";

    private static final String CREATE_TABLE_ScannedReports = "CREATE TABLE "
            + TABLE_ScannedReport + "(" + _reportIDKey + " TEXT PRIMARY KEY," + _date
            + " DATETIME," + _name
            + " TEXT,"+ _path
            + " TEXT,"+ _type
            + " TEXT" + ")";

    private static final String CREATE_TABLE_LabReportResult = "CREATE TABLE "
            + TABLE_LabReportResult + "(" + _reportID + " TEXT PRIMARY KEY," + _testID
            + " TEXT," + _value
            + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE_LabReportMasterData + "';";
        System.out.println("Checking Database table");

        //Cursor c = db.rawQuery(query, null);

        //if(c!=null || c.getCount()>0) {
        //    System.out.println("Table already exists");
        //    return;
        //}

        System.out.println("Creating Database table");
        //db.beginTransaction();
        // creating required tables
        db.execSQL(CREATE_TABLE_LabReportMasterData);
        db.execSQL(CREATE_TABLE_ScannedReports);
        db.execSQL(CREATE_TABLE_LabReportResult);

        LabTestReport report = new LabTestReport();

        report.setTestID("A");
        report.setTestName("Albumin, Serum");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("B");
        report.setTestName("BUN/Creatinine Ratio");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("C");
        report.setTestName("Creatinine, Serum");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("D");
        report.setTestName("eGFR");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("E");
        report.setTestName("A/G Ratio");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("F");
        report.setTestName("Globulin, Total");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("G");
        report.setTestName("Creatinine");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("H");
        report.setTestName("Urea");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("I");
        report.setTestName("Total protein");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("J");
        report.setTestName("Albumin fraction");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("K");
        report.setTestName("Alpha 1 fraction");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("L");
        report.setTestName("Alpha 2 fraction");
        report.setGender("M");
        createTestMaterDataEntry(report, db);


        report.setTestID("M");
        report.setTestName("Beta globulin fraction");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("N");
        report.setTestName("Gamma globulin fraction");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("O");
        report.setTestName("Albumin zone");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("P");
        report.setTestName("Alpha 1 zone");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("Q");
        report.setTestName("Alpha 2 zone");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("R");
        report.setTestName("Beta 1 zone");
        report.setGender("M");
        createTestMaterDataEntry(report, db);

        report.setTestID("S");
        report.setTestName("Beta 2 zone");
        report.setGender("M");
        createTestMaterDataEntry(report, db);
        //db.endTransaction();

        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LabReportMasterData);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_ScannedReports);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_LabReportResult);
        onCreate(db);
    }

    /*
 * Creating a todo
 */
    public long createTestMaterDataEntry(LabTestReport report, SQLiteDatabase db) {
        //SQLiteDatabase db = this.getWritableDatabase();

        System.out.println("Inserting values");
        ContentValues values = new ContentValues();
        values.put(_testID, report.getTestID());
        values.put(_testName, report.getTestName());
        values.put(_testNameAlias, report.getTestNameAlias());
        values.put(_gender, report.getGender());
        values.put(_reportType, report.getReportType());
        values.put(_units, report.getUnits());
        values.put(_lowerVal, report.getLowerVal());
        values.put(_higherVal, report.getHigherVal());
        // insert row
        long report_id = db.insert(TABLE_LabReportMasterData, null, values);

        return report_id;
    }
    public long createScannedReportEntry(ScannedReport report, SQLiteDatabase db) {

        System.out.println("Inserting ScannedReport values");
        ContentValues values = new ContentValues();
        values.put(_reportIDKey, report.getReportID());

        values.put(_date, getDateTime(report.getDate()));
        values.put(_name, report.getName());
        values.put(_path, report.getPath());
        values.put(_type, report.getType());
        // insert row
        long report_id = db.insert(TABLE_ScannedReport, null, values);

        return report_id;
    }

    public void createScannedReportEntries(ScannedReport[] reports) {
        if(reports==null || reports.length==0)
            return;

        SQLiteDatabase db = getWritableDatabase();
        for (ScannedReport report: reports) {
            createScannedReportEntry(report, db);
        }
    }

    private String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public long createLabReportResultEntry(LabReportResult report, SQLiteDatabase db) {
        //SQLiteDatabase db = this.getWritableDatabase();

        System.out.println("Inserting LabReportResult values");
        System.out.println("testid = " + report.getTestID() + ", value = " + report.getValue());
        ContentValues values = new ContentValues();
        values.put(_reportID, report.getReportID());
        values.put(_testID, report.getTestID());
        values.put(_value, report.getValue());
        // insert row
        long report_id = db.insert(TABLE_LabReportResult, null, values);

        return report_id;
    }

    public void createLabReportResultEntries(LabReportResult[] reports) {

        if(reports==null || reports.length==0)
            return;

        SQLiteDatabase db = getWritableDatabase();
        for (LabReportResult report: reports) {
            createLabReportResultEntry(report, db);
        }
    }

    /*
 * get single todo
 */

    public LabTestReport[] getTestMaterDataEntries(String gender) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_LabReportMasterData + " WHERE "
                + _gender + " = \"" + gender + "\"";

        // Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0) {
            LabTestReport[] labReports = new LabTestReport[c.getCount()];
            int i=0;
            c.moveToFirst();
            do {
                LabTestReport td = new LabTestReport();
                td.setTestID(c.getString(0));
                td.setTestName((c.getString(1)));
                td.setGender(c.getString(2));
                td.setTestNameAlias(c.getString(3));

                System.out.println(td.getTestID());
                System.out.println(td.getTestName());
                System.out.println(td.getTestNameAlias());

                labReports[i++] = td;
            }while (c.moveToNext());

            c.close();
            db.close();
            return labReports;
        }
        return null;
    }
    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}