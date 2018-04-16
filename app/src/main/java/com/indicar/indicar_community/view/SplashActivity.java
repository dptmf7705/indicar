package com.indicar.indicar_community.view;

import android.content.Intent;
import android.databinding.ObservableInt;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.SplashActivityBinding;

import java.util.Observable;

public class SplashActivity extends BaseActivity<SplashActivityBinding> {

    public ObservableInt centerImageId = new ObservableInt();
    public ObservableInt bottomImageId = new ObservableInt();

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity;
    }

    @Override
    protected void setActionBarImage(ObservableInt centerImageId, ObservableInt leftImageId) {

    }

    @Override
    protected void initActionBar() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        centerImageId.set(R.drawable.splash_center_image);
        binding.setCenter(centerImageId);
        bottomImageId.set(R.drawable.splash_bottom_copywrite);
        binding.setBottom(bottomImageId);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
                finish();
            }
        }, 2000);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
