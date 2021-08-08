package com.example.dooleapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
DooleView view;
Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       view =(DooleView) findViewById(R.id.doole);
       saveBtn=findViewById(R.id.btn_save);
       saveBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder saveDialog=new AlertDialog.Builder(MainActivity.this);
               saveDialog.setTitle("Save?");
               saveDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       view.setDrawingCacheEnabled(true);
                       String imgSaved= MediaStore.Images.Media.insertImage(getContentResolver(),view.getDrawingCache(), UUID.randomUUID().toString()+".png","Doole image");
                       if(imgSaved!=null)
                           Toast.makeText(getApplicationContext(),"Image saved",Toast.LENGTH_LONG).show();
                       else
                           Toast.makeText(getApplicationContext(),"Internal Error",Toast.LENGTH_LONG).show();
                   }
               });
               saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                   }
               });
               saveDialog.show();
           }
       });
    }
}