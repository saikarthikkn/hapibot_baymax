package com.hapi.sdk.HapiBot;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import Models.LabReportResult;
import Models.LabTestReport;
import Models.OCRFileIDData;
import Models.OCRFileResponse;
import Models.OCRRecognizedTextResponse;
import Models.OCRTextData;
import Models.ScannedReport;
import Remote.ApiUtils;
import Remote.OCRService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sai_goturu on 2/25/2017.
 */

public class OCRHelper extends AsyncTask<Void, Void, Boolean> {

    OCRService service;
    static final String apiKey = "8fd866d85a6abe835ff43474d5db4c74";
    String recognized_text;
    String fileName;
    DatabaseHelper databaseHelper;
    OCRParseActivity activity;
    String reportId;

    OCRHelper(OCRParseActivity activity, String fileName, String reportId) {
        this.fileName = fileName;
        this.databaseHelper  = new DatabaseHelper(activity);
        this.activity = activity;
        this.reportId = reportId;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

//        try {

            getAndSaveRecognizedText();
        //recognized_text = "Laboratory Test Patient’s results Reference Value\\nCreatinine 40.5 <79.2\\nUrea 2.9 <8.3\\nTotal protein 82 64-83\\nAlbumin fraction 63.5 60-71\\nAlpha 1 fraction 2.4 1.4-2.9\\nAlpha 2 fraction 12.1 7-11\\nBeta globulin fraction 16.4 8-13\\nGamma globulin fraction 5.6 9-16\\nAlbumin zone 51.3 53.1-66.4\\nAlpha 1 zone 5.9 3.2-5.7\\nAlpha 2 zone 16.3 7.5-12.4\\nBeta 1 zone 15.7 5.2-8.1\\nBeta 2 zone 4.7 3.4-6.5";
        //parseRecognizedText();
//        }
//
        // TODO: register the new account here.
        return true;
    }

