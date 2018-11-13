package com.am.trendo.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.trendo.R;
import com.am.trendo.model.Sale;
import com.am.trendo.ui.base.BaseAdapter;
import com.am.trendo.ui.base.BaseViewHolder;
import com.am.trendo.ui.saledetails.SaleDetailsActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalesAdapter extends BaseAdapter<Sale> {

    private Activity activity;

    public SalesAdapter() {
    }

    public SalesAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sale_list_item;
    }

    @Override
    protected int getEmptyLayoutId() {
        return R.layout.empty_sales;
    }

    @Override
    protected BaseViewHolder getViewHolderObject(View view) {
        return new SaleHolder(view);
    }

    class SaleHolder extends BaseViewHolder implements View.OnClickListener {
        @BindView(R.id.end_date_tv)
        TextView endDateTv;
        @BindView(R.id.store_name_tv)
        TextView storeNameTv;
        @BindView(R.id.sale_img)
        ImageView saleImg;

        public SaleHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            endDateTv.setText(DateUtils.getRelativeDateTimeString(itemView.getContext(),
                    items.get(position).getEndDate(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
            storeNameTv.setText(items.get(position).getStoreName());
            Glide.with(itemView.getContext())
                    .load(items.get(position).getImageUrl())
                    .into(saleImg);
        }

        @Override
        public void onClick(View view) {
            Intent intent = SaleDetailsActivity.getIntentForSale(view.getContext(), items.get(getAdapterPosition()));
            view.getContext().startActivity(intent);
            if (activity != null)
                activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }
}
