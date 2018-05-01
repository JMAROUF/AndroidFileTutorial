package com.example.jamal.fileexample;

import android.Manifest;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity  {

    File mFile ;
    OutputStream out;
    InputStream in;
    int i;
    String path,monNom = "MY NAME";
    StringBuffer buffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //demande de permission , puisqu'elle une permission dangerouse pour la lecture externe
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        try {
            // ecriture sur le fichier interne
            out = openFileOutput("monFichier",MODE_PRIVATE);
            out.write(monNom.getBytes());
            out.close();
            //lecture depuis le fichier interne
            buffer= new StringBuffer();
            in = openFileInput("monFichier");
            while((i=in.read())!=-1){
                buffer.append((char)i);
            }
            in.close();
            Toast.makeText(this," lecture depuis fichier interne  : "+buffer,Toast.LENGTH_LONG);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        //on verifie si le support du stockage (carte memoire )  est monté et si on a le droit d'ecrire
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) &&
                ! (Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) ){
            //recuperation du chemin
            path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
            // creation du fichier
           mFile= new File(path,"monFichier.txt");
            try {
                mFile.createNewFile();
                // ecriture sur le fichier externe
                out = new FileOutputStream(mFile);
                out.write(monNom.getBytes());
                out.close();
                // lecture depuis le fichier externe
                buffer= new StringBuffer();
                in = new FileInputStream(mFile);
                while((i=in.read())!=-1){
                    buffer.append((char)i);
                }
                in.close();
                Toast.makeText(this," lecture depuis fichier externe  : "+buffer,Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void process(View v){
        // pour tuer le process : ATTENTION ce type d'instruction est trés dangereux , alors eviter de le faire , à la place utiliser finish()
        android.os.Process.killProcess(android.os.Process.myPid());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
