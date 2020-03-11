package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MandarMail extends AppCompatActivity {
    double [] d = null;
    String[] arrayLibros = null;
    String texto = "Listado de libros: \n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandar_mail);
        Toolbar barra = findViewById(R.id.toolbar);
        setSupportActionBar(barra);

        //Recogemos las valoraciones del usuario
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String usuario= extras.getString("user");
            d = extras.getDoubleArray("valoracion");
            arrayLibros = extras.getStringArray("nombreLibros");
        }



        Button enviar = findViewById(R.id.enviar);
        final EditText emailE = findViewById(R.id.email);
        final EditText asuntoE = findViewById(R.id.asunto);

        //Añadimos los nombre de los libros y su valoracion sobre 10 a el texto que mandaremos
        for (int i =0;i<d.length;i++){
            texto = texto + "Libro: " + arrayLibros[i] + " valoracion: "+ d[i]*2 +"\n";
        }
        //Enviaremos las valoracion por correo
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailE.getText().toString();
                String asunto = asuntoE.getText().toString();
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+email));
                i.putExtra(Intent.EXTRA_SUBJECT,asunto);
                i.putExtra(Intent.EXTRA_TEXT, texto);

                startActivity(Intent.createChooser(i,"Mis valoraciones"));
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
