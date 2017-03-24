package com.example.david.tareadescarga;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progreso;
    Button b;
    EditText etUrl,etFichero;
    File ruta=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    String fichero;
    String urlFichero;
    private static final int MY_PERMISIONS=1;
    Descarga downloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=(Button)findViewById(R.id.btDescarga);
        etUrl=(EditText)findViewById(R.id.cajaUrl);
        etFichero=(EditText)findViewById(R.id.cajaNombre);
        progreso=null;
        downloadTask = new Descarga();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISIONS);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    downloadTask.execute(etUrl.getText().toString());
                } else {
                    // permission denied
                    Toast.makeText(getApplicationContext(),"Permiso Denegado",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    private class Descarga extends AsyncTask<String,Integer,Boolean>{

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            progreso = new ProgressDialog(MainActivity.this);
            progreso.setTitle("DESCARGA");
            progreso.setMessage("Descargando: "+ etUrl.getText().toString());
            progreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progreso.setIndeterminate(false);
            progreso.setProgress(0);
            progreso.show();
            fichero=etFichero.getText().toString();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL u=new URL(urls[0]);
                HttpURLConnection c=(HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();
                int tamanyo=c.getContentLength();
                File file = new File(ruta,fichero);
                FileOutputStream f= new FileOutputStream(file);
                InputStream entrada=c.getInputStream();
                byte[] buffer = new byte[1024];
                int bloc = 0;
                long acumulat = 0;
                while ((bloc = entrada.read(buffer)) > 0) {
                    acumulat += bloc;
                    publishProgress( (int)((acumulat*100)/tamanyo));
                    f.write(buffer, 0, bloc);
                }
                f.close();
            }
            catch (Exception e){
                return false;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            progreso.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result)
            {
                progreso.dismiss();
                Toast.makeText(MainActivity.this, "Descarga finalizada!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                progreso.dismiss();
                Toast.makeText(MainActivity.this, "Error de descarga",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
