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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandar_mail);

       /* Toolbar barra = findViewById(R.id.toolbar);
        setSupportActionBar(barra);*/

        Button enviar = findViewById(R.id.enviar);
        final EditText emailE = findViewById(R.id.email);
        final EditText asuntoE = findViewById(R.id.asunto);


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailE.getText().toString();
                String asunto = asuntoE.getText().toString();
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+email));
                i.putExtra(Intent.EXTRA_SUBJECT,asunto);


                startActivity(Intent.createChooser(i,"Título del chooser"));
            }
        });

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        /*switch (id){

        }
        return super.onOptionsItemSelected(item);
    }*/
}
