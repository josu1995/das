package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Toolbar barra = findViewById(R.id.toolbar);
        setSupportActionBar(barra);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            Button finalizar = getActivity().findViewById(R.id.finalizarLibro);

            finalizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment dialogo = new AlertDialogFinalizarLibro();
                    dialogo.show(getFragmentManager(),"finalizarLibro");
                }
            });
        }
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i("AA","save");
        super.onSaveInstanceState(savedInstanceState);


    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("AA","restore");
        super.onRestoreInstanceState(savedInstanceState);

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