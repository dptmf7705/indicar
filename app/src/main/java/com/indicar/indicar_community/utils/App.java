package com.indicar.indicar_community.utils;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by yeseul on 2018-02-23.
 *
 *  글꼴 설정
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunGothicBold.otf"))
                .add("Light",Typekit.createFromAsset(this, "fonts/NanumBarunGothicLight.otf"))
                .add("UltraLight",Typekit.createFromAsset(this, "fonts/NanumBarunGothicLight.otf"));
    }
}
