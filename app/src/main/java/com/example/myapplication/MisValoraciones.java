package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
        //Buscamos en base de datos las valoraciones que tiene el usuario logeado
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
        Toolbar barra = findViewById(R.id.toolbar);
        setSupportActionBar(barra);
        Button email = findViewById(R.id.botonEmail);

        DialogFragment dialogo = new AlertDialogEditarValoracion();
        dialogo.show(getSupportFragmentManager(),"editarValoracion");

        //pasamos los arraylist a listas para que nuestra listview personalizada la pueda recoger
        arrayLibros = nombreLibros.toArray(new String[nombreLibros.size()]);
        d = new double[valoraciones.size()];
        for(int i =0;i<d.length;i++){
            d[i]=valoraciones.get(i) / 2;
        }

        ListView libros = findViewById(R.id.listadoMisValoraciones);
        AdaptadorListView eladap= new AdaptadorListView(getApplicationContext(),arrayLibros,d);
        libros.setAdapter(eladap);

        //Cuando se pulsa una valoracion un rato iremos ala pantalla de modificar valoracion
        libros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long numero) {
                Intent i = new Intent(getApplicationContext(),CambiarValoracion.class);
                i.putExtra("NombreLibro",((TextView)view.findViewById(R.id.nombreLibro)).getText().toString());
                i.putExtra("idLibro",idLibros.get(position));
                startActivity(i);
                finish();
                return false;
            }
        });
        //Al darle al boton de email iremos a una nueva actividad para mandar un correo
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MandarMail.class);
                i.putExtra("valoracion",d);
                i.putExtra("nombreLibros",arrayLibros);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(getApplicationContext(),Login.class);
        startActivity(i);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
