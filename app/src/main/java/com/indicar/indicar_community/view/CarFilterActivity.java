package com.indicar.indicar_community.view;

import android.databinding.ObservableInt;
import android.os.Bundle;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.CarFilterActivityBinding;

import java.util.Observable;

public class CarFilterActivity extends BaseActivity<CarFilterActivityBinding> {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.car_filter_activity;
    }

    @Override
    protected void setActionBarImage(ObservableInt centerImageId, ObservableInt leftImageId) {
        centerImageId.set(R.drawable.logo_community);
        leftImageId.set(R.drawable.btn_back);
    }

    @Override
    public void update(Observable observable, Object o) {

    }

}
