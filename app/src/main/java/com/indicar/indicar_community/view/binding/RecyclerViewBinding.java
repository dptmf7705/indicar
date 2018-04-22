package com.indicar.indicar_community.view.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.indicar.indicar_community.view.adapter.BaseRecyclerViewAdapter;

/**
 * Created by yeseul on 2018-04-22.
 */

public class RecyclerViewBinding {

    @BindingAdapter(value = {"adapter", "layoutManager", "onScroll", "onChildChange"})
    public static void bindRecyclerView(RecyclerView recyclerView,
                                        BaseRecyclerViewAdapter adapter,
                                        RecyclerView.LayoutManager layoutManager,
                                        RecyclerView.OnScrollListener onScrollListener,
                                        RecyclerView.OnChildAttachStateChangeListener onChildChangeListener){

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(onScrollListener);
        recyclerView.addOnChildAttachStateChangeListener(onChildChangeListener);
    }

    @BindingAdapter(value = {"adapter"})
    public static void bindRecyclerView(RecyclerView recyclerView,
                                        BaseRecyclerViewAdapter adapter) {

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }

}