    private void parseRecognizedText()
    {
        activity.parseCompleted(recognized_text);
        try {

            LabTestReport[] testsData = loadTestNameData();

            //String report = "TABLE 1: BLOOD ANALYSIS\nTest Name\n\nAlbunin 4.90 H\nGlobuin 2.70 Low\nTotal Protein 7.60 Opt\nTotal Cholesterol 1 82.00 H\nTrigycetide 70.00 Low";
            //String report = "Coup. Metabolic Panel (14)\n\nGlucose, Serum 84 mg/dL 65-99 01\nBUN 16 mg/dL 5-26 01\nCreatinine, Serum 1.06 mg/dL 0.76-1.27 01\neGFR >59 mL/min/1.73 >59 01\nBUN/Creatinine Ratio 15 8-27 01\nSodium, Serum 141 mmol/L 135-145 01\nPotassium, Serum 4.4 mmol/L 3.5-5.2 01\nChloride, Serum 101 mmol/L 97-108 01\nCarbon Dioxide, Total 23 mmol/L 20-32 01\nCalcium, Serum 9.9 mg/dL 8.7-lO.2 01\nProtein, Total, Serum 7.6 g/dL 6.0-8.5 01\nAlbumin, Serum 4.9 g/dL 3.5-5.5 01\nGlobulin, Total 2.7 g/dL 1.5-4.5 01\nA/G Ratio 1.8 1.1-2.5 01\nBilirubin, Total 1.8 High mg/dL 0.0-1.2 01\nAlkaline Phosphatase, S 65 IU/L 25-150 01\nAST ($60?) 30 IU/L 0-40 01\nALT (SGPT) 32 IU/L 0-55 01";
            String report = recognized_text;
            //report = "Alpha 1 fraction 2.4 1.4-2.9\n" +
            //        "Alpha 2 fraction 12.1 7-11";
            String[] tests = {"Albumin, Serum", "BUN/Creatinine Ratio", "Creatinine, Serum", "eGFR", "A/G Ratio", "Globulin, Total", "Total Protein", "Total Cholesterol", "Trigycetide", "Sodium, Serum", "Chloride, Serum", "Carbon Dioxide, Total", "Calcium, Serum", "Protein, Total, Serum", "Bilirubin, Total", "Alkaline Phosphatase, S", "Bilirubin, Total", "BUN", "Potassium", "Glucose, Serum"};
            StringReader stream = new StringReader(report);
            BufferedReader buffReader = new BufferedReader(stream);

            String line;
            //JSONArray results = new JSONArray();
            ArrayList<LabReportResult> labResults = new ArrayList<LabReportResult>();

            do {
                line = buffReader.readLine();
                if (line == null)
                    break;
                LabReportResult result = null;
                Log.i("baymax", "line = " + line);

                line = line.trim();

                for (LabTestReport test : testsData) {
                    if (line.startsWith(test.getTestName())) {
                        line = line.substring(line.indexOf(test.getTestName())+test.getTestName().length());
                        Log.i("baymax", "updated line = " + line);
                        result = new LabReportResult();
                        result.setTestID(test.getTestID());
                        Log.i("baymax", "test = " + test.getTestID());
                    } else if (test.getTestNameAlias() != null && line.startsWith(test.getTestNameAlias())) {
                        line = line.substring(line.indexOf(test.getTestNameAlias())+ test.getTestNameAlias().length());
                        Log.i("baymax", "updated line = " + line);
                        result = new LabReportResult();
                        result.setTestID(test.getTestID());
                        Log.i("baymax", "test = " + test.getTestID());
                    }
                }

                if (result == null)
                    continue;

                String[] items = line.split(" ");
                String value = null;
                String testName = null;

                for (String item : items) {
                    item = item.trim();
                    Log.i("baymax", "item = " + item);
                    Double testValue = parseDouble(item);
                    Log.i("baymax", "testValue = " + testValue);

                    if (testValue != null) {
                        result.setValue(item);
                        break;
                    }
                }
                if (result != null) {
                    Log.i("baymax", "test name = " + result.getTestID());
                    Log.i("baymax", "test value = " + result.getValue());

                    labResults.add(result);

                        /*JSONObject json = new JSONObject();
                        try {
                            json.put(result.getTestID(), result.getValue());
                            results.put(json);
                        }catch (JSONException e) {
                            System.out.println(e.getStackTrace());
                        }*/
                }
            } while (line != null);

            //System.out.println(results.toString());

            LabReportResult[] values = new LabReportResult[labResults.size()];
            values = labResults.toArray(values);
            databaseHelper.createLabReportResultEntries(values);

            databaseHelper.close();

        }catch (IOException e) {

        }

    }
    private void getAndSaveRecognizedText(){
        Log.i("baymax","start upload");
        service = ApiUtils.getOCRService();
        final String[] fileId = new String[1];

        // MultipartBody.Part is used to send also the actual file name
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), new File(fileName));

        // finally, execute the request
        Call<OCRFileResponse> call = service.upload(body);
        call.enqueue(new Callback<OCRFileResponse>() {
            String recognizedText;
            @Override
            public void onResponse(Call<OCRFileResponse> call,
                                   Response<OCRFileResponse> response) {
                if(response.isSuccessful()){
                    OCRFileResponse ocrFileResponse = response.body();
                    OCRFileIDData data = ocrFileResponse.getData();
                    performOCRRecognition(data.getFileId());
                    Log.v("baymax", "upload success");
                }
            }

            @Override
            public void onFailure(Call<OCRFileResponse> call, Throwable t) {
                Log.e("baymax", t.getMessage());
            }
        });
    }

    private  void performOCRRecognition(String fileId){
        Log.i("baymax","start ocr");
        service = ApiUtils.getOCRService();
        final String[] recognizedText = new String[1];
        Call<OCRRecognizedTextResponse> call = service.recognizePIC(apiKey,fileId,"1","eng","6");
        call.enqueue(new Callback<OCRRecognizedTextResponse>() {
            @Override
            public void onResponse(Call<OCRRecognizedTextResponse> call,
                                   Response<OCRRecognizedTextResponse> response) {
                if(response.isSuccessful()){
                    OCRRecognizedTextResponse ocrFileResponse = response.body();
                    OCRTextData data = ocrFileResponse.getData();
                    //call the parser from here
                    saveRecognizedText(data.getText());

                    parseRecognizedText();
                }
                Log.v("baymax", "ocr success");
            }

            @Override
            public void onFailure(Call<OCRRecognizedTextResponse> call, Throwable t) {
                Log.e("baymax", t.getMessage());
            }
        });
    }

    public void saveRecognizedText(String recogtext)
    {
        recognized_text = recogtext;
    }

    private Double parseDouble(String str) {
        try {
            Double val = Double.parseDouble(str);
            return val;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(final Boolean success) {

        //activity.parseCompleted();
        if (success) {

        }
    }


    private LabTestReport[] loadTestNameData() {
        return databaseHelper.getTestMaterDataEntries("M");
    }
}