package com.indicar.indicar_community.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.ActionbarManager;

import static com.indicar.indicar_community.utils.Constants.*;

public class BoardWriteFilterActivity extends AppCompatActivity {
    private ImageView btn_next;
    private ActionbarManager actionbarManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write_filter);

        btn_next = findViewById(R.id.btn_next);
        actionbarManager = new ActionbarManager(this);
        actionbarManager.setCustomActionbar(AV_BOARD_WRITE);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardWriteFilterActivity.this, BoardWriteActivity.class);
                startActivity(intent);
            }
        });
    }
}
