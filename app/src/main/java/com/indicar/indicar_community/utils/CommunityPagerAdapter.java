package com.indicar.indicar_community.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.indicar.indicar_community.view.fragment.NormalBoardFragment;
import com.indicar.indicar_community.view.fragment.PopularBoardFragment;

import static com.indicar.indicar_community.utils.Constants.*;

/**
 * Created by yeseul on 2018-03-24.
 */

public class CommunityPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;

    public CommunityPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new PopularBoardFragment();
                break;
            case 1:
                fragment = new NormalBoardFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return BOARD_COUNT;
    }
}
