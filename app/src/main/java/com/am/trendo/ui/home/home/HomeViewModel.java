package com.am.trendo.ui.home.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.am.trendo.model.Sale;
import com.am.trendo.repo.DataRepo;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class HomeViewModel extends AndroidViewModel {
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private ObservableBoolean isError = new ObservableBoolean(false);
    private ObservableField<String> errorMsg = new ObservableField<>();

    private MutableLiveData<List<Sale>> salesLiveData = new MutableLiveData<>();

    private DataRepo dataRepo;

    public HomeViewModel(@NonNull Application application, DataRepo dataRepo) {
        super(application);
        this.dataRepo = dataRepo;

         initData();
    }

    private void initData(){
        isLoading.set(true);
        dataRepo.getTopSales()
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

                    }
                });
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
