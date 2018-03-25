package com.indicar.indicar_community.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.CustomActionBar;
import com.indicar.indicar_community.utils.CustomViewPager;
import com.indicar.indicar_community.utils.MainTabPagerAdapter;

import static com.indicar.indicar_community.utils.Constants.NUM_OF_MAIN_TAB_BUTTONS;

public class MainActivity extends AppCompatActivity {
    private CustomActionBar customActionBar;
    private View actionBarView;
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
        setContentView(R.layout.activity_main);
        customActionBar = new CustomActionBar(this, getSupportActionBar());
        actionBarView = customActionBar.getView();
        ImageView imageView = actionBarView.findViewById(R.id.imageCenter);
        imageView.setImageResource(R.drawable.logo_tuning);
        customActionBar.setView(actionBarView);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);

        pagerAdapter = new MainTabPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPagingEnabled(false);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        for(int i = 0 ; i < NUM_OF_MAIN_TAB_BUTTONS ; i++) {
            View view = getLayoutInflater().inflate(R.layout.layout_main_tab, null);
            imageView = view.findViewById(R.id.image);
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
                ImageView imageView = actionBarView.findViewById(R.id.imageCenter);
                ImageView leftButton = actionBarView.findViewById(R.id.leftButton);
                leftButton.setVisibility(View.GONE);

                switch (position) {
                    case 0:
                        imageView.setImageResource(R.drawable.logo_tuning);
                        actionBar.setElevation(2);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.logo_community);
                        leftButton.setImageResource(R.drawable.btn_car_filter);
                        leftButton.setVisibility(View.VISIBLE);
                        actionBar.setElevation(0);
                         break;
                    case 2:
                        imageView.setImageResource(R.drawable.logo_tuning);
                        actionBar.setElevation(2);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.logo_community);
                        actionBar.setElevation(2);
                        break;
                }
                customActionBar.setView(actionBarView);
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
