package com.am.trendo.ui.saledetails;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.am.trendo.R;
import com.am.trendo.model.Sale;
import com.am.trendo.databinding.ActivitySaleDetailsBinding;
import com.am.trendo.repo.RepoFactory;
import com.am.trendo.ui.base.BaseActivity;
import com.am.trendo.utils.AlarmUtils;
import com.am.trendo.utils.LocationUtils;
import com.bumptech.glide.Glide;
import com.google.firebase.database.core.Repo;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SaleDetailsActivity extends BaseActivity {

    private static final String ARG_SALE = "sale";
    private static final int PENDING = 0;
    private static final int ADDED_TO_FAVORITES = 1;
    private static final int NOT_IN_FAVORITES = -1;
    private Sale mSale;
    private ActivitySaleDetailsBinding mBinding;
    private Snackbar mSnackbar;
    private int isFavorite = PENDING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sale_details);
        setSupportActionBar(mBinding.toolbar);
        showBackArrow();
        setTitle("");

        if (getIntent() != null) {
            mSale = getIntent().getParcelableExtra(ARG_SALE);
        }

        if (mSale == null) {
            finish();
            return;
        }

        initUI();

    }

    private void initUI() {
        Glide.with(this)
                .load(mSale.getImageUrl())
                .into(mBinding.saleImg);
        mBinding.endDateTv.setText(DateUtils.getRelativeDateTimeString(this,
                mSale.getEndDate(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
        mBinding.descriptionTv.setText(mSale.getDescription());
        mBinding.lovesTv.setText(getString(R.string.love_count, String.valueOf(mSale.getLoveCount())));
        mBinding.distanceTv.setText(LocationUtils.getStringDistance(mSale.getDistance()));
        mBinding.storeNameTv.setText(mSale.getStoreName());

        RepoFactory.getDataRepo()
                .isFavorite(this, mSale.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        isFavorite = aBoolean ? 1 : -1;
                        updateLoveFabImage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });

        mBinding.putAlarmBtn.setOnClickListener(view -> {
            RepoFactory.getDataRepo()
                    .addSaleAlarm(this, mSale);
            Toast.makeText(this, R.string.alarm_added, Toast.LENGTH_SHORT).show();
        });
        mBinding.showDirectionsBtn.setOnClickListener(view -> {
            openDirections();
        });
        mBinding.loveFab.setOnClickListener(view -> {
            if (isFavorite == ADDED_TO_FAVORITES) {
                RepoFactory.getDataRepo()
                        .removeFavorite(mSale, view.getContext())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                isFavorite = NOT_IN_FAVORITES;
                                showSnackbar(getString(R.string.removed_from_favorites));
                                updateLoveFabImage();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        });
            } else if (isFavorite == NOT_IN_FAVORITES) {
                RepoFactory.getDataRepo()
                        .addToFavorite(mSale, this)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                isFavorite = ADDED_TO_FAVORITES;
                                showSnackbar(getString(R.string.added_to_favorites));
                                updateLoveFabImage();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        });
            }
        });
    }

    private void openDirections() {
        Uri gmmIntentUri = Uri.parse(getString(R.string.geo_map,String.valueOf(mSale.getLat()),
                String.valueOf(mSale.getLng())));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public static Intent getIntentForSale(Context context, Sale sale) {
        Intent intent = new Intent(context, SaleDetailsActivity.class);
        intent.putExtra(ARG_SALE, sale);
        return intent;
    }

    public void showSnackbar(String txt) {
        if (mSnackbar != null) mSnackbar.dismiss();
        mSnackbar = Snackbar.make(mBinding.containerLayout, txt, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }
    private void updateLoveFabImage(){
        if (isFavorite == ADDED_TO_FAVORITES)
            mBinding.loveFab.setImageResource(R.drawable.ic_favorite_black_24dp);
        else
            mBinding.loveFab.setImageResource(R.drawable.ic_favorite_border_black_24dp);

    }
}
