package com.indicar.indicar_community.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.CustomActionBar;
import com.indicar.indicar_community.utils.CustomViewPager;
import com.indicar.indicar_community.adapters.MainTabPagerAdapter;
import com.indicar.indicar_community.utils.MainPageTransformer;

import static com.indicar.indicar_community.utils.Constants.NUM_OF_MAIN_TAB_BUTTONS;

public class A01MainActivity extends AppCompatActivity {
    private CustomActionBar customActionBar;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private MainTabPagerAdapter pagerAdapter;
    public static final int[] IMAGE_TAB_ICON = { // 하단 탭 버튼 레이아웃
            R.drawable.tab_t,
            R.drawable.tab_c,
            R.drawable.tab_s,
            R.drawable.tab_a
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a01_main);
        customActionBar = new CustomActionBar(this, getSupportActionBar());
        customActionBar.setBackgroundImage(R.drawable.logo_tuning);
        customActionBar.commit();

        viewPager = findViewById(R.id.viewPager);
        viewPager.setPageTransformer(true, new MainPageTransformer());

        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);
        pagerAdapter = new MainTabPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPagingEnabled(false);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        for(int i = 0 ; i < NUM_OF_MAIN_TAB_BUTTONS ; i++) {
            View view = getLayoutInflater().inflate(R.layout.layout_main_tab, null);
            ImageView imageView = view.findViewById(R.id.image);
            imageView.setImageResource(IMAGE_TAB_ICON[i]);
            tabLayout.getTabAt(i).setCustomView(view);
        }
        tabLayout.setFocusableInTouchMode(false);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                viewPager.setCurrentItem(position);
                ActionBar actionBar = customActionBar.getActionBar();
                customActionBar.removeLeftButton();

                switch (position) {
                    case 0:
                        customActionBar.setBackgroundImage(R.drawable.logo_tuning);
                        actionBar.setElevation(2);
                        break;
                    case 1:
                        customActionBar.setBackgroundImage(R.drawable.logo_community);
                        customActionBar.setLeftButtonImage(R.drawable.btn_car_filter);
                        (customActionBar.getLeftButton()).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(A01MainActivity.this, CarFilterActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.enter_no_anim_start, R.anim.exit_no_anim_start);
                            }
                        });
                        actionBar.setElevation(0);
                         break;
                    case 2:
                        customActionBar.setBackgroundImage(R.drawable.logo_tuning);
                        actionBar.setElevation(2);
                        break;
                    case 3:
                        customActionBar.setBackgroundImage(R.drawable.logo_community);
                        actionBar.setElevation(2);
                        break;
                }
                customActionBar.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
