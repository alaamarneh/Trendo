package com.am.trendo.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.am.trendo.model.Sale;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface SaleDao {
    @Query("SELECT * FROM sales")
    LiveData<List<Sale>> getAllFavorites();

    @Delete
    void delete(Sale sale);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSale(Sale sale);

    @Query("SELECT * FROM sales WHERE id = :saleId")
    Single<List<Sale>> getSaleCount(String saleId);
}
