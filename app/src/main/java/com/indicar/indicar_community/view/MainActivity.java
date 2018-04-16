package com.indicar.indicar_community.view;

import android.content.Intent;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.MainActivityBinding;
import com.indicar.indicar_community.utils.MainPageTransformer;

import java.util.Observable;

public class MainActivity extends BaseActivity<MainActivityBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void setActionBarImage(ObservableInt centerImageId, ObservableInt leftImageId) {
        centerImageId.set(R.drawable.logo_tuning);
        leftImageId.set(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.setActivity(this);

        // 화면 전환 효과 없애기
        binding.viewPagerMain.setPageTransformer(true, new MainPageTransformer());

        // 탭 레이아웃과 뷰페이저 연결
        binding.tabLayoutMain.setupWithViewPager(binding.viewPagerMain);

        // 터치스크롤로 페이지 안넘어가게 하기
        binding.viewPagerMain.setPagingEnabled(false);

        binding.viewPagerMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayoutMain));

        binding.tabLayoutMain.setFocusableInTouchMode(false);

        binding.tabLayoutMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                binding.viewPagerMain.setCurrentItem(position);

                switch (position) {
                    case 0:
                        centerImageId.set(R.drawable.logo_tuning);
                        leftImageId.set(0);
                        getSupportActionBar().setElevation(2);
                        break;
                    case 1:
                        centerImageId.set(R.drawable.logo_community);
                        leftImageId.set(R.drawable.btn_car_filter);
                        getSupportActionBar().setElevation(0);
                        actionBarBinding.buttonLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, C0CarFilterActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.enter_no_anim_start, R.anim.exit_no_anim_start);
                            }
                        });
                         break;
                    case 2:
                        centerImageId.set(R.drawable.logo_tuning);
                        leftImageId.set(0);
                        getSupportActionBar().setElevation(2);
                        break;
                    case 3:
                        centerImageId.set(R.drawable.logo_community);
                        leftImageId.set(0);
                        getSupportActionBar().setElevation(2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void update(Observable observable, Object o) {

    }

}
