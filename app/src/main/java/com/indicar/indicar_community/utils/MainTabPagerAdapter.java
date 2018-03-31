package com.indicar.indicar_community.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.indicar.indicar_community.view.fragment.CommunityFragment;
import com.indicar.indicar_community.view.fragment.ProfileFragment;
import com.indicar.indicar_community.view.fragment.StoreFragment;
import com.indicar.indicar_community.view.fragment.TunningFragment;

/**
 * Created by yeseul on 2018-03-24.
 */

public class MainTabPagerAdapter extends FragmentStatePagerAdapter {
    private final static int TAB_COUNT = 4;

    private Context context;

    public MainTabPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new TunningFragment();
                break;
            case 1:
                fragment = new CommunityFragment();
                break;
            case 2:
                fragment = new StoreFragment();
                break;
            case 3:
                fragment = new ProfileFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
