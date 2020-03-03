package com.example.myapplication;

//Clase singelton para guardar los datos del usuario logeado
public class Singelton {
    private static  Singelton miUsuario;
    private static  String nombreUsuario;
    private static  int idUsuario;

    public static Singelton getInstance() {
        if (miUsuario == null) {
            miUsuario = new Singelton();
        }
        return miUsuario;
    }

    private Singelton() {
    }
    public static void setNombreUsuario(String nombreUsu){
        Singelton.nombreUsuario = nombreUsu;
    }

    public static void setIdUsuario(int idUsuario) {
        Singelton.idUsuario = idUsuario;
    }

    public static String getNombreUsuario() {
        return nombreUsuario;
    }

    public static int getIdUsuario() {
        return idUsuario;
    }
}
