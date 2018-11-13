package com.am.trendo.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.am.trendo.R;
import com.am.trendo.model.Sale;
import com.am.trendo.ui.saledetails.SaleDetailsActivity;

public final class NotificationUtils {
    private static final String SALE_ALARM_NOTIFICATION_CHANNEL_ID = "alarm-sale";
    private static final int SALE_PENDING_INTENT_ID = 1;

    public static void notifySaleAlarm(Context context, Sale sale){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    SALE_ALARM_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.sale_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, SALE_ALARM_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.dont_miss,sale.getTitle()))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.dont_miss,sale.getTitle())))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(saleAlarmIntent(context, sale))
                .setAutoCancel(true);

        // set the notification's priority to PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(SALE_PENDING_INTENT_ID, notificationBuilder.build());
    }

    private static PendingIntent saleAlarmIntent(Context context, Sale sale) {
        Intent startActivityIntent = SaleDetailsActivity.getIntentForSale(context, sale);
        return PendingIntent.getActivity(
                context,
                SALE_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
