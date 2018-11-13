package com.am.trendo.repo;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.Location;
import android.os.SystemClock;

import com.am.trendo.data.database.AppDatabase;
import com.am.trendo.data.network.FirebaseFactory;
import com.am.trendo.model.Sale;
import com.am.trendo.ui.widget.WidgetService;
import com.am.trendo.utils.AlarmUtils;
import com.am.trendo.utils.LocationUtils;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class AppDataRepo implements DataRepo {


    private static AppDataRepo sInstance;

    public static AppDataRepo getInstance() {
        if (sInstance == null)
            sInstance = new AppDataRepo();
        return sInstance;
    }

    @Override
    public Single<List<Sale>> getTopSales() {
        return FirebaseFactory.getFirebaseDB()
                .getTopSales();
    }

    @Override
    public Single<List<Sale>> getNearbySales(Location location) {
        return FirebaseFactory.getFirebaseDB()
                .getTopSales()
                .map(sales -> { //sort by distance
                    for (Sale sale : sales) {
                        Location locationA = new Location("Location A");
                        locationA.setLatitude(sale.getLat());
                        locationA.setLongitude(sale.getLng());

                        sale.setDistance(LocationUtils.getDistance(location, locationA));
                    }

                    Collections.sort(sales);
                    return sales;
                });
    }


    @Override
    public LiveData<List<Sale>> getFavorites(Context context) {
        return AppDatabase.getInstance(context)
                .saleDao()
                .getAllFavorites();
    }

    @Override
    public Single<Sale> getSaleById(String saleId) {
        return FirebaseFactory.getFirebaseDB()
                .getSaleById(saleId);
    }


    @Override
    public Completable addToFavorite(Sale sale, Context context) {


        return Completable.fromAction(() -> {
            AppDatabase.getInstance(context)
                    .saleDao()
                    .addSale(sale);

            WidgetService.startShowSale(context);
        });
    }

    @Override
    public Completable removeFavorite(Sale sale, Context context) {
        return Completable.fromAction(() -> {
            AppDatabase.getInstance(context)
                    .saleDao()
                    .delete(sale);
        });
    }

    @Override
    public Single<Boolean> isFavorite(Context context, String saleId) {
        return AppDatabase.getInstance(context)
                .saleDao()
                .getSaleCount(saleId)
                .map(sales -> {
                    return !sales.isEmpty();
                });

    }

    @Override
    public void addSaleAlarm(Context context, Sale sale) {
        AlarmUtils.addSaleAlarm(context, SystemClock.elapsedRealtime() + 1000 * 5, sale.getId());
    }
}
