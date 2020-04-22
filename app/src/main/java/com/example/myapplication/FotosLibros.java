package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


public class FotosLibros extends AppCompatActivity {
    Bd GestorBD = new Bd(this,"biblioteca",null,4);
    private static final int CODIGO_FOTO = 131 ;
    private static final int CODIGO_GALERIA = 130;
    private StorageReference mStorageRef;
    ImageView elImageView;
    Uri imagenSeleccionada = null;
    Bitmap laminiatura = null;
    ProgressDialog mProgressDialog;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos_libros);

        //Creamos un pequeño anuncio el cual se nos mostrara en la parte superior de la ventana
        mStorageRef = FirebaseStorage.getInstance().getReference();
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Button elegir = findViewById(R.id.elegirFoto);
        Button movil = findViewById(R.id.fotoMovil);
        Button subir = findViewById(R.id.subirFoto);
        elImageView = findViewById(R.id.foto);
        mProgressDialog = new ProgressDialog(this);

        mAdView.setAdListener(new AdListener(){




        });

        elegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imagen de galeria
                Intent elIntentGal = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(elIntentGal, CODIGO_GALERIA);

            }
        });

        movil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent elIntentFoto= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (elIntentFoto.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(elIntentFoto, CODIGO_FOTO);
                }
            }
        });
        //Cuando le damos a subir comprobamos que no esta vacia la imagen.
        //Si no subiremos la foto ya sea de la camara o del movil
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(laminiatura != null){
                    //El process dialog nos permite mostrar un texto el cual no se cerrara hasta que se acabe la subida
                    mProgressDialog.setTitle("Subiendo...");
                    mProgressDialog.setMessage("La foto se esta subiendo.");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();

                    //Hacemos la imagen mas pequeña
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    laminiatura.compress(Bitmap.CompressFormat.JPEG,100,baos);

                    byte[] datos = baos.toByteArray();
                    String nombre =laminiatura.getGenerationId()+".jpg";
                    StorageReference filePath = mStorageRef.child(nombre);
                    UploadTask uploadTask = filePath.putBytes(datos);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                            Consultas.registrarUri(nombre,GestorBD);
                            mProgressDialog.dismiss();
                            Toast.makeText(FotosLibros.this,"La foto se subió correctamente",Toast.LENGTH_SHORT).show();

                        }
                    });

                }else if(imagenSeleccionada != null){
                    mProgressDialog.setTitle("Subiendo...");
                    mProgressDialog.setMessage("La foto se esta subiendo.");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();

                    String nombre =imagenSeleccionada.getLastPathSegment()+".jpg";
                    Log.i("AAA",imagenSeleccionada.getLastPathSegment());
                    StorageReference filePath = mStorageRef.child(nombre);
                    filePath.putFile(imagenSeleccionada).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //guardamos la uri en nuestra base de datos local.
                            Consultas.registrarUri(nombre,GestorBD);
                            mProgressDialog.dismiss();
                            Toast.makeText(FotosLibros.this,"La foto se subió correctamente",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    });

                }else{
                    Toast.makeText(FotosLibros.this,"Seleccione o saque una foto",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    //Vemos si la foto esta cogida desde la galeria o desde la camara
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_GALERIA && resultCode == RESULT_OK) {
            laminiatura=null;
            imagenSeleccionada = data.getData();
            elImageView.setImageURI(imagenSeleccionada);
        }
        if (requestCode == CODIGO_FOTO && resultCode == RESULT_OK) {
            imagenSeleccionada=null;
            Bundle extras = data.getExtras();
            laminiatura = (Bitmap) extras.get("data");
            elImageView.setImageBitmap(laminiatura);

        }
    }
}
