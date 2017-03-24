package com.example.david.parserxml;

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
import android.Manifest;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    File ruta;
    File fichero;
    ArrayList<Parte> partes;
    ListView lista;
    Adaptador adap;
    private static final int MY_PERMISIONS=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ruta= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        fichero = new File(ruta,"partes.xml");
        //-- URL: http://www.comptechdoc.org/independent/web/xml/guide/parts.xml
        partes=new ArrayList<Parte>();
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

    private void parseo() throws XmlPullParserException,IOException{
        Parte prt=null;
        XmlPullParser p= Xml.newPullParser();
        try {
            FileInputStream fin = new FileInputStream(fichero);
            p.setInput(fin, "UTF-8");
        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        int event=p.next();
        while (event!=XmlPullParser.END_DOCUMENT) {

            if (event==XmlPullParser.START_TAG)
            {
                if (p.getName().equalsIgnoreCase("PART"))
                {
                    prt=new Parte();
                }
                else if (p.getName().equalsIgnoreCase("ITEM"))
                {
                    p.next();
                    prt.setNombre(p.getText());
                }
                else if (p.getName().equalsIgnoreCase("MANUFACTURER"))
                {
                    p.next();
                    prt.setFabircante(p.getText());
                }
                else if (p.getName().equalsIgnoreCase("MODEL"))
                {
                    p.next();
                    prt.setModelo(p.getText());
                }
                else if (p.getName().equalsIgnoreCase("COST"))
                {
                    p.next();
                    prt.setPrecio(Double.parseDouble(p.getText()));
                }
            }
            if (event==XmlPullParser.END_TAG)
            {
                if (p.getName().equalsIgnoreCase("PART"))
                {
                    partes.add(prt);;
                }

            }
            event=p.next();
        }
        adap=new Adaptador(partes,getApplicationContext());
        lista.setAdapter(adap);
    }
}
