package com.am.trendo.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.am.trendo.model.Sale;
import com.am.trendo.repo.RepoFactory;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public final class AlarmUtils {

    public final static String ACTION_SALE = "action.sale";
    private static final int CODE_SALE = 1;

    public static void addSaleAlarm(Context context, long time, String saleId) {
        addAlarm(context, CODE_SALE, ACTION_SALE, time, saleId);
    }

    public static void addAlarm(Context context, int code, String action, long time, String id) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(action);
        intent.setData(Uri.parse(id));
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, code, intent, 0);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                time, alarmIntent);
    }


    public static class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;
            if (action.equals(ACTION_SALE)) {
                String saleId = intent.getData().toString();
                RepoFactory.getDataRepo()
                        .getSaleById(saleId)
                        .subscribe(new SingleObserver<Sale>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(Sale sale) {
                                NotificationUtils.notifySaleAlarm(context, sale);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        });
            }
        }
    }
}
