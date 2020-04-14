package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class AdaptadorListViewFotos  extends BaseAdapter {
    private Context contexto;
    private LayoutInflater inflater;
    private Uri[] imagenes;


    //constructura para crear la listview personalizado
    public AdaptadorListViewFotos(Context pcontext, Uri[] pimagenes) {
        contexto = pcontext;
        imagenes = pimagenes;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imagenes.length;
    }

    @Override
    public Object getItem(int position) {
        return imagenes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view=inflater.inflate(R.layout.fotos,null);

        ImageView imagen = (ImageView)view.findViewById(R.id.imagenes);
        Glide.with(view.getContext()).load(imagenes[position]).fitCenter().centerCrop().into(imagen);

        return view;
    }
}
