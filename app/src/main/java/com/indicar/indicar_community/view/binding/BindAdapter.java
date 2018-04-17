package com.indicar.indicar_community.view.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.ImageUtil;
import com.indicar.indicar_community.utils.ScrollBottonAction;
import com.indicar.indicar_community.view.BaseActivity;
import com.indicar.indicar_community.view.CommunityFragment;
import com.indicar.indicar_community.view.MainActivity;
import com.indicar.indicar_community.view.adapter.BaseRecyclerViewAdapter;
import com.indicar.indicar_community.view.adapter.BaseViewPagerAdapter;
import com.indicar.indicar_community.view.adapter.BoardViewPagerAdapter;
import com.indicar.indicar_community.view.adapter.MainViewPagerAdapter;

import static com.indicar.indicar_community.utils.Constants.NUM_OF_BOARD_BUTTONS;
import static com.indicar.indicar_community.utils.Constants.NUM_OF_MAIN_TAB_BUTTONS;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BindAdapter {


    @BindingAdapter(value = {"imageUrl", "boardType"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, String boardType){

        int placeHolderId = R.drawable.button_share;

        if(boardType != null) {
            if (boardType.equals("daylife")) placeHolderId = R.drawable.button_daylife_deactive;
            else if (boardType.equals("market")) placeHolderId = R.drawable.button_market_deactive;
            else if (boardType.equals("diy")) placeHolderId = R.drawable.button_diy_deactive;
        }

        ImageUtil.loadImage(imageView, url, placeHolderId);
    }

    @BindingAdapter(value = {"imageUrl", "error"}, requireAll = false)
    public static void loadImage(ImageView imageView, Uri url, Drawable errorDrawable){

        ImageUtil.loadImage(imageView, url, errorDrawable);
    }

    @BindingAdapter(value = {"imageUrl", "error"}, requireAll = false)
    public static void loadImage(ImageView imageView, int id, Drawable errorDrawable){

        Log.d("", "loadImage() with id: " + id);
        ImageUtil.loadImage(imageView, id, errorDrawable);
    }

    @BindingAdapter(value = {"circleImageUrl", "error"}, requireAll = false)
    public static void loadCircleImage(ImageView imageView, String url, Drawable errorDrawable){

        ImageUtil.loadCircleImage(imageView, url, errorDrawable);
    }

    @BindingAdapter(value = {"circleImageUrl", "error"}, requireAll = false)
    public static void loadCircleImage(ImageView imageView, int id, Drawable errorDrawable){

        ImageUtil.loadCircleImage(imageView, id, errorDrawable);
    }

    @BindingAdapter(value = {"circleImageUrl", "error"}, requireAll = false)
    public static void loadCircleImage(ImageView imageView, Uri url, Drawable errorDrawable){

        ImageUtil.loadCircleImage(imageView, url, errorDrawable);
    }


    @BindingAdapter(value = {"adapter", "layoutManager", "onScroll", "onChildChange"})
    public static void bindRecyclerView(RecyclerView recyclerView,
                            BaseRecyclerViewAdapter adapter,
                            RecyclerView.LayoutManager layoutManager,
                            RecyclerView.OnScrollListener onScrollListener,
                            RecyclerView.OnChildAttachStateChangeListener onChildChangeListener){

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(onScrollListener);
        recyclerView.addOnChildAttachStateChangeListener(onChildChangeListener);
    }

    @BindingAdapter(value = {"adapter", "layoutManager"})
    public static void bindRecyclerView(RecyclerView recyclerView,
                                        BaseRecyclerViewAdapter adapter,
                                        RecyclerView.LayoutManager layoutManager){

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

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
            View view = activity.getLayoutInflater().inflate(R.layout.a1_layout_main_tab, null);
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
