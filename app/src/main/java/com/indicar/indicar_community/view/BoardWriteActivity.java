package com.indicar.indicar_community.view;

import android.Manifest;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.BoardWriteActivityBinding;

import java.util.ArrayList;
import java.util.Observable;

public class BoardWriteActivity extends BaseActivity<BoardWriteActivityBinding> {

    private static final int WRITE_DETAIL = 0;
    private static final int WRITE_BACK = 1;
    private static final int WRITE_CANCEL = 2;

    private PermissionListener permissionListener;

    public final ObservableInt DAY_LIFE = new ObservableInt(0);
    public final ObservableInt MARKET = new ObservableInt(1);
    public final ObservableInt DIY = new ObservableInt(2);

    public final ObservableInt boardType = new ObservableInt();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_right_bottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBarBinding.buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_right_bottom);
            }
        });

        binding.setActivity(this);
        DAY_LIFE.set(0);
        MARKET.set(1);
        DIY.set(2);

        getPermission();
    }

    public void startActivity(){
        Intent intent = new Intent(getApplicationContext(), BoardWriteDetailActivity.class);

        String boardTypeString = changeBoardTypeToString(boardType.get());

        intent.putExtra("boardType", boardTypeString);
//        intent.putExtra("carType", carType);

        startActivityForResult(intent, WRITE_DETAIL);
        overridePendingTransition(R.anim.enter_no_anim_start, R.anim.exit_no_anim_start);
    }

    private String changeBoardTypeToString(int type) {
        if(type == DAY_LIFE.get()) return "daylife";
        else if(type == MARKET.get()) return "market";
        else if(type == DIY.get()) return "diy";
        else return "";
    }

    /**
     *  board type 설정
     * */
    public void setBoardType(int type){
        boardType.set(type);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.board_write_activity;
    }

    @Override
    protected void setActionBarImage(ObservableInt centerImageId, ObservableInt leftImageId) {
        centerImageId.set(R.drawable.logo_community);
        leftImageId.set(R.drawable.btn_back);
    }

    private void getPermission(){

        permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(BoardWriteActivity.this, "권한 허용", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(BoardWriteActivity.this, "권한 거부 " + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK){
            return;
        }

        if(requestCode == WRITE_DETAIL){
            int result = data.getIntExtra("result", 0);
            if(result == WRITE_BACK){

            }else if(result == WRITE_CANCEL){
                finish();
                overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
            }
        }

    }

    @Override
    public void update(Observable observable, Object o) {

    }

}
