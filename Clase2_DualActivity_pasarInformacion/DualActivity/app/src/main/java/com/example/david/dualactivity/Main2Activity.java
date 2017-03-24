package com.example.david.dualactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView tv;
    EditText et;
    ImageView iv;
    Button b;
    Objeto obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv=(TextView)findViewById(R.id.etiqueta2);
        iv=(ImageView)findViewById(R.id.imagen2);
        b=(Button)findViewById(R.id.boton2);
        et=(EditText)findViewById(R.id.caja2);
        obj=(Objeto)getIntent().getSerializableExtra("Objeto");
        tv.setText(obj.getTitulo());
        iv.setImageBitmap(obj.getIcono());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                obj.setTitulo(et.getText().toString());
                i.putExtra("Objeto",obj);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }
}
