package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Bd extends SQLiteOpenHelper {

    public Bd(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS USUARIOS ('IdUsuario' INTEGER PRIMARY KEY AUTOINCREMENT  ,'Nombre' TEXT NOT NULL,'Pass' TEXT NOT NULL,UNIQUE('Nombre'))");
        db.execSQL("CREATE TABLE IF NOT EXISTS LIBRO  ('IdLibro' INTEGER PRIMARY KEY AUTOINCREMENT ,'NombreLibro' TEXT NOT NULL,UNIQUE('NombreLibro'))");
        db.execSQL("CREATE TABLE IF NOT EXISTS VALORACIONES ('IdUsuario' INTEGER NOT NULL,'IdLibro' INTEGER NOT NULL,'valoracion' DOUBLE NOT NULL,FOREIGN KEY ('IdUsuario') REFERENCES USUARIOS('IdUsuario'),FOREIGN KEY ('IdLibro') REFERENCES LIBROS('IdLibro'))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
