package com.am.trendo.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.am.trendo.R;
import com.am.trendo.model.Sale;
import com.am.trendo.utils.DateUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.target.Target;

import java.util.UUID;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    static void updateWidgetSale(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId, Sale sale){

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

        views.setTextViewText(R.id.day_tv, DateUtils.getDay(sale.getEndDate()));
        views.setTextViewText(R.id.date_tv, DateUtils.getMonthAndDayName(sale.getEndDate()));
        views.setTextViewText(R.id.title_tv, sale.getTitle());

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetService.startShowSale(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

