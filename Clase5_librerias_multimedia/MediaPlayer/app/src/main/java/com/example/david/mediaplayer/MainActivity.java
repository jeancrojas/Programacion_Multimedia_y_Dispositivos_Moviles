package com.example.david.mediaplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    ImageView foto;
    TextView posicion,duracion,titulo;
    SeekBar barra;
    double tiempoFin,tiempoActual;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MIS_PERMISOS=1;
    private Handler manejador = new Handler();
    File rutaDescargas,rutaImagenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        foto=(ImageView)findViewById(R.id.foto);
        titulo=(TextView)findViewById(R.id.titulo);
        posicion=(TextView)findViewById(R.id.posicion);
        duracion=(TextView)findViewById(R.id.duracion);
        barra=(SeekBar)findViewById(R.id.barra);
        rutaDescargas= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        rutaImagenes= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, MIS_PERMISOS);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MIS_PERMISOS: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    inicializarAudio("mpthreetest.mp3");
                } else {
                    Toast.makeText(getApplicationContext(),"Permiso Denegado",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.play) {
            mp.start();
            Toast.makeText(MainActivity.this, "sonando", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.stop) {
            mp.stop();
            return true;
        }
        if (id == R.id.Camara) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            return true;
        }
        if (id == R.id.Salvar) {
            Bitmap b = ((BitmapDrawable)foto.getDrawable()).getBitmap();
            guardarImagen(b,"foto");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            foto.setImageBitmap(imageBitmap);
        }
    }

    private void guardarImagen(Bitmap b,String nombre){
        File imagen=new File(rutaImagenes,nombre+".jpg");
        FileOutputStream os=null;
        try
        {
            os=new FileOutputStream(imagen);
            b.compress(Bitmap.CompressFormat.JPEG,100,os);
        }catch (Exception e){

        }finally {
            try {
                os.close();
            }
            catch (Exception e2){

            }
        }
    }

    private void inicializarAudio(String nombre){
        //mp=MediaPlayer.create(this,R.raw.strauss);
        String fichero= rutaDescargas+"/"+nombre;
        mp=new MediaPlayer();
        try {
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(fichero);
            mp.prepare();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        tiempoFin=mp.getDuration();
        tiempoActual=mp.getCurrentPosition();
        duracion.setText(String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes((long) tiempoFin),
                TimeUnit.MILLISECONDS.toSeconds((long) tiempoFin) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                        tiempoFin)))
        );
        posicion.setText(String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes((long) tiempoActual),
                TimeUnit.MILLISECONDS.toSeconds((long) tiempoActual) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                        tiempoActual)))
        );
        barra.setMax((int)tiempoFin);
        barra.setProgress((int)tiempoActual);
        titulo.setText(nombre);
        manejador.postDelayed(actualizarBarra,100);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
            }
        });
    }

    private Runnable actualizarBarra = new Runnable() {
        public void run() {
            tiempoActual = mp.getCurrentPosition();
            posicion.setText(String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes((long) tiempoActual),
                    TimeUnit.MILLISECONDS.toSeconds((long) tiempoActual) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                            tiempoActual)))
            );
            barra.setProgress((int)tiempoActual);
            manejador.postDelayed(this, 100);
        }
    };
}
