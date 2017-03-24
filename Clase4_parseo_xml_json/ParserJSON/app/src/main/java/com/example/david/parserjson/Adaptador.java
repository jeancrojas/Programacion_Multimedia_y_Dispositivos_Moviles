package com.example.david.parserjson;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by david on 7/3/17.
 */

public class Adaptador extends BaseAdapter {
    ArrayList<Capitulo> capitulos;
    Context c;

    public Adaptador(ArrayList<Capitulo> capitulos, Context c) {
        this.capitulos = capitulos;
        this.c = c;
    }

    @Override
    public int getCount() {
        return capitulos.size();
    }

    @Override
    public Object getItem(int position) {
        return capitulos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return capitulos.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflador=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflador.inflate(R.layout.item_lista,parent,false);
        final TextView titulo=(TextView)v.findViewById(R.id.titulo);
        titulo.setText(capitulos.get(position).getTitulo());
        TextView cap=(TextView)v.findViewById(R.id.capitulo);
        cap.setText(capitulos.get(position).getNum());
        TextView fecha=(TextView)v.findViewById(R.id.fecha);
        fecha.setText(capitulos.get(position).getFecha());
        TextView puntos=(TextView)v.findViewById(R.id.Valoracion);
        puntos.setText(capitulos.get(position).getPuntos());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.imdb.com/title/"+capitulos.get(position).imdbID));
                c.startActivity(i);
            }
        });
        return v;
    }

}
