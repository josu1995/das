package com.example.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AlertDialogLogin extends DialogFragment {
    @NonNull
    @Override
    //Dialogo para mostrar un mensaje para informar y si damos al boton de Registrar iremos a la parte de registrarnos
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Usuario y/o contraseña incorrectos");
        builder.setMessage("Por favor, revise los campos y si no es usuario puede registrarse");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNeutralButton("Registrarse", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getActivity().getApplicationContext(), Registro.class);
                startActivity(i);
            }
        });
        return builder.create();
    }
}
