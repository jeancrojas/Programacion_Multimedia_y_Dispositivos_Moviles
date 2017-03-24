package com.example.david.dualactivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;



public class Objeto implements Serializable {

    //--- EL SERIALVERISON debe ser distinto para cada clase serializable
    private static final long serialVersionUID = 1L;

    private int valor;
    private String titulo;
    private Bitmap icono;

    public Objeto(int valor, String titulo, Bitmap icono) {
        this.valor = valor;
        this.titulo = titulo;
        this.icono = icono;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Bitmap getIcono() {
        return icono;
    }

    public void setIcono(Bitmap icono) {
        this.icono = icono;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    // Hay que definir los metodos readObject y writeObject
    // y passar los datos de la classe que queremos serializar.
    // las variables no basicas como los bitmaps hay que meterlos
    // en un array de Bytes.

    private void readObject(ObjectInputStream is) throws ClassNotFoundException, IOException {
        valor=is.readInt();
        titulo=(String)is.readObject();
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        int b;
        while((b = is.read()) != -1)
            os.write(b);
        byte[] bitmapBytes=os.toByteArray();
        icono = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }

    private void writeObject(ObjectOutputStream os) throws IOException {
        os.writeInt(valor);
        os.writeObject(titulo);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        icono.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] imageByteArray = stream.toByteArray();
        os.write(imageByteArray,0,imageByteArray.length);
    }
}
