package com.indicar.indicar_community.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.BoardDetailAdapter;
import com.indicar.indicar_community.utils.CustomActionBar;
import com.indicar.indicar_community.utils.HTTPClient;
import com.indicar.indicar_community.vo.BbsVO;
import com.indicar.indicar_community.vo.BoardItemVO;
import com.indicar.indicar_community.vo.FileDetailVO;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

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

public class BoardDetailActivity extends AppCompatActivity {
    private CustomActionBar customActionBar;
    private ImageView actionbarLeftBtn;
    private RecyclerView itemView;
    private BoardDetailAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView iv_like;
    private ImageView iv_comment;
    private Boolean isLike = false;
    private InputMethodManager inputManager;
    private ImageView mainImage;
    private ImageView userImage;
    private TextView userName;
    private TextView filter;
    private TextView likeCount;
    private TextView commentCount;
    private TextView lastDate;
    private TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        itemView = findViewById(R.id.recyclerView);
        itemView.setFocusable(false);
        customActionBar = new CustomActionBar(this, getSupportActionBar());
        customActionBar.setBackgroundImage(R.drawable.logo_community);
        customActionBar.setLeftButtonImage(R.drawable.btn_back);
        customActionBar.commit();


        actionbarLeftBtn = customActionBar.getLeftButton();
        actionbarLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mainImage = findViewById(R.id.mainImage);

        userImage = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        filter = findViewById(R.id.filter);
        likeCount = findViewById(R.id.likeCount);
        commentCount = findViewById(R.id.commentCount);
        lastDate = findViewById(R.id.lastDate);
        mainText = findViewById(R.id.mainText);

        layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        itemView.setLayoutManager(layoutManager);
        itemView.setNestedScrollingEnabled(false);

        adapter = new BoardDetailAdapter(this);
        itemView.setAdapter(adapter);

        Intent intent = getIntent();
        String bbsId = intent.getStringExtra("bbs_id");
        String nttId = intent.getStringExtra("ntt_id");
        selectBoardArticle(nttId, bbsId);

        ArrayList<FileDetailVO> files = intent.getParcelableArrayListExtra("files");

        ArrayList<BoardItemVO> items = new ArrayList<>();

        for(int i = 0 ; i < files.size() ; i++){
            BoardItemVO vo = new BoardItemVO(files.get(i).getFile_sn(), null, files.get(i).getFile_cn());
            items.add(vo);
        }

        adapter.addList(items);

        for(int i = 0 ; i < files.size() ; i++ ){
            final FileDetailVO file = files.get(i);

            HTTPClient.get(file.getFileStoreUri(), null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] fileData) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
                    if(bitmap != null) {
                        int index = file.getFile_sn();
                        if(index == 0){
                            setMainFile(bitmap, file.getFile_cn());
                        }
                        adapter.addImage(index, bitmap);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }
            });

        }

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
    private void setMainFile(Bitmap bitmap, String text){
        mainImage.setImageBitmap(bitmap);
        mainText.setText("" + text);
    }

    private void setView(BbsVO vo){
        userName.setText("" + vo.getNtcr_nm());
        filter.setText("" + vo.getBbs_id());
        likeCount.setText("" + vo.getLike());
        commentCount.setText("" + vo.get__v());
        lastDate.setText("" + vo.getLast_updt_time());
    }

    private void selectBoardArticle(String nttId, String bbsId){
        RequestParams params2 = new RequestParams();
        params2.put("ntt_id", nttId);
        params2.put("bbs_id", bbsId);
        HTTPClient.post("/selectBoardArticle", params2, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int index, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    BbsVO vo = new BbsVO();
                    JSONArray array = new JSONArray(result);
                    JSONObject json = array.getJSONObject(0);
                    vo.set_id(json.getString("_id"));
                    vo.setBbs_id(json.getString("bbs_id"));
                    vo.setNtt_sj(json.getString("ntt_sj"));
                    vo.setNtt_cn(json.getString("ntt_cn"));
                    vo.setNtcr_nm(json.getString("ntcr_nm"));
                    vo.setAtch_file_id(json.getString("atch_file_id"));
                    vo.setFrst_time(json.getString("frst_time"));
                    vo.setLast_updt_time(json.getString("last_updt_time"));
                    vo.setRdcnt(json.getInt("rdcnt"));
                    vo.setLike(json.getInt("like"));
                    vo.setNtt_id(json.getString("ntt_id"));
                    vo.set__v(json.getInt("__v"));
                    vo.setPopPoint(json.getString("popPoint"));
/*                    JSONArray commentArray = json.getJSONArray("COMMENT");
                    for(int i = 0; i < commentArray.length() ; i++){
                        JSONObject comment = commentArray.getJSONObject(i);

                    }*/

                    setView(vo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

}
