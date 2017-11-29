package com.example.staffonechristian.seeksubstitute.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.staffonechristian.seeksubstitute.MainActivity;
import com.example.staffonechristian.seeksubstitute.R;
import com.example.staffonechristian.seeksubstitute.Splash;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.net.URLDecoder;

/**
 * Created by staffonechristian on 2017-02-25.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG="MyGcmListenerService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String image = remoteMessage.getNotification().getIcon();
        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();
        String sound = remoteMessage.getNotification().getSound();



        int notificationId;
        String key,personId,category,questionKey;
            System.out.println("remoteMessage.getData()-->"+remoteMessage.getData().toString());

            notificationId = Integer.parseInt(remoteMessage.getData().get("id"));

       // this.sendNotification(new NotificationData(image,notificationId,title,text,sound,key,personId,category,questionKey) );

            this.sendNotification(new NotificationData(image,notificationId,title,text,sound) );


        super.onMessageReceived(remoteMessage);
    }

    private void sendNotification(NotificationData notificationData) {

        Intent intent = new Intent(this,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        // Adds the back stack

        stackBuilder.addParentStack(Splash.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(notificationData.getId(),PendingIntent.FLAG_UPDATE_CURRENT );

        NotificationCompat.Builder notificationBuilder = null;
                try{
                    String channel_Id="channel01";
                    //not defined channel id
                    notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.substitute)
                    .setContentTitle(URLDecoder.decode(notificationData.getTitle(),"UTF-8"))
                    .setContentText(URLDecoder.decode(notificationData.getTextMessage(),"UTF-8"))
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

        if (notificationBuilder != null) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationData.getId(), notificationBuilder.build());
        } else {
            Log.d(TAG, "notificationBuilder is null");
        }
    }


}
