package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MisValoraciones extends AppCompatActivity {
    Bd GestorBD = new Bd(this,"biblioteca",null,3);
    int id =Singelton.getIdUsuario();;
    double [] d;
    String[] arrayLibros;
    ArrayList<Integer> idLibros = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<String> nombreLibros = new ArrayList<String>();
        ArrayList<Double> valoraciones = new ArrayList<Double>();

        String [] args = {Integer.toString(id)};
        Cursor cu = Consultas.getValoracionesUsuario(args,GestorBD);
        while (cu.moveToNext()){
            int idLibro = cu.getInt(1);
            double val = cu.getDouble(2);
            idLibros.add(idLibro);
            String [] arg = {Integer.toString(idLibro)};
            Cursor c = Consultas.getLibroById(arg,GestorBD);
            c.moveToNext();
            String nombreLibro = c.getString(1);
            nombreLibros.add(nombreLibro);
            valoraciones.add(val);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_valoraciones);
        Button email = findViewById(R.id.botonEmail);


        arrayLibros = nombreLibros.toArray(new String[nombreLibros.size()]);
        d = new double[valoraciones.size()];
        for(int i =0;i<d.length;i++){
            d[i]=valoraciones.get(i) / 2;
        }

        ListView libros = findViewById(R.id.listadoMisValoraciones);
        AdaptadorListView eladap= new AdaptadorListView(getApplicationContext(),arrayLibros,d);
        libros.setAdapter(eladap);

        libros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long numero) {
                Intent i = new Intent(getApplicationContext(),CambiarValoracion.class);
                i.putExtra("NombreLibro",((TextView)view.findViewById(R.id.nombreLibro)).getText().toString());
                i.putExtra("idLibro",idLibros.get(position));
                startActivity(i);
                return false;
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MandarMail.class);
                i.putExtra("valoracion",d);
                i.putExtra("nombreLibros",arrayLibros);
                startActivity(i);
            }
        });
    }
}
