package com.am.trendo.ui.home.home;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.am.trendo.R;
import com.am.trendo.databinding.FragmentHomeBinding;
import com.am.trendo.repo.RepoFactory;
import com.am.trendo.ui.home.SalesAdapter;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private SalesAdapter mAdapter;
    private HomeViewModel mViewModel;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        initRecyclerView();
        return mBinding.getRoot();
    }

    private void initRecyclerView() {
        mBinding.salesRv.setLayoutManager(new GridLayoutManager(getContext(), getNumberOfColumn()));
        mBinding.salesRv.setHasFixedSize(true);
        mAdapter = new SalesAdapter(getActivity());
        mBinding.salesRv.setAdapter(mAdapter);
    }

    private int getNumberOfColumn() {
        return 1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, new HomeViewModelFactory(getActivity().getApplication(),
                RepoFactory.getDataRepo())).get(HomeViewModel.class);
        mBinding.setViewModel(mViewModel);

        mViewModel.getSalesLiveData()
                .observe(this, response -> {
                    mAdapter.setItems(response);
                });

    }

}
