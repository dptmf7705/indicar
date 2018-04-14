package com.indicar.indicar_community.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.CustomActionBar;

public class C0CarFilterActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m2_activity_car_filter);
//        initActionBar();

    }

//    private void initActionBar() {
//        CustomActionBar.with(this).init(getSupportActionBar());
//    }
}
