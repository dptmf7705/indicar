package com.indicar.indicar_community.utils;

import com.indicar.indicar_community.R;

/**
 * 이 클래스는 어플 전체적으로 쓰이는 상수를 모아놓은 클래스다.
 * 다른 클래스에서 이 클래스를 import 하여 상수를 공통적으로 사용하고 관리한다.
 * 사용 의도와 목적에 따라 내부 클래스로 다시 그룹핑한다.
 * (같은 의도로 사용되는 상수가 여러개 생기거나, 다른 의도로 사용되는 것을 방지하기 위함)
 *
 * @version 1.0 2018-02-23
 * @author yeseul
 */

public final class Constants {

    public static final class MainTab{
        public static final int NUM_OF_MAIN_TAB_BUTTONS = 4; // 하단 탭 버튼 개수

        public static final int TUNING = 0;
        public static final int COMMUNITY = 1;
        public static final int STORE = 2;
        public static final int PROFILE = 3;
    }

    public static class Actionbar{
        public static final int TUNING = 0;
        public static final int COMMUNITY = 1;
        public static final int STORE = 2;
        public static final int PROFILE = 3;
        public static final int WRITE = 4;
    }

    /** 하단 탭 관련 상수 */
    public static final int NUM_OF_MAIN_TAB_BUTTONS = 4; // 하단 탭 버튼 개수
    /* 하단 탭 태그 */
    public static final int TUNING = 0;
    public static final int COMMUNITY = 1;
    public static final int STORE = 2;
    public static final int PROFILE = 3;

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
//    public static final int[] LAYOUT_ACTIONBAR_TAB_BUTTONS = { // 액션바 탭 버튼 레이아웃
//            R.id.iv_tab_left,
//            R.id.iv_tab_right
//    };

    public static final int AV_TUNING = 0;
    public static final int AV_COMMUNITY = 1;
    public static final int AV_STORE = 2;
    public static final int AV_PROFILE = 3;
    public static final int AV_BOARD_DETAIL = 4;
    public static final int AV_BOARD_WRITE = 5;
    public static final int AV_ADD_PHOTO =6;

    /** 카메라/앨범 사진 관련 상수 */
    public static final int PICK_FROM_CAMERA = 0;
    public static final int PICK_FROM_ALBUM = 1;
    public static final int CROP_FROM_IMAGE = 2;

    public final static int BOARD_COUNT = 2;
}
