package com.am.trendo.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    protected List<T> items;

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        switch (viewType) {
            case VIEW_TYPE_EMPTY:
                view = inflater.inflate(getEmptyLayoutId(), viewGroup, false);
                return new EmptyHolder(view);
            case VIEW_TYPE_NORMAL:
            default:
                view = inflater.inflate(getLayoutId(), viewGroup, false);
                return getViewHolderObject(view);
        }
    }

    protected abstract int getLayoutId();

    protected abstract int getEmptyLayoutId();

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemViewType(int position) {
        if (items != null && !items.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (items != null && items.size() > 0) {
            return items.size();
        } else {
            return 1;
        }
    }

    class EmptyHolder extends BaseViewHolder {

        public EmptyHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
        }
    }

    protected abstract BaseViewHolder getViewHolderObject(View view);

}
