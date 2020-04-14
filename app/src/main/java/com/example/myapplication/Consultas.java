package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Consultas {

    //Obtener un usuario
     public static Cursor getLogin(String [] args ,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("USUARIOS",null,"Nombre=? and Pass=?",args,null,null,null);
    }
    //Ver si el nombre de usuario esta cogido
    public static Cursor getUserRegistro(String [] args ,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("USUARIOS",null,"Nombre=?",args,null,null,null);
    }

    //Añadir un nuevo usuario
    public static void registrarUsuario(String usuario,String pass,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        ContentValues nuevo = new ContentValues();
        nuevo.put("Nombre", usuario);
        nuevo.put("Pass", pass);
        bd.insert("USUARIOS", null, nuevo);
        bd.close();
    }
    //Añadir URI
    public static void registrarUri(String uri,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        ContentValues nuevo = new ContentValues();
        nuevo.put("NombreUri",uri);
        bd.insert("URIS",null,nuevo);
        bd.close();
    }

    //Buscar un libro por su nombre
    public static Cursor getLibro(String [] args ,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("LIBRO",null,"NombreLibro=?",args,null,null,null);
    }

    //Obetener la valoracion que ha hecho un usuario de un libro
    public static Cursor getValoracionUsuarioLibro(String [] args ,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("VALORACIONES",null,"IdUsuario=? and IdLibro=?",args,null,null,null);
    }

    //Añadir una nueva valoracion
    public static void anadirValoracion(int id,int idLibro,String valoracion,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        ContentValues nuevo = new ContentValues();
        nuevo.put("IdUsuario", id);
        nuevo.put("IdLibro", idLibro);
        nuevo.put("valoracion", Double.parseDouble(valoracion));
        bd.insert("VALORACIONES", null, nuevo);
        bd.close();
    }

    //Añadir un nuevo libro
    public static void anadirLibro(String libro,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        ContentValues nuevo = new ContentValues();
        nuevo.put("NombreLibro",libro);
        bd.insert("LIBRO",null,nuevo);
    }

    //Obtener el id de un libro
    public static Cursor getIdLibro(String [] args ,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("LIBRO",null,"NombreLibro=?",args,null,null,null);
    }

    //Obtener todas las valoraciones
    public static Cursor getValoraciones(Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("VALORACIONES",null,null,null,null,null,null);
    }

    //Obtener las uris
    public static Cursor getUris(Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("URIS",null,null,null,null,null,null);
    }

    //Obetner un libro por id
    public static Cursor getLibroById(String [] args ,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("LIBRO",null,"IdLibro=?",args,null,null,null);
    }

    //Obtener las valoraciones de un usuario
    public static Cursor getValoracionesUsuario(String [] args,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("VALORACIONES",null,"idUsuario=?",args,null,null,null);
    }

    //Modificar la valoracion de un libro
    public static void updateValoracion(String[] args,double val,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        ContentValues modificacion = new ContentValues();
        modificacion.put("valoracion",val);
        bd.update("VALORACIONES", modificacion, "IdUsuario=? AND IdLibro=?", args);

    }

    //Obtener los usuario excepto el logeado
    public static Cursor getUsuarios(String [] args,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("USUARIOS",null,"IdUsuario!=?",args,null,null,null);
    }

    //Obtener los libros que ha valorado un usuario
    public static Cursor getLibrosUsuario(String [] args,Bd GestorBD){
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();
        return bd.query("VALORACIONES",null,"IdUsuario=?",args,null,null,null);
    }


}
