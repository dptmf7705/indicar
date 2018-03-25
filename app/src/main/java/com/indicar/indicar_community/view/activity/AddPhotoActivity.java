package com.indicar.indicar_community.view.activity;

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
import com.indicar.indicar_community.view.fragment.AlbumFragment;
import com.indicar.indicar_community.view.fragment.AllPhotosFragment;

import static com.indicar.indicar_community.utils.Constants.ALBUM;
import static com.indicar.indicar_community.utils.Constants.ALL_PHOTO;
import static com.indicar.indicar_community.utils.Constants.AV_ADD_PHOTO;
import static com.indicar.indicar_community.utils.Constants.NUM_OF_ADD_PHOTO_OPTION_BUTTONS;

public class AddPhotoActivity extends AppCompatActivity {
    private final int IMG_ALL_PHOTOS_CLICKED = R.drawable.all_photos_clicked;
    private final int IMG_ALBUM_CLICKED = R.drawable.album_clicked;
    private final int IMG_ALL_PHOTOS_UNCLICKED = R.drawable.all_photos_unclicked;
    private final int IMG_ALBUM_UNCLICKED = R.drawable.album_unclicked;

    private ActionbarManager actionbarManager; // 액션바 커스텀 클래스
    private ImageView[] actionbarTabButtons; //게시판 버튼 배열
    private int currentTab = 0; //현재 게시판
    private ImageView actionbarBtnBack;
    private ViewPager pager; //뷰페이저

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        actionbarManager = new ActionbarManager(this);
        actionbarManager.setCustomActionbar(AV_ADD_PHOTO);
        actionbarTabButtons = actionbarManager.getTabButtons();
        actionbarBtnBack = actionbarManager.getLeft_btn();
        actionbarBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //initViewPager();
        setTabButtons();
    }

    private void initViewPager(){
        //뷰페이저 세팅
        pager = findViewById(R.id.vp_photos);
        pager.setAdapter(new AddPhotoActivity.pagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(currentTab);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(currentTab != position){
                    actionbarManager.setCustomActionbar(position);
                    changeTab(position);
                    currentTab = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class pagerAdapter extends FragmentStatePagerAdapter {

        public pagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position){
                case ALL_PHOTO:
                    fragment = new AllPhotosFragment();
                    break;
                case ALBUM:
                    fragment = new AlbumFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_OF_ADD_PHOTO_OPTION_BUTTONS;
        }
    }

    private void setTabButtons(){
        actionbarTabButtons = actionbarManager.getTabButtons();
        changeTab(currentTab);

        for(int i = 0; i < NUM_OF_ADD_PHOTO_OPTION_BUTTONS; i++){
            actionbarTabButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tag = (int) view.getTag();

                    // 현재 탭과 다른 탭이 눌렸을 때
                    if(currentTab != tag) {
                        changeTab(tag);
                        pager.setCurrentItem(tag);
                        currentTab = tag;
                    }
                }
            });
        }
    }

    private void changeTab(int tag){
        switch (tag){
            case ALL_PHOTO:
                actionbarTabButtons[ALL_PHOTO].setImageResource(IMG_ALL_PHOTOS_CLICKED);
                actionbarTabButtons[ALBUM].setImageResource(IMG_ALBUM_UNCLICKED);
                break;
            case ALBUM:
                actionbarTabButtons[ALL_PHOTO].setImageResource(IMG_ALL_PHOTOS_UNCLICKED);
                actionbarTabButtons[ALBUM].setImageResource(IMG_ALBUM_CLICKED);
                break;
        }

        //////////////////// 뷰 로딩 ////////////////////

    }
}
