package com.indicar.indicar_community.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.indicar.indicar_community.view.fragment.CommunityFragment;
import com.indicar.indicar_community.view.fragment.ProfileFragment;
import com.indicar.indicar_community.view.fragment.StoreFragment;
import com.indicar.indicar_community.view.fragment.TunningFragment;

import static com.indicar.indicar_community.utils.Constants.*;

/**
 * Created by yeseul on 2018-03-12.
 */

public class ViewPagerAdapter  extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case TUNING:
                fragment = new TunningFragment();
                break;
            case COMMUNITY:
                CommunityFragment communityFragment = new CommunityFragment();
//                communityFragment.setBtnFilter(actionbarManager.getLeft_btn());
                fragment = communityFragment;
                break;
            case STORE:
                fragment = new StoreFragment();
                break;
            case PROFILE:
                fragment = new ProfileFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_OF_MAIN_TAB_BUTTONS;
    }


}
