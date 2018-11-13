package com.am.trendo.ui.home.home;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.am.trendo.repo.DataRepo;
import com.am.trendo.ui.home.nearby.NearbyViewModel;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private DataRepo dataRepository;
    private Application application;

    public HomeViewModelFactory(Application application, DataRepo dataRepository) {
        this.dataRepository = dataRepository;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeViewModel(application, dataRepository);
    }
}
