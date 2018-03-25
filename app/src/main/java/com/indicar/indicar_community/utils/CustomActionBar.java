package com.indicar.indicar_community.utils;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.indicar.indicar_community.R;

/**
 * Created by yeseul on 2018-03-23.
 */

public class CustomActionBar {
    private ActionBar actionBar;
    private View view;
    private ImageView leftButton;
    private ImageView background;

    public CustomActionBar(AppCompatActivity activity, ActionBar actionBar){
        this.actionBar = actionBar;
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);     //액션바 아이콘을 업 네비게이션 형태로 표시
        actionBar.setDisplayShowTitleEnabled(false);    //액션바에 표시되는 제목의 표시유무 설정
        actionBar.setDisplayShowHomeEnabled(false);     //홈 아이콘 숨김처리

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_actionbar, null);

        leftButton = view.findViewById(R.id.leftButton);
        background = view.findViewById(R.id.imageCenter);

        actionBar.setCustomView(view); //커스텀 뷰 적용
        actionBar.setElevation(2);

        // 액션바 padding 없애기
        Toolbar parent = (Toolbar)view.getParent();
        parent.setContentInsetsAbsolute(0,0);
    }

    public View getView(){
        return view;
    }

    public void setView(View view){
        this.view = view;
    }

    public ActionBar getActionBar(){
        return actionBar;
    }

    public void setBackgroundImage(int src){
        background.setImageResource(src);
    }

    public void setLeftButtonImage(int src){
        leftButton.setImageResource(src);
        leftButton.setVisibility(View.VISIBLE);
    }

    public ImageView getLeftButton() {
        return leftButton;
    }

    public void commit(){
        actionBar.setCustomView(view);
    }

}
