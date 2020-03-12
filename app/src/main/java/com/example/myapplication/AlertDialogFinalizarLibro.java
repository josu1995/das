package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

public class AlertDialogFinalizarLibro extends DialogFragment {
    @NonNull
    @Override
    //Dialogo para mostrar un mensaje para informar y si pulsa en Si cogera los datos de las preferencias y borrara los antiguos
    //Y nos ira a una nueva actividad y cerraremos la anterior
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Libro Finalizado");
        builder.setMessage("Ha finalizado el libro.Si continua irá a la pagina de valorar libro y perdera todos sus datos.¿Desea continuar?");


        builder.setNeutralButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor= prefs.edit();

                Intent i = new Intent(getActivity().getApplicationContext(), AnadirValoracion.class);
                i.putExtra("libro", prefs.getString("libro",null));
                editor.putString("libro",null);
                editor.putString("pagina",null);
                editor.putString("comentarios",null);
                editor.apply();
                startActivity(i);
                getActivity().finish();
            }
        });

        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
