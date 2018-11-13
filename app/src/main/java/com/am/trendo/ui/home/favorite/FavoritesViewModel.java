package com.am.trendo.ui.home.favorite;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.am.trendo.model.Sale;
import com.am.trendo.repo.DataRepo;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private ObservableBoolean isError = new ObservableBoolean(false);
    private ObservableField<String> errorMsg = new ObservableField<>();

    private LiveData<List<Sale>> salesLiveData;

    private DataRepo dataRepo;

    public FavoritesViewModel(@NonNull Application application, DataRepo dataRepo) {
        super(application);
        this.dataRepo = dataRepo;

        loadData();
    }

    private void loadData() {
        salesLiveData = dataRepo.getFavorites(getApplication().getApplicationContext());
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
