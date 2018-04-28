package com.indicar.indicar_community.b2_community.boardWrite;

import android.Manifest;
import android.content.Intent;
import android.databinding.ObservableInt;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.View;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.indicar.indicar_community.BaseActivity;
import com.indicar.indicar_community.R;
import com.indicar.indicar_community.adapter.BoardWriteAdapter;
import com.indicar.indicar_community.data.vo.BoardWriteVO;
import com.indicar.indicar_community.databinding.BoardWriteActivityBinding;
import com.indicar.indicar_community.utils.CustomAlertDialog;
import com.indicar.indicar_community.utils.PickPhotoHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.apptik.multiview.layoutmanagers.ViewPagerLayoutManager;

import static com.indicar.indicar_community.Constant.BoardWrite.NEW;

public class BoardWriteActivity extends BaseActivity<BoardWriteActivityBinding> implements BoardWriteNavigator {

    private int command;
    private BoardWriteViewModel viewModel;

    private PickPhotoHelper pickPhotoHelper = new PickPhotoHelper(this);

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
        viewModel.addObserver(this);
    }

    @Override
    protected void onPause() {
        viewModel.deleteObserver(this);
        viewModel.onPause();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.board_write_activity;
    }

    @Override
    protected void setActionBarImage(ObservableInt centerImageId, ObservableInt leftImageId) {
        centerImageId.set(R.drawable.logo_write);
        leftImageId.set(R.drawable.ic_action_close);
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPermission();

        viewModel = new BoardWriteViewModel();
        viewModel.setNavigator(this);

        binding.setViewModel(viewModel);
        binding.detail.pageContainer.setLayoutManager(new ViewPagerLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.detail.pageContainer.setAdapter(new BoardWriteAdapter(this, viewModel.items, pickPhotoHelper));

        getUpdateItems();
    }

    private void getUpdateItems() {
        Intent intent = getIntent();
        this.command = intent.getIntExtra("command", NEW);

        if(command == NEW){
            viewModel.addNewPage(binding.detail.pageContainer);
            return;
        }

        int count = intent.getIntExtra("itemCount", 0);

        List<BoardWriteVO> updateItemList = new ArrayList<>();
        for(int i = 0 ; i < count ; i++){
            BoardWriteVO vo = new BoardWriteVO();
            vo.setFilePath(intent.getStringExtra("fileUrl" + i));
            vo.setWriteText(intent.getStringExtra("content" + i));
            vo.setFileIndex(intent.getIntExtra("fileIndex" + i, 0));
            updateItemList.add(vo);
        }

        viewModel.addPageList(binding.detail.pageContainer, updateItemList);
    }

    @Override
    public void onPageChangedToDetail() {
        leftImageId.set(R.drawable.btn_back);

        actionBarBinding.buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.isIntroPage.set(true);
                onPageChangedToIntro();
            }
        });
    }

    @Override
    public void onPageChangedToIntro() {
        leftImageId.set(R.drawable.ic_action_close);

        actionBarBinding.buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelWrite();
            }
        });
    }

    @Override
    public void onCancelWrite() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        final CustomAlertDialog dialog = new CustomAlertDialog(this);
        dialog.setTitle("게시물 작성을 정말로 취소하시겠습니까?")
                .setSubTitle("Delete this post.")
                .setPositiveButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                })
                .setSize((int) (size.x * 0.9), (int) (size.y * 0.25))
                .show();

    }

    @Override
    public void onSubmitWrite() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        final CustomAlertDialog dialog = new CustomAlertDialog(this);
        dialog.setTitle("게시물을 등록하시겠습니까?")
                .setSubTitle("Upload this post.")
                .setPositiveButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.uploadBoard();
                        dialog.dismiss();
                    }
                })
                .setSize((int) (size.x * 0.9), (int) (size.y * 0.25))
                .show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        viewModel.onActivityResult(requestCode, resultCode, data);
        pickPhotoHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void getPermission(){

        PermissionListener permissionListener = permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }
}
