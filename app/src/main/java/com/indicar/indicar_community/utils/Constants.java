package com.indicar.indicar_community.utils;

import com.indicar.indicar_community.R;

/**
 * Created by yeseul on 2018-02-23.
 *
 * Constant 클래스
 * 어플 전체적으로 공유할 상수 정의
 *
 */

public final class Constants {

    /** 하단 탭 관련 상수 */
    public static final int NUM_OF_MAIN_TAB_BUTTONS = 4; // 하단 탭 버튼 개수
    /* 하단 탭 태그 */
    public static final int TUNING = 0;
    public static final int COMMUNITY = 1;
    public static final int STORE = 2;
    public static final int PROFILE = 3;
    public static final int[] LAYOUT_MAIN_TAB_BUTTONS = { // 하단 탭 버튼 레이아웃
            R.id.btn_tab_t,
            R.id.btn_tab_c,
            R.id.btn_tab_s,
            R.id.btn_tab_a
    };

    /** 액션바 관련 상수 */
    public static final int NUM_OF_BOARD_BUTTONS = 2; // 게시판 탭 버튼 개수
    public static final int POPULAR = 0;
    public static final int ALL = 1;


    public static final int NUM_OF_ADD_PHOTO_OPTION_BUTTONS = 2; // 사진 추가 옵션 탭 버튼 개수
    public static final int ALL_PHOTO = 0;
    public static final int ALBUM = 1;

    public static final int[] IMGS_ACTIONBAR_BG = { // 액션바 배경 이미지
            R.drawable.actionbar_bg_tuning,
            R.drawable.actionbar_bg_community,
            R.drawable.actionbar_bg_tuning,
            R.drawable.actionbar_bg_tuning,
            R.drawable.actionbar_bg_community,
            R.drawable.actionbar_bg_write,
            R.drawable.actionbar_bg_write
    };
    public static final int[] LAYOUT_ACTIONBAR_TAB_BUTTONS = { // 액션바 탭 버튼 레이아웃
            R.id.iv_tab_left,
            R.id.iv_tab_right
    };

    public static final int AV_TUNING = 0;
    public static final int AV_COMMUNITY = 1;
    public static final int AV_STORE = 2;
    public static final int AV_PROFILE = 3;
    public static final int AV_BOARD_DETAIL = 4;
    public static final int AV_BOARD_WRITE = 5;
    public static final int AV_ADD_PHOTO =6;

}
