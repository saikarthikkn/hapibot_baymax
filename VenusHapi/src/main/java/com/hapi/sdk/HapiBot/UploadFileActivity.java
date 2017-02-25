package com.hapi.sdk.HapiBot;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URISyntaxException;

public class UploadFileActivity extends AppCompatActivity {

    private static final int REQUEST_GET_CONTENT = 330;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        Button browseButton = (Button)findViewById(R.id.browse);
        Button uploadButton = (Button)findViewById(R.id.upload);
        browseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                selectFileToUpload();
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });

    }

    private void uploadFile()
    {
        TextView textView = (TextView) findViewById(R.id.editText);
        String filePath =  textView.getText().toString();
        Intent returnData = new Intent(getApplication(),OCRParseActivity.class);
        returnData.putExtra("file",filePath);
        startActivity(returnData);
    }
    private void selectFileToUpload()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    REQUEST_GET_CONTENT);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_GET_CONTENT:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("baymax", "File Uri: " + uri.toString());
                    // Get the path
                    try {
                        String path = getPath(uri);
                        Log.d("baymax", "File Path: " + path);
                        TextView textView = (TextView) findViewById(R.id.editText);
                        textView.setText(path);
                    }
                    catch(Exception e)
                    {

                    }
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private String getPath(Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
}
