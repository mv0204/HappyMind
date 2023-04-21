package com.example.happymind;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        context.startService(new Intent(context,MusicService.class));
        Intent i = new Intent(context, PillReminder.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);

        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pill, null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeicon = bitmapDrawable.getBitmap();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Pills")
                .setSmallIcon(R.drawable.small_icon)
                .setContentTitle("Don't forget to take your Pills")
                .setContentText("GET YOUR PILLS NOW..")
                .setAutoCancel(true)
                .setLargeIcon(largeicon)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Give Proper Permission", Toast.LENGTH_SHORT).show();
            return;
        }
        notificationManagerCompat.notify(123, builder.build());
        Toast.makeText(context, "Heloooooooooooo", Toast.LENGTH_SHORT).show();


    }
}