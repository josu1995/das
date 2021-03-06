package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ProtocolException;

import javax.net.ssl.HttpsURLConnection;

public class Registro extends AppCompatActivity {
    Bd GestorBD = new Bd(this,"biblioteca",null,4);
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        firebaseAuth = FirebaseAuth.getInstance();

        Button registro = findViewById(R.id.newRegistro);

        final EditText usuario = findViewById(R.id.newUsuario);
        final EditText pass = findViewById(R.id.newPass);
        final EditText pass2 = findViewById(R.id.newPass2);

        //Al darle al boton registrar se hacen una serie de comprobaciones
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si alguno de los campos esta vacio mostramos un dialogo.
                if(usuario.getText().toString().length() == 0 || pass.getText().toString().length() == 0 ){
                    DialogFragment dialogo = new AlertDialogDatos();
                    dialogo.show(getSupportFragmentManager(),"datos");
                    usuario.getText().clear();
                    pass.getText().clear();
                    pass2.getText().clear();
                    //Si las contraseñas no coinciden mostramos un dialogo
                }else if(!pass.getText().toString().equals(pass2.getText().toString())){
                    DialogFragment dialogo = new AlertDialogPass();
                    dialogo.show(getSupportFragmentManager(),"registro");
                    pass.getText().clear();
                    pass2.getText().clear();
                }else{
                    //Si las comprobaciones son correctas miramos que el usuario no este ya registrado, con
                    //ese nombre de usuario
                    //Este sirve para llamar a firebase y registrar el usuario.
                    firebaseAuth.createUserWithEmailAndPassword(usuario.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //si se registra
                                    if(task.isSuccessful()){
                                        Consultas.registrarUsuario(usuario.getText().toString(), pass.getText().toString(),GestorBD);
                                        Toast.makeText(Registro.this,"Se ha registrado el usuario con el email: "+ usuario.getText().toString(),Toast.LENGTH_LONG).show();

                                        NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getBaseContext(), "RegistroOk");

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            NotificationChannel elCanal = new NotificationChannel("RegistroOk", "NombreCanal", NotificationManager.IMPORTANCE_HIGH);
                                            elCanal.setDescription("Descripción del canal");
                                            elCanal.enableLights(true);
                                            elCanal.setLightColor(Color.RED);
                                            elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                                            elCanal.enableVibration(true);
                                            elManager.createNotificationChannel(elCanal);
                                        }
                                        elBuilder.setSmallIcon(android.R.drawable.stat_sys_warning)
                                                .setContentTitle("Registro")
                                                .setContentText("El registro se completó correctamente.")
                                                .setVibrate(new long[]{0, 1000, 500, 1000})
                                                .setAutoCancel(true);

                                        elManager.notify(1, elBuilder.build());

                                        //Llamamos a firebase para recoger el token de este dispitivo
                                        //Una vez lo tenemos llamamos al workmanager que conecta con nuestra base de datos
                                        //pasando el token
                                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                if(!task.isSuccessful()){
                                                    return;
                                                }

                                                String token = task.getResult().getToken();
                                                Data datos = new Data.Builder()
                                                        .putString("token",token)
                                                        .build();

                                                OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(datos).build();
                                                WorkManager.getInstance(Registro.this).getWorkInfoByIdLiveData(otwr.getId())
                                                        .observe(Registro.this, new Observer<WorkInfo>() {
                                                            @Override
                                                            public void onChanged(WorkInfo workInfo) {
                                                                if(workInfo != null && workInfo.getState().isFinished()){

                                                                }
                                                            }
                                                        });
                                                WorkManager.getInstance(Registro.this).enqueue(otwr);
                                            }
                                        });

                                        //Volvemos a la pantalla de login
                                        Intent i = new Intent(getApplicationContext(),Login.class);
                                        startActivity(i);
                                        finish();
                                        //Diferentes excepcion personalizadas dependiendo el error que nos de.
                                    }else{
                                        if(task.getException().getMessage().equals("The email address is badly formatted.")){
                                            Toast.makeText(Registro.this,"El email no tiene un formato correcto",Toast.LENGTH_LONG).show();
                                        }else if(task.getException().getMessage().equals("The email address is already in use by another account.")) {
                                            Toast.makeText(Registro.this, "Ya existe una cuenta con este usuario", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(Registro.this, "La contraseña es incorrecta. Debe tener minimo 6 carácteres", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                }
                            });
                }
            }
        });
    }
}
