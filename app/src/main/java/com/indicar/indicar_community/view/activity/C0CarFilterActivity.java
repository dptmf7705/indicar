package com.indicar.indicar_community.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.CustomActionBar;

public class C0CarFilterActivity extends AppCompatActivity {
    private CustomActionBar customActionBar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m2_activity_car_filter);

        customActionBar = new CustomActionBar(this, getSupportActionBar());
        customActionBar.setBackgroundImage(R.drawable.logo_community);
        customActionBar.setLeftButtonImage(R.drawable.btn_back);
        customActionBar.commit();

        (customActionBar.getLeftButton()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
            }
        });
    }
}
