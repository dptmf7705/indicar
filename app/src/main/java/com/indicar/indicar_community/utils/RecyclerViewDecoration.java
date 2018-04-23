package com.indicar.indicar_community.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yeseul on 2018-04-23.
 */

public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
    private final int divHeight;

    public RecyclerViewDecoration(int divHeight){
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = divHeight;
    }

}
