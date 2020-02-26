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
    int id =0;
    double [] d = null;
    String[] arrayLibros = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<String> nombreLibros = new ArrayList<String>();
        ArrayList<Double> valoraciones = new ArrayList<Double>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String usuario= extras.getString("user");
            id = extras.getInt("id");
        }
        String [] args = {Integer.toString(id)};

        Cursor cu = Consultas.getValoracionesUsuario(args,GestorBD);
        while (cu.moveToNext()){
            int idLibro = cu.getInt(1);
            double val = cu.getDouble(2);
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

        libros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.i("AA", ((TextView)view.findViewById(R.id.nombreLibro)).getText().toString());
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
