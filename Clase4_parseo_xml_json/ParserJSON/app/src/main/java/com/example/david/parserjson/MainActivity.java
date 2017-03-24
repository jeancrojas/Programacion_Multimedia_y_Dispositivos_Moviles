package com.example.david.parserjson;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    File ruta;
    File fichero;
    ArrayList<Capitulo> capitulos;
    ListView lista;
    Adaptador adap;
    private static final int MY_PERMISIONS=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ruta= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        fichero = new File(ruta,"series.json");
        //-- URL: http://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1
        capitulos=new ArrayList<Capitulo>();
        lista=(ListView)findViewById(R.id.lista);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                parseo();
            }
            catch (Exception e){
            }

        } else {
            // permission denied
            Toast.makeText(getApplicationContext(),"Permiso Denegado",Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void parseo() throws Exception{
        Capitulo cap=null;
        FileInputStream is = new FileInputStream(fichero);
        String jsonStr="";

        DataInputStream in = new DataInputStream(is);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            jsonStr = jsonStr + strLine;
        }
        in.close();

        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray data  = jsonObj.getJSONArray("Episodes");
        for (int i = 0; i < data.length(); i++) {
            JSONObject c = data.getJSONObject(i);
            String titulo=c.getString("Title");
            String numCap=c.getString("Episode");
            String fecha=c.getString("Released");
            String puntos=c.getString("imdbRating");
            String imdbID=c.getString("imdbID");
            cap=new Capitulo(titulo,numCap,fecha,puntos,imdbID);
            capitulos.add(cap);
        }
        adap=new Adaptador(capitulos,getApplicationContext());
        lista.setAdapter(adap);
    }

    public void abrirWeb(Intent i){
        startActivity(i);
    }
}
