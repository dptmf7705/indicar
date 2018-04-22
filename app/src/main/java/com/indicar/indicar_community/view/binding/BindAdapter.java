package com.indicar.indicar_community.view.binding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.model.vo.BoardDetailVO;
import com.indicar.indicar_community.utils.ImageUtil;
import com.indicar.indicar_community.view.CommunityFragment;
import com.indicar.indicar_community.view.MainActivity;
import com.indicar.indicar_community.view.adapter.BaseRecyclerViewAdapter;
import com.indicar.indicar_community.view.adapter.BoardDetailAdapter;
import com.indicar.indicar_community.view.adapter.BoardViewPagerAdapter;
import com.indicar.indicar_community.view.adapter.MainViewPagerAdapter;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BindAdapter {

    public static final int NUM_OF_BOARD_BUTTONS = 2; // 게시판 탭 버튼 개수
    public static final int NUM_OF_MAIN_TAB_BUTTONS = 4; // 하단 탭 버튼 개수

    @BindingAdapter({"setSelected"})
    public static void isSelected(ImageButton imageView, Boolean bool){
        imageView.setSelected(bool);
    }

    @BindingAdapter(value = {"activity", "tabLayout"}, requireAll = false)
    public static void bindMainTab(ViewPager viewPager,
                                   MainActivity activity,
                                   TabLayout tabLayout){

        viewPager.setAdapter(new MainViewPagerAdapter(viewPager.getContext(), activity.getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

        int[] IMAGE_TAB_ICON = { // 하단 탭 버튼 레이아웃
                R.drawable.tab_t,
                R.drawable.tab_c,
                R.drawable.tab_s,
                R.drawable.tab_a
        };

        for(int i = 0 ; i < NUM_OF_MAIN_TAB_BUTTONS ; i++) {
            View view = activity.getLayoutInflater().inflate(R.layout.main_tab_layout, null);
            ImageView imageView = view.findViewById(R.id.image);
            imageView.setImageResource(IMAGE_TAB_ICON[i]);
            tabLayout.getTabAt(i).setCustomView(view);
        }
    }

    @BindingAdapter(value = {"fragment", "tabLayout"}, requireAll = false)
    public static void bindBoardTab(ViewPager viewPager,
                                   CommunityFragment fragment,
                                   TabLayout tabLayout){

        viewPager.setAdapter(new BoardViewPagerAdapter(viewPager.getContext(), fragment.getChildFragmentManager()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

        String[] TAB_NAME = {
                "인기",
                "전체"
        };

        for(int i = 0 ; i < NUM_OF_BOARD_BUTTONS ; i++){
            tabLayout.getTabAt(i).setText(TAB_NAME[i]);
        }

    }

    @BindingAdapter({"onTouch"})
    public static void bindOnTouch(ImageButton imageButton,
                               View.OnTouchListener onTouchListener){

        imageButton.setOnTouchListener(onTouchListener);
    }

    @BindingAdapter({"onRefresh"})
    public static void bindOnRefresh(SwipeRefreshLayout refreshLayout,
                             SwipeRefreshLayout.OnRefreshListener onRefreshListener){

        refreshLayout.setOnRefreshListener(onRefreshListener);
    }

}
