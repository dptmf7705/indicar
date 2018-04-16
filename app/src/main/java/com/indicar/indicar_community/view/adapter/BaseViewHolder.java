package com.indicar.indicar_community.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yeseul on 2018-04-17.
 */

public class BaseViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {

    protected B binding;

    public BaseViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
