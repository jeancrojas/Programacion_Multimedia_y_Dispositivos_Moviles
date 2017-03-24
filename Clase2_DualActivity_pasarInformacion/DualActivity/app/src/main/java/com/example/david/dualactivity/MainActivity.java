package com.example.david.dualactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button b;
    EditText et;
    ImageView iv;
    Objeto obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=(Button)findViewById(R.id.boton1);
        et=(EditText)findViewById(R.id.caja1);
        iv=(ImageView)findViewById(R.id.imagen1);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj.setTitulo(et.getText().toString());
                Bitmap bmp=BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                obj.setIcono(bmp);
                Intent i=new Intent(getApplicationContext(),Main2Activity.class);
                i.putExtra("Objeto",obj);
                startActivityForResult(i,1);
            }
        });
        obj=new Objeto(1,"",null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1){
            if (resultCode==RESULT_OK){
                obj=(Objeto)data.getSerializableExtra("Objeto");
                et.setText(obj.getTitulo());

            }
            if (resultCode==RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"CANCELADO",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
