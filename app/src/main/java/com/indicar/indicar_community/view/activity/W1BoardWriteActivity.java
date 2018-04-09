package com.indicar.indicar_community.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.CustomActionBar;
import com.indicar.indicar_community.utils.WriteFilterRadio;

import java.util.ArrayList;

public class W1BoardWriteActivity extends AppCompatActivity {
    private ImageView btn_next;
    private CustomActionBar customActionBar;
    private WriteFilterRadio filterRadio;
    private PermissionListener permissionListener;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_right_bottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w1_activity_board_write);

        getPermission();
        setupView();

    }

    private void getPermission(){

        permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(W1BoardWriteActivity.this, "권한 허용", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(W1BoardWriteActivity.this, "권한 거부 " + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }

    private void setupView() {

        setupActionBar();

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

                    Intent intent = new Intent(getApplicationContext(), W2BoardWriteDetailActivity.class);
                    intent.putExtra("filter", filter);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter_no_anim_start, R.anim.exit_no_anim_start);
                }
            }
        });
    }

    private void setupActionBar() {
        customActionBar = new CustomActionBar(this, getSupportActionBar());
        customActionBar.setBackgroundImage(R.drawable.logo_write);
        customActionBar.setLeftButtonImage(R.drawable.btn_back);
        customActionBar.commit();
        (customActionBar.getLeftButton()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_right_bottom);
            }
        });
    }



}
