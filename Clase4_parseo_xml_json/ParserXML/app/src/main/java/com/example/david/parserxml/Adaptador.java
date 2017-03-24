package com.example.david.parserxml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    ArrayList<Parte> partes;
    Context c;

    public Adaptador(ArrayList<Parte> partes, Context c) {
        this.partes = partes;
        this.c = c;
    }

    @Override
    public int getCount() {
        return partes.size();
    }

    @Override
    public Object getItem(int position) {
        return partes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return partes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflador=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflador.inflate(R.layout.item_lista,parent,false);
        final TextView nombre=(TextView)v.findViewById(R.id.nombre);
        nombre.setText(partes.get(position).getNombre());
        TextView precio=(TextView)v.findViewById(R.id.precio);
        precio.setText(String.valueOf(partes.get(position).getPrecio())+"€");
        TextView marca=(TextView)v.findViewById(R.id.marca);
        marca.setText(partes.get(position).getFabircante());
        TextView modelo=(TextView)v.findViewById(R.id.modelo);
        modelo.setText(partes.get(position).getModelo());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,nombre.getText(),Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
