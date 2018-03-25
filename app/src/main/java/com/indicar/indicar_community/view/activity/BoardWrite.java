package com.indicar.indicar_community.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.CustomActionBar;

public class BoardWrite extends AppCompatActivity {
    private CustomActionBar customActionBar;
    private View actionBarView;
    private RecyclerView itemView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ImageView actionbarBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_write);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);


        customActionBar = new CustomActionBar(this, getSupportActionBar());
        customActionBar.setBackgroundImage(R.drawable.logo_write);
        customActionBar.setLeftButtonImage(R.drawable.btn_back);
        customActionBar.commit();

        ImageView leftButton = customActionBar.getLeftButton();
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
/*
        itemView = findViewById(R.id.rv_board_write);
        ArrayList<BoardWriteVO> items = new ArrayList<>();
        items.add(new BoardWriteVO());
        items.add(new BoardWriteVO());

        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        itemView.setLayoutManager(layoutManager);

        adapter = new BoardWriteAdapter(this, items);
        itemView.setAdapter(adapter);*/
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
            return;
        }

        switch (requestCode){
            case PICK_FROM_CAMERA:
                data.getData();

                break;
            case PICK_FROM_ALBUM:

                break;
            case CROP_FROM_IMAGE:

                break;
        }
    }*/
}
