package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class AdaptadorListView extends BaseAdapter {
    private Context contexto;
    private LayoutInflater inflater;
    private String[] datos;

    private double[] puntuaciones;
    public AdaptadorListView(Context pcontext, String[] pdatos, double[]ppuntuaciones) {
        contexto = pcontext;
        datos = pdatos;
        puntuaciones=ppuntuaciones;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datos.length;
    }

    @Override
    public Object getItem(int position) {
        return datos[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view=inflater.inflate(R.layout.listado,null);
        TextView nombre= (TextView) view.findViewById(R.id.nombreLibro);
        RatingBar barra= (RatingBar) view.findViewById(R.id.ratingBar);
        nombre.setText(datos[i]);
        barra.setRating((float)puntuaciones[i]);

        return view;
    }
}
