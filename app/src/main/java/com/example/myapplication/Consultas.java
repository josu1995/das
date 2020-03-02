package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Consultas {
     public static Cursor getLogin(String [] args ,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("USUARIOS",null,"Nombre=? and Pass=?",args,null,null,null);
    }

    public static Cursor getUserRegistro(String [] args ,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("USUARIOS",null,"Nombre=?",args,null,null,null);
    }

    public static void registrarUsuario(String usuario,String pass,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        ContentValues nuevo = new ContentValues();
        nuevo.put("Nombre", usuario);
        nuevo.put("Pass", pass);
        bd.insert("USUARIOS", null, nuevo);
        bd.close();
    }

    public static Cursor getLibro(String [] args ,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("LIBRO",null,"NombreLibro=?",args,null,null,null);
    }

    public static Cursor getValoracionUsuarioLibro(String [] args ,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("VALORACIONES",null,"IdUsuario=? and IdLibro=?",args,null,null,null);
    }

    public static void anadirValoracion(int id,int idLibro,String valoracion,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        ContentValues nuevo = new ContentValues();
        nuevo.put("IdUsuario", id);
        nuevo.put("IdLibro", idLibro);
        nuevo.put("valoracion", Double.parseDouble(valoracion));
        bd.insert("VALORACIONES", null, nuevo);
        bd.close();
    }

    public static void anadirLibro(String libro,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        ContentValues nuevo = new ContentValues();
        nuevo.put("NombreLibro",libro);
        bd.insert("LIBRO",null,nuevo);
    }

    public static Cursor getIdLibro(String [] args ,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("LIBRO",null,"NombreLibro=?",args,null,null,null);
    }

    public static Cursor getValoraciones(Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("VALORACIONES",null,null,null,null,null,null);
    }


    public static Cursor getLibroById(String [] args ,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("LIBRO",null,"IdLibro=?",args,null,null,null);
    }

    public static Cursor getValoracionesUsuario(String [] args,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("VALORACIONES",null,"idUsuario=?",args,null,null,null);
    }

    public static void updateValoracion(String[] args,double val,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        ContentValues modificacion = new ContentValues();
        modificacion.put("valoracion",val);
        bd.update("VALORACIONES", modificacion, "IdUsuario=? AND IdLibro=?", args);

    }

    public static Cursor getUsuarios(String [] args,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("USUARIOS",null,"IdUsuario!=?",args,null,null,null);
    }

    public static Cursor getLibrosUsuario(String [] args,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("VALORACIONES",null,"IdUsuario=?",args,null,null,null);
    }


}
