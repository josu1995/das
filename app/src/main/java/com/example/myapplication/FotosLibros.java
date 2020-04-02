package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class FotosLibros extends AppCompatActivity {
    private static final int CODIGO_FOTO = 131 ;
    private static final int CODIGO_GALERIA = 130;
    private StorageReference mStorageRef;
    ImageView elImageView;
    Uri imagenSeleccionada = null;
    Bitmap laminiatura = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos_libros);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        Button elegir = findViewById(R.id.elegirFoto);
        Button movil = findViewById(R.id.fotoMovil);
        Button subir = findViewById(R.id.subirFoto);
        elImageView = findViewById(R.id.foto);

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

        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(laminiatura != null){
                    Log.i("AAA","lamini");
                }else if(imagenSeleccionada != null){
                    Log.i("AAA","img");
                }else{
                    Log.i("AAA","ninguna");
                    //descargar
                    StorageReference ref = mStorageRef.child("nombre.jpg");
                    ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task){
                            elImageView.setImageURI(task.getResult());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_GALERIA && resultCode == RESULT_OK) {
            laminiatura=null;
            imagenSeleccionada = data.getData();
            elImageView.setImageURI(imagenSeleccionada);
            String nombre =imagenSeleccionada.getLastPathSegment()+".jpg";
            Log.i("AAA",imagenSeleccionada.getLastPathSegment());
            StorageReference filePath = mStorageRef.child(nombre);
            filePath.putFile(imagenSeleccionada).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(FotosLibros.this,"La foto se subió correctamente",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.i("AAA",exception+"");
                }
            });

        }

        if (requestCode == CODIGO_FOTO && resultCode == RESULT_OK) {
            imagenSeleccionada=null;
            Bundle extras = data.getExtras();
            laminiatura = (Bitmap) extras.get("data");
            elImageView.setImageBitmap(laminiatura);
            //laminiatura.getGenerationId();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            laminiatura.compress(Bitmap.CompressFormat.JPEG,100,baos);

            byte[] datos = baos.toByteArray();

            String nombre = "asdasdsad.jpg";
            StorageReference filePath = mStorageRef.child(laminiatura.getGenerationId()+".jpg");
            UploadTask uploadTask = filePath.putBytes(datos);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){


                }
            });
        }
    }
}
