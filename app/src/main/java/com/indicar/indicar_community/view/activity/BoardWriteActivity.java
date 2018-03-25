package com.indicar.indicar_community.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.CustomActionBar;
import com.indicar.indicar_community.utils.WriteFilterRadio;

public class BoardWriteActivity extends AppCompatActivity {
    private ImageView btn_next;
    private CustomActionBar customActionBar;
    private View actionBarView;
    private WriteFilterRadio filterRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        customActionBar = new CustomActionBar(this, getSupportActionBar());
        actionBarView = customActionBar.getView();
        ImageView background = actionBarView.findViewById(R.id.imageCenter);
        background.setImageResource(R.drawable.logo_write);
        ImageView leftButton = actionBarView.findViewById(R.id.leftButton);
        leftButton.setImageResource(R.drawable.btn_back);
        leftButton.setVisibility(View.VISIBLE);
        customActionBar.setView(actionBarView);

        ImageButton buttonDayLife = findViewById(R.id.buttonDayLife);
        ImageButton buttonMarket = findViewById(R.id.buttonMarket);
        ImageButton buttonDIY = findViewById(R.id.buttonDIY);
        filterRadio = new WriteFilterRadio(buttonDayLife, buttonMarket, buttonDIY);

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int filter = filterRadio.getChecked();
                if(filter < 0){
                    Toast.makeText(getApplicationContext(), "카테고리를 선택해 주세요.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), BoardWriteDetailActivity.class);
                    intent.putExtra("filter", filter);
                    startActivity(intent);
                }
            }
        });

    }
}
