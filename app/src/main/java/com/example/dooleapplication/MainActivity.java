package com.example.dooleapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
static int EXTERNAL_WRITEABLE_CODE=101;
DooleView dooleView;
Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dooleView =(DooleView) findViewById(R.id.doole);
        saveBtn=findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(v -> {
            AlertDialog.Builder saveDialog=new AlertDialog.Builder(MainActivity.this);
            saveDialog.setTitle("Save?");
            saveDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) == (PackageManager.PERMISSION_GRANTED)) {
                         storeImage();
                    }
                    else{
                        requestPermission();
                    }
                }
            });
            saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                 dialog.cancel();
                }
            });
            saveDialog.show();
        });
    }
    private boolean hasExternalStorageWritePermission(){
        boolean m= ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        Log.e("hasE",String.valueOf(m));
     return ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this.getApplicationContext())
                    .setTitle("Permission Needed")
                    .setMessage("Permission needed to save the image")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_WRITEABLE_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_WRITEABLE_CODE);
        }

     /* if(!hasExternalStorageWritePermission()){

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
        }*/

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==EXTERNAL_WRITEABLE_CODE){
            if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED)
                storeImage();
        }
    }
    public void storeImage() {
        dooleView.setDrawingCacheEnabled(true);
        String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(), dooleView.getDrawingCache(), UUID.randomUUID().toString() + ".png", "Doole image");
        if (imgSaved != null)
            Toast.makeText(getApplicationContext(), "Image saved", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "Internal Error", Toast.LENGTH_LONG).show();
    }
}