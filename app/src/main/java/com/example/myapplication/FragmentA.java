package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentA extends Fragment {
    ListView lv;
    ArrayAdapter<String> adapter;
    String []datos;
    ArrayList<Integer> ids = new ArrayList<Integer>();
    ArrayList<String> usuarios = new ArrayList<String>();
    View view;
    private listenerDelFragment elListener;


    public FragmentA() {
        // Required empty public constructor
    }

    public interface listenerDelFragment{
        void seleccionarElemento(int posicion);
    }

    public void seleccionarElemento(int posicion){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_a,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Buscamos en la base de datos todos los usuario excepto el logeado
        final Bd GestorBD = new Bd(getActivity(),"biblioteca",null,3);
        String[] args = {Integer.toString(Singelton.getIdUsuario())};
        Cursor cu = Consultas.getUsuarios(args,GestorBD);
        while (cu.moveToNext()){
            ids.add(cu.getInt(0));
            usuarios.add(cu.getString(1));
        }
        //Mostramos los usuarios en una lista
        datos = usuarios.toArray(new String[usuarios.size()]);
        lv = (ListView) view.findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,datos);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                elListener.seleccionarElemento(ids.get(position));
                return false;
            }
        });
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            elListener = (FragmentA.listenerDelFragment) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException("La clase " +context.toString() + "debe implementar listenerDelFragment");
        }
    }

}
