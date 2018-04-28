package com.indicar.indicar_community.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.indicar.indicar_community.b1_tunning.TunningFragment;
import com.indicar.indicar_community.b2_community.CommunityFragment;
import com.indicar.indicar_community.b3_shopping.ShoppingFragment;
import com.indicar.indicar_community.b4_account.AccountFragment;

/**
 * Created by yeseul on 2018-03-24.
 */

public class MainViewPagerAdapter extends BaseViewPagerAdapter {

    private final int TAB_COUNT = 4;

    public MainViewPagerAdapter(Context context, FragmentManager fm) {
        super(context, fm);
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
                fragment = new ShoppingFragment();
                break;
            case 3:
                fragment = new AccountFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }


}
