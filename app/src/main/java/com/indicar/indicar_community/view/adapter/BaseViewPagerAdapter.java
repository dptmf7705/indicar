package com.indicar.indicar_community.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by yeseul on 2018-04-16.
 */

public abstract class BaseViewPagerAdapter extends FragmentPagerAdapter {

    protected Context context;

    public BaseViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

}
