package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificationManager notificationManager;
    private static final String ADMIN_CHANNEL_ID ="admin_channel";
    @Override
    public void onNewToken(String s) {
        /*
            En este método recibimos el 'token' del dispositivo.
            Lo necesitamos si vamos a comunicarnos con el dispositivo directamente.
        */
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
        /*
            A partir de aquí podemos hacer lo que queramos con el token como
            enviarlo al servidor para guardarlo en una B.DD.
            Nosotros no haremos nada con el token porque no nos vamos a comunicar con un sólo
            dispositivo.
         */
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // En este método recibimos el mensaje
        int notificationId = new Random().nextInt(60000);
        // Creamos la notificación en si
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)  //a resource for your custom small icon
                .setContentTitle(remoteMessage.getData().get("title")) //the "title" value you sent in your notification
                .setContentText(remoteMessage.getData().get("message")) //ditto
                .setAutoCancel(true)  //dismisses the notification on click
                .setSound(defaultSoundUri);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(){
        CharSequence adminChannelName = getString(R.string.app_name);
        String adminChannelDescription = getString(R.string.app_name);
        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

}
