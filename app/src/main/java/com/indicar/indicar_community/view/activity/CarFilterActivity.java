package com.indicar.indicar_community.view.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.indicar.indicar_community.R;

public class CarFilterActivity extends AppCompatActivity {
    private ActionBar actionBar; //액션바
    private View layout_actionBar; //액션바 레이아웃

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_filter);

        initActionBar();
    }

    private void initActionBar(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); //상태바 text color 검정색으로 변경

        actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);     //액션바 아이콘을 업 네비게이션 형태로 표시
        actionBar.setDisplayShowTitleEnabled(false);    //액션바에 표시되는 제목의 표시유무 설정
        actionBar.setDisplayShowHomeEnabled(false);     //홈 아이콘 숨김처리


        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        layout_actionBar = inflater.inflate(R.layout.layout_actionbar, null);

        actionBar.setCustomView(layout_actionBar);

        //actionbar padding 없애기
        Toolbar parent = (Toolbar)layout_actionBar.getParent();
        parent.setContentInsetsAbsolute(0,0);
    }
}
