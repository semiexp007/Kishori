package com.uietsocial.kishori.Notification;

import android.app.NotificationManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.uietsocial.kishori.R;

import org.jetbrains.annotations.NotNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title=remoteMessage.getNotification().getTitle();
        String body=remoteMessage.getNotification().getBody();

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"CHAT");

        builder.setContentTitle(title);
        builder.setContentTitle(body);
        builder.setSmallIcon(R.drawable.smallicon);

        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1000,builder.build());
    }


}
