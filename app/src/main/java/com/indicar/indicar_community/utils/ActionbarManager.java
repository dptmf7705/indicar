package com.indicar.indicar_community.utils;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.indicar.indicar_community.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.indicar.indicar_community.utils.Constants.*;

/**
 * Created by yeseul on 2018-02-23.
 *
 * 액션바 커스텀 클래스
 *
 */

public class ActionbarManager {
    private AppCompatActivity activity;
    private ActionBar actionbar;
    private View actionbarLayout;
    private ImageView[] tabButtons;

    private ImageView background;
    private LinearLayout tab_container;
    private ImageView left_btn;

    public ActionbarManager(AppCompatActivity activity) {
        this.activity = activity;
        actionbar = activity.getSupportActionBar(); //기존 액션바 얻기
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); //상태바 text 검정색으로 변경

        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(false);     //액션바 아이콘을 업 네비게이션 형태로 표시
        actionbar.setDisplayShowTitleEnabled(false);    //액션바에 표시되는 제목의 표시유무 설정
        actionbar.setDisplayShowHomeEnabled(false);     //홈 아이콘 숨김처리

        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        actionbarLayout = inflater.inflate(R.layout.layout_actionbar, null); //커스텀 액션바 레이아웃

    }

    public void setCustomActionbar(int tag){
        setCustomLayout(tag);
        actionbar.setCustomView(actionbarLayout);

        /* 액션바 padding 없애기 */
        Toolbar parent = (Toolbar)actionbarLayout.getParent();
        parent.setContentInsetsAbsolute(0,0);
    }

    private void setCustomLayout(int tag){
        background = actionbarLayout.findViewById(R.id.iv_background);
        tab_container = actionbarLayout.findViewById(R.id.ll_tab_container);
        left_btn = actionbarLayout.findViewById(R.id.lv_left_btn);

        /* 액션바 왼쪽버튼. 탭 기본적으로 안보이게 설정 후 필요할때만 아래서 VISIBLE 풀어줌 */
        tab_container.setVisibility(View.GONE);
        left_btn.setVisibility(View.GONE);

        switch (tag){
            case AV_TUNING:

                background.setImageResource(IMGS_ACTIONBAR_BG[TUNING]);
                break;
            case AV_COMMUNITY:
                background.setImageResource(IMGS_ACTIONBAR_BG[COMMUNITY]);
                /* 왼쪽 차량필터 버튼 생성 */
                left_btn.setVisibility(View.VISIBLE);
                left_btn.setImageResource(R.drawable.btn_car_filter);
                left_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //////////////////// 필터 액티비티 띄우기 ////////////////////
                    }
                });

                /* 게시판 탭 버튼 생성 */
                tab_container.setVisibility(View.VISIBLE);
                tabButtons = new ImageView[NUM_OF_BOARD_BUTTONS];
                for(int i = 0; i < NUM_OF_BOARD_BUTTONS; i++){
                    tabButtons[i] = actionbarLayout.findViewById(LAYOUT_ACTIONBAR_TAB_BUTTONS[i]);
                    tabButtons[i].setTag(i);
                }

                break;
            case AV_STORE:
                background.setImageResource(IMGS_ACTIONBAR_BG[STORE]);
                break;
            case AV_PROFILE:
                background.setImageResource(IMGS_ACTIONBAR_BG[PROFILE]);
                break;
            case AV_BOARD_DETAIL:
                background.setImageResource(IMGS_ACTIONBAR_BG[AV_BOARD_DETAIL]);
                left_btn.setVisibility(View.VISIBLE);
                left_btn.setImageResource(R.drawable.btn_back);
                break;
            case AV_BOARD_WRITE:
                background.setImageResource(IMGS_ACTIONBAR_BG[AV_BOARD_WRITE]);
                left_btn.setVisibility(View.VISIBLE);
                left_btn.setImageResource(R.drawable.btn_back);
                break;
            case AV_ADD_PHOTO:
                background.setImageResource(IMGS_ACTIONBAR_BG[AV_ADD_PHOTO]);
                left_btn.setVisibility(View.VISIBLE);
                left_btn.setImageResource(R.drawable.btn_back);

                /* 게시판 탭 버튼 생성 */
                tab_container.setVisibility(View.VISIBLE);
                tabButtons = new ImageView[NUM_OF_ADD_PHOTO_OPTION_BUTTONS];
                for(int i = 0; i < NUM_OF_ADD_PHOTO_OPTION_BUTTONS; i++){
                    tabButtons[i] = actionbarLayout.findViewById(LAYOUT_ACTIONBAR_TAB_BUTTONS[i]);
                    tabButtons[i].setTag(i);
                }

                break;

        }
    }

    /* 탭버튼에 OnClickListener 달기위해 getter 생성 */
    public ImageView[] getTabButtons() {
        return tabButtons;
    }

    public ImageView getLeft_btn() {
        return left_btn;
    }
}
