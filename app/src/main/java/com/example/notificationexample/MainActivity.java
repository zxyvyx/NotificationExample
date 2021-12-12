package com.example.notificationexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private final String CHANNEL_ID = "personal_notification";
    public static final int NOTIFICATION_ID = 001;
    public static final String TXT_REPLY = "text_reply";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayNotification(View view) {
        createNotificationChannel();

        int max_progress = 100;
        int current_progress = 0;

        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.ic_download)
                .setContentTitle("File Transfer")
                .setContentText("Downloading…")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setProgress(0, 0, true);
        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, notificationBuilder.build());

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    int count = 0;
                    while (count <= 100) {
                        count += 10;
                        sleep(1000);
//                        notificationBuilder.setProgress(max_progress, count,false);
//                        notificationManagerCompat.notify(NOTIFICATION_ID, notificationBuilder.build());
                    }
                    notificationBuilder.setContentText("Download Complete")
                            .setProgress(0, 0, false);
                    notificationManagerCompat.notify(NOTIFICATION_ID, notificationBuilder.build());
                } catch (InterruptedException error) { }
            }
        };
        thread.start();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence charSequenceName = "Personal Notifications";
            String description = "Include all the personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, charSequenceName, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService((NOTIFICATION_SERVICE));
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}