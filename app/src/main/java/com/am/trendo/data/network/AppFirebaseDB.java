package com.am.trendo.data.network;

import android.support.annotation.NonNull;

import com.am.trendo.model.Sale;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Single;

public class AppFirebaseDB implements FirebaseDB {
    @Override
    public Single<List<Sale>> getTopSales() {
        return Single.create(emitter -> {
            List<Sale> sales = new ArrayList<>();
            ServerEndPoints.salesReference
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                            while (iterator.hasNext()) {
                                DataSnapshot ds = iterator.next();
                                Sale sale = ds.getValue(Sale.class);
                                sale.setId(ds.getKey());
                                sales.add(sale);
                            }
                            emitter.onSuccess(sales);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            emitter.onError(databaseError.toException());
                        }
                    });
        });
    }

    @Override
    public Single<Sale> getSaleById(String saleId) {
        return Single.create(emitter -> {
            ServerEndPoints.salesReference
                    .child(saleId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Sale sale = dataSnapshot.getValue(Sale.class);
                            sale.setId(dataSnapshot.getKey());
                            emitter.onSuccess(sale);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            emitter.onError(databaseError.toException());
                        }
                    });
        });
    }
}
