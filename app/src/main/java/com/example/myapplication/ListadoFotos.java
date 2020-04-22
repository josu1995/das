package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.BackoffPolicy;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ListadoFotos extends AppCompatActivity {
    Bd GestorBD = new Bd(this,"biblioteca",null,4);
    private StorageReference mStorageRef;
    ArrayList<Uri> imagenes = new ArrayList<Uri>();
    ProgressDialog mProgressDialog;
    int cont = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_fotos);

        //buscamos todas las uris que tenemos en nuestra base de datos local
        //y con cada uri vamos a nuestro servidor de firebase y cogemos
        //esas fotos
        mProgressDialog = new ProgressDialog(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Cursor cu = Consultas.getUris(GestorBD);
        while (cu.moveToNext()) {
            mProgressDialog.setTitle("Cargando...");
            mProgressDialog.setMessage("Las fotos se estan cargando.");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            //CargarImg
            StorageReference ref = mStorageRef.child(cu.getString(1));
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    imagenes.add(uri);
                    cont--;
                    if(cont == 0) {
                        cargarImagenes();
                    }
                }
            });
            cont++;

        }


    }
    //una vez se han cargado todas las imagenes las meteemos en nuestro adapter para mostrarla en nuestra lista personalizada
    public void cargarImagenes(){
        Log.i("AAA","Cargando");
        Uri [] img = new Uri [imagenes.size()];
        for(int i =0;i<imagenes.size();i++){
            img[i]=imagenes.get(i);
        }
        ListView laLista = findViewById(R.id.listadoFotos);
        AdaptadorListViewFotos fotos = new AdaptadorListViewFotos(getApplicationContext(),img);
        laLista.setAdapter(fotos);

        mProgressDialog.dismiss();
    }
}
