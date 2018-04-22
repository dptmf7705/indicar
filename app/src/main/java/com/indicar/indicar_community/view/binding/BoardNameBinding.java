package com.indicar.indicar_community.view.binding;

import android.databinding.BindingAdapter;
import android.widget.TextView;

/**
 * Created by yeseul on 2018-04-22.
 */

public class BoardNameBinding {

    @BindingAdapter({"boardName"})
    public static void bindBoardTypeToName(TextView textView, String boardType) {
        String boardName = "";

        if(boardType == null) return;

        if(boardType.equals("daylife")) boardName = "자유, 일상";
        else if(boardType.equals("market")) boardName = "중고, 판매";
        else if(boardType.equals("diy")) boardName = "시공, DIY";
        else if(boardType.equals("test_main")) boardName = "테스트";

        textView.setText(boardName);
    }
}