package com.am.trendo.ui.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.am.trendo.repo.RepoFactory;

public class WidgetService extends IntentService {
    private static final String ACTION_SHOW = "action.show";
    public WidgetService() {
        super("WidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;
        String action = intent.getAction();
        switch (action) {
            case ACTION_SHOW:
                handleActionShow();
                break;
        }
    }

    private void handleActionShow() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, AppWidget.class));

        RepoFactory.getDataRepo()
                .getFavorites(this)
                .observeForever(response -> {
                    if (response != null && !response.isEmpty()) {

                        for (int appWidgetId : appWidgetIds) {
                            AppWidget.updateWidgetSale(this, appWidgetManager, appWidgetId, response.get(0));
                        }
                    }
                });


    }

    public static void startShowSale(Context context) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.setAction(WidgetService.ACTION_SHOW);
        context.startService(intent);
    }
}
