package com.am.trendo.ui.home.nearby;

import android.Manifest;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.pm.PackageManager;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.am.trendo.model.Sale;
import com.am.trendo.repo.DataRepo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NearbyViewModel extends AndroidViewModel {

    private FusedLocationProviderClient mFusedLocationClient;

    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private ObservableBoolean isError = new ObservableBoolean(false);
    private ObservableField<String> errorMsg = new ObservableField<>();

    private MutableLiveData<List<Sale>> salesLiveData = new MutableLiveData<>();

    private DataRepo dataRepo;

    public NearbyViewModel(@NonNull Application application, DataRepo dataRepo) {
        super(application);
        this.dataRepo = dataRepo;

        initData();
    }

    public void initData() {
        isLoading.set(true);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplication());
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(location -> {
                    dataRepo.getNearbySales(location.getResult())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new SingleObserver<List<Sale>>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(List<Sale> sales) {
                                    isLoading.set(false);
                                    salesLiveData.setValue(sales);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }
                            });
                });


    }

    private void getLocation() {

    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public ObservableBoolean getIsError() {
        return isError;
    }

    public ObservableField<String> getErrorMsg() {
        return errorMsg;
    }

    public LiveData<List<Sale>> getSalesLiveData() {
        return salesLiveData;
    }
}
