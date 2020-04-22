package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ProtocolException;

import javax.net.ssl.HttpsURLConnection;

public class conexionBDWebService extends Worker {

    public conexionBDWebService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String valor= getInputData().getString("token");

        //Creamos la conexion segura con la base de datos
        HttpsURLConnection urlConnection= GeneradorConexionesSeguras.getInstance()
                .crearConexionSegura(getApplicationContext(),"https://134.209.235.115/jgutierrez053/WEB/insert.php");

        //Creamos un JSON con los parametros que le vamos a pasar y se los añadimos a nuestra conexion
        JSONObject parametrosJSON = new JSONObject();
        parametrosJSON.put("token", valor);

        try {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type","application/json");

            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametrosJSON.toString());
            out.close();

            //ejecutamos la conexion y recogemos el codigo que nos devuelve
            int statusCode = urlConnection.getResponseCode();
            Log.i("AAA",statusCode+"");

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
