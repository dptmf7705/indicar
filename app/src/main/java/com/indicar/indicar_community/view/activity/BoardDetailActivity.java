package com.indicar.indicar_community.view.activity;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.ActionbarManager;
import com.indicar.indicar_community.utils.BoardDetailAdapter;
import com.indicar.indicar_community.vo.BoardItemVO;

import java.util.ArrayList;

import static com.indicar.indicar_community.utils.Constants.AV_BOARD_DETAIL;

public class BoardDetailActivity extends AppCompatActivity {
    private ActionbarManager actionbarManager; // 액션바 커스텀 매니저 클래스
    private ImageView actionbarLeftBtn;
    private RecyclerView itemView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView iv_like;
    private ImageView iv_comment;
    private EditText et_comment;
    private Boolean isLike = false;
    private InputMethodManager inputManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        itemView = findViewById(R.id.board_detail_view);
        actionbarManager = new ActionbarManager(BoardDetailActivity.this);
        actionbarManager.setCustomActionbar(AV_BOARD_DETAIL);
        actionbarLeftBtn = actionbarManager.getLeft_btn();
        actionbarLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ArrayList<BoardItemVO> items = new ArrayList<>();
        items.add(new BoardItemVO(R.drawable.car_img_5, "Returns an array of model names created on this connection."));
        items.add(new BoardItemVO(R.drawable.car_img_1, "반디캠은 월등한 성능을 보여주는 고성능 동영상 녹화 프로그램으로, 언제든 설치파일을 다운로드 받아 설치 및 사용이 가능하며, 정품을 구매하시고 정품 등록을 하시면 모든 기능을 제한 없이 사용하실 수 있습니다."));
        items.add(new BoardItemVO(R.drawable.car_img_2, "반디캠은 월등한 성능을 보여주는 고성능 동영상 녹화 프로그램으로"));
        items.add(new BoardItemVO(R.drawable.car_img_3, "언제든 설치파일을 다운로드 받아 설치 및 사용이 가능하며, 정품을 구매하시고 정품 등록을 하시면 모든 기능을 제한 없이 사용하실 수 있습니다."));
        items.add(new BoardItemVO(R.drawable.car_img_4, "When no collection argument is passed, Mongoose produces a collection name by passing the model name to the utils.toCollectionName method. This method pluralizes the name. If you don't like this behavior, either pass a collection name or set your schemas collection name option."));

        layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        itemView.setLayoutManager(layoutManager);
        itemView.setNestedScrollingEnabled(false);

        adapter = new BoardDetailAdapter(this, items);
        itemView.setAdapter(adapter);

        et_comment = findViewById(R.id.et_comment);

        iv_comment = findViewById(R.id.iv_comment);
        iv_comment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    //////////////////// 댓글쓰기 ////////////////////
                    inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    //et_comment.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        iv_like = findViewById(R.id.iv_like);
        iv_like.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    //////////////////// 좋아요 여부 서버에서 받아오기 ////////////////////
                    if(isLike) { // 좋아요 누른 게시글인 경우
                        iv_like.setImageResource(R.drawable.btn_like_unclicked); //취소
                    }else{
                        iv_like.setImageResource(R.drawable.btn_like_clicked); //좋아요
                    }
                    isLike = !isLike;
                 }
                return true;
            }
        });

    }


}
