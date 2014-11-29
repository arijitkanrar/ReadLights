package com.maniacapps.readlights;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Scanner extends Activity {

    private Button stButton;
    //private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        stButton = (Button) findViewById(R.id.startButton);
        stButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCameraImage();
            }
        });

    }

    private Uri getImageFileDirectory() {
        File stoDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "readLights");

        if(!stoDir.exists()){
            if(!stoDir.mkdirs()){
                Log.d("ReadLights App status", "Unable to create app dir");
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(stoDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");

        Log.i("ReadLights app status", "File: " + mediaFile.getAbsolutePath());
        return Uri.fromFile(mediaFile);
    }

    private void getCameraImage() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT,getImageFileDirectory());

        startActivityForResult(i, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == 100) {
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "Image saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Image capture cancelled by user", Toast.LENGTH_SHORT).show();
                    Log.d("ReadLights App status", "Image capture cancelled by user");
                } else {
                    Toast.makeText(this, "Image capture failed for unknown reasons", Toast.LENGTH_SHORT).show();
                    Log.d("ReadLights App status", "Image capture failed for unknown reasons");
                }
            }
        }
        catch(Exception e){
            Log.d("ReadLights exception", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scanner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
