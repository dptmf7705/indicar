package com.indicar.indicar_community.view;

import android.content.Intent;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.WindowManager;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.BoardWriteDetailActivityBinding;
import com.indicar.indicar_community.utils.PickPhotoHelper;
import com.indicar.indicar_community.view.adapter.BoardWriteAdapter;
import com.indicar.indicar_community.viewmodel.BoardWriteViewModel;

import java.util.Observable;

import io.apptik.multiview.layoutmanagers.ViewPagerLayoutManager;

/**
 * TODO
 * 1. 페이지 하나 일 때 삭제 시, 내용이 있으면 reset
 * 2. 리사이클러 뷰 어댑터 : 페이지 추가, 사진 추가 기능 나눠서 구현
 *
 *
 *
 * */

public class BoardWriteDetailActivity extends BaseActivity<BoardWriteDetailActivityBinding> {

    BoardWriteViewModel viewModel = new BoardWriteViewModel();

    public PickPhotoHelper pickPhotoHelper = new PickPhotoHelper(this);

    @Override
    protected int getLayoutId() {
        return R.layout.board_write_detail_activity;
    }

    @Override
    protected void setActionBarImage(ObservableInt centerImageId, ObservableInt leftImageId) {
        centerImageId.set(R.drawable.logo_community);
        leftImageId.set(R.drawable.btn_back);
    }

    /**
     * 뒤로가기 버튼 눌렀을 때
     * 액티비티 전환 애니메이션 설정
     * */
    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();

        super.onBackPressed();
        overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.addObserver(this);
        viewModel.onResume();
    }

    @Override
    protected void onPause() {
        viewModel.deleteObserver(this);
        viewModel.onPause();

        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel.adapter.set(new BoardWriteAdapter(this, pickPhotoHelper));
        viewModel.layoutManager.set(new ViewPagerLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        viewModel.boardType.set(getIntent().getStringExtra("boardType"));
//        writeViewModel.carType.set(getIntent().getStringExtra("carType"));
        viewModel.onCreate();

        binding.setViewModel(viewModel);

        // 키보드 input mode
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        viewModel.onActivityResult(requestCode, resultCode, data);
        pickPhotoHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void update(Observable observable, Object o) {

        if(o == null) {
            binding.recyclerviewPageContainer.smoothScrollToPosition(viewModel.currentPage.get());
        } else {
            if(o.equals("finish")) finish();
        }

    }

}
