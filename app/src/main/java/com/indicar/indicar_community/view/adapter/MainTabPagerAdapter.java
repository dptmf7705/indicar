package com.indicar.indicar_community.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.indicar.indicar_community.view.M2CommunityFragment;
import com.indicar.indicar_community.view.M4AccountFragment;
import com.indicar.indicar_community.view.M3ShoppingFragment;
import com.indicar.indicar_community.view.M1TunningFragment;

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
                fragment = new M1TunningFragment();
                break;
            case 1:
                fragment = new M2CommunityFragment();
                break;
            case 2:
                fragment = new M3ShoppingFragment();
                break;
            case 3:
                fragment = new M4AccountFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
