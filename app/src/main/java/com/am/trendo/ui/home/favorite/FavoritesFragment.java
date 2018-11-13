package com.am.trendo.ui.home.favorite;

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
import com.am.trendo.databinding.FavoritesFragmentBinding;
import com.am.trendo.repo.RepoFactory;
import com.am.trendo.ui.home.SalesAdapter;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel mViewModel;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }
    private FavoritesFragmentBinding mBinding;
    private SalesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.favorites_fragment, container, false);
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
        mViewModel = ViewModelProviders.of(this, new FavoritesViewModelFactory(getActivity().getApplication(),
                RepoFactory.getDataRepo())).get(FavoritesViewModel.class);

        mViewModel.getSalesLiveData()
                .observe(this, response -> {
                    mAdapter.setItems(response);
                });

    }

}
