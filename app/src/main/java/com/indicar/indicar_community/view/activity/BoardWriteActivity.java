package com.indicar.indicar_community.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.ActionbarManager;
import com.indicar.indicar_community.utils.BoardWriteAdapter;
import com.indicar.indicar_community.vo.BoardWriteVO;

import java.util.ArrayList;

import static com.indicar.indicar_community.utils.Constants.*;

public class BoardWriteActivity extends AppCompatActivity {
    private RecyclerView itemView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ActionbarManager actionbarManager;
    private ImageView actionbarBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        actionbarManager = new ActionbarManager(this);
        actionbarManager.setCustomActionbar(AV_BOARD_WRITE);

        actionbarBtnBack = actionbarManager.getLeft_btn();
        actionbarBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        itemView = findViewById(R.id.rv_board_write);
        ArrayList<BoardWriteVO> items = new ArrayList<>();
        items.add(new BoardWriteVO());
        items.add(new BoardWriteVO());

        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        itemView.setLayoutManager(layoutManager);

        adapter = new BoardWriteAdapter(this, items);
        itemView.setAdapter(adapter);
    }

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
    }
}
