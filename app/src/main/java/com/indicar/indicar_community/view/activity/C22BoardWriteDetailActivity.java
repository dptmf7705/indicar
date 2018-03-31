package com.indicar.indicar_community.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.adapters.BoardWritePagerAdapter;
import com.indicar.indicar_community.utils.CustomActionBar;
import com.indicar.indicar_community.utils.ZoomOutPageTransformer;
import com.indicar.indicar_community.vo.WriteFileAndTextVO;

import java.util.ArrayList;

public class C22BoardWriteDetailActivity extends AppCompatActivity{
    private final int MAX_PAGE = 15;
    private int currentPage = 0;
    private int pageCount = 1;

    private CustomActionBar customActionBar;

    private ImageButton btnAdd;
    private ImageButton btnDelete;
    private TextView currentPageView;
    private TextView totalPageView;
    private BoardWritePagerAdapter adapter;

    private ViewPager viewPager;
    private ArrayList<WriteFileAndTextVO> itemList = new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write_detail);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        customActionBar = new CustomActionBar(this, getSupportActionBar());
        customActionBar.setBackgroundImage(R.drawable.logo_write);
        customActionBar.setLeftButtonImage(R.drawable.btn_back);
        customActionBar.commit();

        currentPageView = findViewById(R.id.currentPage);
        totalPageView = findViewById(R.id.totalPage);

        ImageView leftButton = customActionBar.getLeftButton();
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), C21BoardWriteActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
            }
        });

        btnAdd = findViewById(R.id.btnAddSlide);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pageCount < MAX_PAGE) {
                    addPage();
                }
            }
        });
        btnDelete = findViewById(R.id.btnDeleteSlide);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pageCount > 1){
                    deletePage();
                }
            }
        });

        viewPager = findViewById(R.id.writeDetailPager);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        View view = getLayoutInflater().inflate(R.layout.fragment_board_write_item, null);
        viewPager.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        showPager();
    }

    private void showPager(){
        itemList.add(new WriteFileAndTextVO());
        adapter = new BoardWritePagerAdapter(this, getSupportFragmentManager(), itemList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                currentPageView.setText("" + (currentPage+1));
                totalPageView.setText("" + pageCount);
                Toast.makeText(getApplicationContext(), "current: "+ currentPage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addPage(){
        itemList.add(currentPage, new WriteFileAndTextVO());
        adapter.setList(itemList);
        adapter.notifyDataSetChanged();

        pageCount++;
        viewPager.setCurrentItem(currentPage + 1);
    }

    private void deletePage(){
        itemList.remove(currentPage);
        adapter.setList(itemList);
        adapter.notifyDataSetChanged();

        pageCount--;
        viewPager.setCurrentItem(currentPage - 1);
    }


}
