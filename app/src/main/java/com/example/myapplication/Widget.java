package com.example.myapplication;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import io.paperdb.Paper;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    static String CLICK_ACTION = "CLICKED";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //Recibimos el titulo y el cuerpo de la otra actividad y montamos un widget estilo nota
        Intent i = new Intent(context,Widget.class);
        i.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,i,0);

        Paper.init(context);
        String titulo = Paper.book().read("titulo");
        String cuerpo = Paper.book().read("cuerpo");


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.appwidget_titulo, titulo);
        views.setTextViewText(R.id.appwidget_cuerpo, cuerpo);
        views.setOnClickPendingIntent(R.id.widgetLayout,pendingIntent);


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    //Si pinchamos en el widget nos mostrara una notificación
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getAction().equals(CLICK_ACTION)){
            Toast.makeText(context,"Para crear otra nota vuelva a entrar en la aplicación", Toast.LENGTH_SHORT).show();
        }
    }

}

