package com.am.trendo.data.network;

import com.am.trendo.model.Sale;

import java.util.List;

import io.reactivex.Single;

public interface FirebaseDB {
    Single<List<Sale>> getTopSales();
    Single<Sale> getSaleById(String saleId);
}
