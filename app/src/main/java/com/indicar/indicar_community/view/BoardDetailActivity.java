package com.indicar.indicar_community.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.BoardDetailActivityBinding;
import com.indicar.indicar_community.model.vo.BoardCommentVO;
import com.indicar.indicar_community.model.vo.BoardDetailVO;
import com.indicar.indicar_community.model.vo.BoardFileVO;
import com.indicar.indicar_community.utils.CustomActionBar;
import com.indicar.indicar_community.view.adapter.BoardCommentAdapter;
import com.indicar.indicar_community.view.adapter.BoardDetailAdapter;
import com.indicar.indicar_community.viewmodel.BoardCommentViewModel;
import com.indicar.indicar_community.viewmodel.BoardDetailViewModel;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 게시물 조회 화면
 *
 * TODO
 * 0. 메인 img & text 중복되는거 없애기
 * 1. 댓글 UI, 기능 구현
 * 2. 좋아요
 * 3. 맨위로 스크롤
 * 4. 스크롤 시 좋아요, 댓글 버튼 숨기기
 * 5. 키보드 열릴때 좋아요, 댓글 버튼 숨기기
 * 6. 글 수정
 * (+)
 * 7. 이미지 슬라이드 뷰
 * 8. 액션바 없애고 툴바 -> 글 제목
 *
 * @version 1.0 2018-02-23
 * @author yeseul
 */

public class BoardDetailActivity extends BaseActivity<BoardDetailActivityBinding> implements Observer {
    private final String TAG = this.getClass().getSimpleName();

    BoardDetailViewModel boardDetailViewModel = new BoardDetailViewModel();
    BoardCommentViewModel boardCommentViewModel = new BoardCommentViewModel();

    BoardDetailAdapter boardAdapter;
    BoardCommentAdapter commentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.board_detail_activity;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume()");

        Intent intent = getIntent();
        String bbsId = intent.getStringExtra("bbs_id");
        String nttId = intent.getStringExtra("ntt_id");
        String atchFileId = intent.getStringExtra("atch_file_id");

        Log.d(TAG, "boardDetailViewModel" + boardDetailViewModel.toString());

        boardDetailViewModel.addObserver(this);
        boardDetailViewModel.start(bbsId, nttId, atchFileId);
        boardCommentViewModel.addObserver(this);
//        boardCommentViewModel.start(bbsId, nttId);
    }

    @Override
    protected void onPause() {

        Log.d(TAG, "onPause()");

        boardDetailViewModel.deleteObserver(this);
        boardCommentViewModel.deleteObserver(this);

        super.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setViewModel(boardDetailViewModel);

        Log.d(TAG, "onCreate()");

        initView();

        setBoardView();

//        setCommentView();
    }

    private void initView() {

//        initActionBar();

        binding.imagebuttonLike.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return true;
            }
        });

        binding.imagebuttonComment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return true;
            }
        });
    }
/*

    private void initActionBar() {
        CustomActionBar.with(this).init(getSupportActionBar());

    }
*/

    public BoardDetailViewModel getBoardDetailViewModel(){

        return boardDetailViewModel;
    }

    public BoardCommentViewModel getBoardCommentViewModel(){

        return boardCommentViewModel;
    }

    private void setCommentView() {

        binding.recyclerviewCommentContainer
                .setLayoutManager(
                        new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        commentAdapter = new BoardCommentAdapter(this);

        binding.recyclerviewCommentContainer.setAdapter(commentAdapter);
    }

    private void setBoardView() {

        binding.recyclerviewBoardContainer
                .setLayoutManager(
                        new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        binding.recyclerviewBoardContainer.setNestedScrollingEnabled(false);

        boardAdapter = new BoardDetailAdapter(this);

        binding.recyclerviewBoardContainer.setAdapter(boardAdapter);
    }

/*
    private void showKeyboard(EditText editText) {
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.requestFocus();
        inputManager.toggleSoftInput(SHOW_FORCED, 0);
    }

    private void hideKeyboard(EditText editText) {
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.clearFocus();
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    */

    @Override
    public void update(Observable observable, Object o) {

        Log.d(TAG, "update()");

        if(observable instanceof BoardDetailViewModel){
            Toast.makeText(this, ""+o.toString(), Toast.LENGTH_SHORT).show();
            boardAdapter.setBoardHeader((BoardDetailVO) o);
            boardAdapter.notifyDataSetChanged();
        }else if(observable instanceof BoardCommentViewModel){
            commentAdapter.addItems((List<BoardCommentVO>) o);
        }
    }
}
