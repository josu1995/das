package com.example.myapplication;


import android.app.NotificationManager;

import android.util.Log;



import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onNewToken(String s) {

        super.onNewToken(s);
        Log.i("NEW_TOKEN",s);

    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

    }


}
