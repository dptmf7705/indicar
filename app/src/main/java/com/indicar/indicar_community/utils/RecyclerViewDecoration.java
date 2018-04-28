package com.indicar.indicar_community.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yeseul on 2018-04-23.
 */

public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
    private final int divTop;
    private final int divBottom;
    private final int divLeft;
    private final int divRight;

    public RecyclerViewDecoration(int divTop, int divBottom, int divLeft, int divRight) {
        this.divTop = divTop;
        this.divBottom = divBottom;
        this.divLeft = divLeft;
        this.divRight = divRight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = divBottom;
        outRect.top = divTop;
        outRect.left = divLeft;
        outRect.right = divRight;
    }

}
