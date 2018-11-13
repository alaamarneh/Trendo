package com.am.trendo.ui.home.nearby;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.am.trendo.repo.DataRepo;

public class NearbyViewModelFactory implements ViewModelProvider.Factory {
    private DataRepo dataRepository;
    private Application application;

    public NearbyViewModelFactory(Application application, DataRepo dataRepository) {
        this.dataRepository = dataRepository;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NearbyViewModel(application, dataRepository);
    }
}
