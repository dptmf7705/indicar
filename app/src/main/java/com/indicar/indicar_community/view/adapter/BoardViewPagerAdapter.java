package com.indicar.indicar_community.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.indicar.indicar_community.view.BoardListFragment;

import static com.indicar.indicar_community.view.adapter.BoardListAdapter.BOARD_ALL;
import static com.indicar.indicar_community.view.adapter.BoardListAdapter.BOARD_POPULAR;

/**
 * Created by yeseul on 2018-04-16.
 */

public class BoardViewPagerAdapter extends BaseViewPagerAdapter{

    private final int TAB_COUNT = 2;

    public BoardViewPagerAdapter(Context context, FragmentManager fm) {
        super(context, fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle args = new Bundle();

        switch (position){
            case BOARD_POPULAR:
                fragment = new BoardListFragment();
                args.putInt("boardType", BOARD_POPULAR);
                fragment.setArguments(args);
                break;
            case BOARD_ALL:
                fragment = new BoardListFragment();
                args.putInt("boardType", BOARD_ALL);
                fragment.setArguments(args);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
