package com.am.trendo.ui.home.nearby;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.am.trendo.R;
import com.am.trendo.databinding.NearbyFragmentBinding;
import com.am.trendo.repo.RepoFactory;
import com.am.trendo.ui.home.SalesAdapter;

public class NearbyFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private NearbyViewModel mViewModel;
    private NearbyFragmentBinding mBinding;
    private SalesAdapter mAdapter;

    public static NearbyFragment newInstance() {
        return new NearbyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.nearby_fragment, container, false);
        initRecyclerView();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
        }

        return mBinding.getRoot();
    }

    private void initRecyclerView() {
        mBinding.salesRv.setLayoutManager(new GridLayoutManager(getContext(), getNumberOfColumn()));
        mBinding.salesRv.setHasFixedSize(true);
        mAdapter = new SalesAdapter();
        mBinding.salesRv.setAdapter(mAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mViewModel.initData();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private int getNumberOfColumn() {
        return 1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, new NearbyViewModelFactory(getActivity().getApplication(),
                RepoFactory.getDataRepo())).get(NearbyViewModel.class);
        mBinding.setViewModel(mViewModel);

        mViewModel.getSalesLiveData()
                .observe(this, response -> {
                    mAdapter.setItems(response);
                });

    }

}
