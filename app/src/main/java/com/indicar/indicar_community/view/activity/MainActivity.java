package com.indicar.indicar_community.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.ActionbarManager;
import com.indicar.indicar_community.view.fragment.CommunityFragment;
import com.indicar.indicar_community.view.fragment.ProfileFragment;
import com.indicar.indicar_community.view.fragment.StoreFragment;
import com.indicar.indicar_community.view.fragment.TunningFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import static com.indicar.indicar_community.utils.Constants.*;

public class MainActivity extends AppCompatActivity {
    private final int IMG_POPULAR_CLICKED = R.drawable.tab_popular_clicked;
    private final int IMG_ALL_CLICKED = R.drawable.tab_all_cliked;
    private final int IMG_POPULAR_UNCLICKED = R.drawable.tab_popular_unclicked;
    private final int IMG_ALL_UNCLICKED = R.drawable.tab_all_unclicked;

    private ViewPager pager; //뷰페이저
    private ActionbarManager actionbarManager; // 액션바 커스텀 클래스
    private ImageView[] mainTabButtons = new ImageView[NUM_OF_MAIN_TAB_BUTTONS]; // 하단 탭 버튼 배열
    private int currentMainTab = TUNING; // 현재 탭

    private ImageView[] boardButtons; //게시판 버튼 배열
    private int currentBoardTab = 0; //현재 게시판

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionbarManager = new ActionbarManager(MainActivity.this);
        actionbarManager.setCustomActionbar(currentMainTab);
        initViewPager();
    }

    private void setBoardTabButtons(){
        boardButtons = actionbarManager.getTabButtons();
        changeBoardTab(currentMainTab);

        for(int i = 0; i < NUM_OF_BOARD_BUTTONS; i++){
            boardButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tag = (int) view.getTag();

                    // 현재 탭과 다른 탭이 눌렸을 때
                    if(currentBoardTab != tag) {
                        changeBoardTab(tag);
                        currentBoardTab = tag;
                    }
                }
            });
        }
    }

    private void changeBoardTab(int tag){
        switch (tag){
            case POPULAR:
                boardButtons[POPULAR].setImageResource(IMG_POPULAR_CLICKED);
                boardButtons[ALL].setImageResource(IMG_ALL_UNCLICKED);
                break;
            case ALL:
                boardButtons[POPULAR].setImageResource(IMG_POPULAR_UNCLICKED);
                boardButtons[ALL].setImageResource(IMG_ALL_CLICKED);
                break;
        }

        //////////////////// 게시판 로딩 ////////////////////

    }

    private void initViewPager(){
        // 탭 버튼 세팅
        for(int i = 0 ; i < NUM_OF_MAIN_TAB_BUTTONS ; i++){
            mainTabButtons[i] = findViewById(LAYOUT_MAIN_TAB_BUTTONS[i]);
            mainTabButtons[i].setTag(i);
            mainTabButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        int tag = (int) view.getTag();
                        pager.setCurrentItem(tag);
                }
            });
        }

        //뷰페이저 세팅
        pager = findViewById(R.id.view_pager);
        pager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(currentMainTab);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /* 버튼 색 변경, 액션바 이미지 변경(setActionBar() 호출) */

            }

            @Override
            public void onPageSelected(int position) {
                if(currentMainTab != position){
                    actionbarManager.setCustomActionbar(position);

                    /* 커뮤니티 탭이 눌리면 게시판 탭 로딩 */
                    if(position == COMMUNITY){
                        setBoardTabButtons();
                    }

                    currentMainTab = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class pagerAdapter extends FragmentStatePagerAdapter{

        public pagerAdapter(FragmentManager fragmentManager){
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
                    communityFragment.setBtnFilter(actionbarManager.getLeft_btn());
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

}
