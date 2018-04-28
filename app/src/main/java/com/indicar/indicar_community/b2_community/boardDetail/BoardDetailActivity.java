package com.indicar.indicar_community.b2_community.boardDetail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.commit451.teleprinter.Teleprinter;
import com.indicar.indicar_community.BaseActivity;
import com.indicar.indicar_community.R;
import com.indicar.indicar_community.adapter.BaseRecyclerViewAdapter;
import com.indicar.indicar_community.adapter.BoardCommentAdapter;
import com.indicar.indicar_community.adapter.BoardDetailAdapter;
import com.indicar.indicar_community.b2_community.boardWrite.BoardWriteActivity;
import com.indicar.indicar_community.data.vo.BoardFileVO;
import com.indicar.indicar_community.databinding.BoardDetailActivityBinding;
import com.indicar.indicar_community.utils.RecyclerViewDecoration;

import java.util.List;
import java.util.Observable;

import static com.indicar.indicar_community.Constant.BoardWrite.UPDATE;

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

public class BoardDetailActivity extends BaseActivity<BoardDetailActivityBinding> {

    private static final int BOARD_UPDATE = 1;
    private final String TAG = this.getClass().getSimpleName();

    BoardDetailViewModel viewModel = new BoardDetailViewModel();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.board_detail_activity;
    }

    @Override
    protected void setActionBarImage(ObservableInt centerImageId, ObservableInt leftImageId) {
        centerImageId.set(R.drawable.logo_community);
        leftImageId.set(R.drawable.btn_back);
    }

    @Override
    protected void onResume() {

        Log.d(TAG, "onResume()");

        super.onResume();

        viewModel.addObserver(this);
        viewModel.onResume();
    }

    @Override
    protected void onPause() {

        Log.d(TAG, "onPause()");

        viewModel.deleteObserver(this);
        viewModel.onPause();

        super.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called...");

        binding.setViewModel(viewModel);

        actionBarBinding.buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
            }
        });

        Intent intent = getIntent();
        String bbsId = intent.getStringExtra("bbs_id");
        String nttId = intent.getStringExtra("ntt_id");
        String commentCount = intent.getStringExtra("commentCount");

        viewModel.scrollView = binding.scrollViewContainer;
        viewModel.keyboard = new Teleprinter(this);

        viewModel.adapter.set(new BoardDetailAdapter(this, viewModel.header.get()));
        viewModel.commentAdapter.set(new BoardCommentAdapter(this));

        binding.recyclerviewBoardContainer.setNestedScrollingEnabled(false);
        binding.recyclerviewCommentContainer.setNestedScrollingEnabled(false);

        RecyclerViewDecoration decoration = new RecyclerViewDecoration(0, 3, 0, 0);
        binding.recyclerviewCommentContainer.addItemDecoration(decoration);

        viewModel.onCreate(bbsId, nttId, commentCount);

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

        viewModel.commentAdapter.get().setOnItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                showCommentDialog(position);
            }
        });

        viewModel.adapter.get().setOnMenuClickListener(new BoardDetailAdapter.OnMenuClickListener() {
            @Override
            public void onMenuClick(View view, int position) {
                showMenuDialog(view);
            }
        });

        binding.textCommentType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.commentWrite.set(editable.toString());
            }
        });

        binding.scrollViewContainer.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int scrollDown = scrollY - oldScrollY;
                int scrollUp = oldScrollY - scrollY;
                Handler handler = new Handler();

                if(scrollDown > 20){

                    viewModel.isScrollDown.set(true);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewModel.isScrollDown.set(false);
                        }
                    }, 2000);
                }

                if(scrollUp > 20){

                    viewModel.isScrollUp.set(true);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            viewModel.isScrollUp.set(false);
                        }
                    }, 2000);
                }
            }
        });
    }

    private void showMenuDialog(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        getMenuInflater().inflate(R.menu.board_detail_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.update:
                        updateBoard();
                        break;
                    case R.id.delete:
                        showDeleteDialog();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void showDeleteDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogStyle));
        dialog.setTitle("게시글 삭제");
        dialog.setMessage("정말로 삭제 하시겠습니까?");
        dialog.setCancelable(false);
        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do nothing
                    }
                });
        dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        viewModel.deleteBoard();
                        finish();
                        overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
                    }
                });
        dialog.show();
    }

    public void showCommentDialog(final int position){
        CharSequence[] items = {"댓글 수정", "댓글 삭제"};

        new AlertDialog.Builder(this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            viewModel.openCommentBoxWithUpdateItem(position);
                        } else {
                            viewModel.deleteCommentItem(position);
                        }
                    }
                })
                .show();
    }

    public void updateBoard() {
        List<BoardFileVO> list =  viewModel.adapter.get().getItemList();
        Intent intent = new Intent(getApplicationContext(), BoardWriteActivity.class);

        intent.putExtra("command", UPDATE);
        intent.putExtra("itemCount", list.size());
        for( int i = 0 ; i < list.size() ; i++){
            intent.putExtra("fileUrl" + i, list.get(i).getFileUrl());
            intent.putExtra("content" + i, list.get(i).getContent());
            intent.putExtra("fileIndex" + i, list.get(i).getFileIndex());
        }
        startActivityForResult(intent, BOARD_UPDATE);
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
            return;
        }

        if(requestCode == BOARD_UPDATE){

        }
    }
}
