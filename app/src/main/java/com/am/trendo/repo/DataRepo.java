package com.am.trendo.repo;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.Location;

import com.am.trendo.model.Sale;

import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface DataRepo {
    Single<List<Sale>> getTopSales();
    Single<List<Sale>> getNearbySales(Location location);
    LiveData<List<Sale>> getFavorites(Context context);
    Single<Sale> getSaleById(String saleId);

    Completable addToFavorite(Sale sale, Context context);
    Completable removeFavorite(Sale sale, Context context);

    Single<Boolean> isFavorite(Context context, String saleId);

    void addSaleAlarm(Context context, Sale sale);
}
