package com.indicar.indicar_community.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by yeseul on 2018-04-16.
 */

public abstract class BaseViewPagerAdapter extends FragmentStatePagerAdapter {

    protected Context context;

    public BaseViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
