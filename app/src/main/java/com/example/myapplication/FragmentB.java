package com.example.myapplication;


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
public class FragmentB extends Fragment {
    ListView lv;
    ArrayAdapter<String> adapter;
    String []datos;
    ArrayList<Integer> ids = new ArrayList<Integer>();
    ArrayList<String> libros;
    View view;

    public FragmentB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return  view = inflater.inflate(R.layout.fragment_fragment_b,container,false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    //Recibimos un id de un usuario y miramos sus libros para mostrar
    public void hacerAlgo(int id) {
        libros = new ArrayList<String>();

        Log.i("DEBUG", "Args: "+(getActivity() == null));


        final Bd GestorBD = new Bd(getActivity(),"biblioteca",null,3);
        String[] args = {Integer.toString(id)};

        Cursor cu = Consultas.getLibrosUsuario(args,GestorBD);
        while (cu.moveToNext()){
            int m= cu.getInt(1);
            String [] arg = {Integer.toString(m)};
            Cursor c = Consultas.getLibroById(arg,GestorBD);
            c.moveToNext();
            String a = c.getString(1);
            libros.add(a);

        }

        cu.close();


        //Mostrar los libros por pantalla
        datos = libros.toArray(new String[libros.size()]);
        lv = (ListView) view.findViewById(R.id.listViewLibros);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,datos);
        lv.setAdapter(adapter);
    }
}
