package com.indicar.indicar_community.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.model.vo.BoardWriteVO;
import com.indicar.indicar_community.utils.CustomActionBar;
import com.indicar.indicar_community.view.adapter.BoardWriteListAdapter;
import com.indicar.indicar_community.view.adapter.viewHolder.BoardWriteViewHolder;
import com.indicar.indicar_community.viewmodel.IPickPhotoHelper;
import com.indicar.indicar_community.viewmodel.PickPhotoHelper;
import com.loopj.android.http.RequestParams;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.apptik.multiview.layoutmanagers.ViewPagerLayoutManager;

/**
 * TODO
 * 1. 페이지 하나 일 때 삭제 시, 내용이 있으면 reset
 * 2. 리사이클러 뷰 어댑터 : 페이지 추가, 사진 추가 기능 나눠서 구현
 *
 *
 *
 * */

public class BoardWriteDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private final int MAX_PAGE = 15;
    private int currentPage = -1;
    private int pageCount = 0;

    private ImageButton btnAdd;
    private ImageButton btnDelete;
    private TextView currentPageView;
    private TextView totalPageView;
    private ImageButton btnPrev;
    private ImageButton btnNext;

    private BoardWriteListAdapter adapter;
    private RecyclerView recyclerView;
    private ViewPagerLayoutManager layoutManager;

    private ImageButton btnSubmit;
    private ImageButton btnCancel;

    private ArrayList<BoardWriteVO> list;
    private BoardWriteViewHolder currentViewHolder;

    PickPhotoHelper pickPhotoHelper;

    /**
     * 뒤로가기 버튼 눌렀을 때
     * 액티비티 전환 애니메이션 설정
     * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w2_activity_board_write_detail);

        // 키보드 input mode
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        pickPhotoHelper = new PickPhotoHelper(this);

        initView();
        setupPager();
        addPage(); // 첫 페이지 추가
    }
/*

    private void initActionBar() {
        CustomActionBar.with(this).init(getSupportActionBar());
    }
*/


    private void initView() {

//        initActionBar();

        currentPageView = findViewById(R.id.currentPage);

        totalPageView = findViewById(R.id.totalPage);

        btnAdd = findViewById(R.id.btnAddSlide);
        btnAdd.setOnClickListener(this);

        btnDelete = findViewById(R.id.btnDeleteSlide);
        btnDelete.setOnClickListener(this);

        btnPrev = findViewById(R.id.buttonPrev);
        btnPrev.setOnClickListener(this);

        btnNext = findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(this);

        btnCancel = findViewById(R.id.buttonCancel);
        btnCancel.setOnClickListener(this);

        btnSubmit = findViewById(R.id.buttonSubmit);
        btnSubmit.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void onClick(View view) {

        /**
         * 리사이클러 뷰 관련 클릭 이벤트 처리
         */
        switch (view.getId()){
            case R.id.btnAddSlide: // 페이지 추가
                if(pageCount < MAX_PAGE) {
                    addPage();
                }
                break;
            case R.id.btnDeleteSlide: // 현재 페이지 삭제
                if(pageCount > 1) {
                    removePage();
                }
                break;
            case R.id.buttonPrev: // 이전 페이지로 이동
                if(currentPage > 0) {
                    pageChangeTo(currentPage - 1);
                }
                break;
            case R.id.buttonNext: // 다음 페이지로 이동
                if(currentPage < (pageCount - 1)) {
                    pageChangeTo(currentPage + 1);
                }
                break;
            case R.id.buttonCancel: // 글작성 취소

                break;
            case R.id.buttonSubmit: // 게시글 업로드
                list = adapter.getItems();
                insertBoardArticle();
                break;
        }

    }

    private void setupPager() {
        adapter = new BoardWriteListAdapter(this, R.layout.w2_layout_board_write_item,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /** 사진 추가 관련 클릭 이벤트 처리 */
                        if(view.getTag() != null){
                            int tag = Integer.parseInt(view.getTag().toString());

                            switch (tag) {
                                case 0: // 앨범에서 사진 선택
                                    pickPhotoHelper.pickFromAlbum(new IPickPhotoHelper.loadPhotoListCallBack<Uri>() {
                                        @Override
                                        public void onPhotoListLoaded(List<Uri> photoUriList) {

                                            final int IMAGE_COUNT = photoUriList.size(); // 리스트 개수

                                            ArrayList<BoardWriteVO> list = new ArrayList<>();

                                            for(int i = 0 ; i < IMAGE_COUNT ; i++){
                                                BoardWriteVO vo = new BoardWriteVO();
                                                vo.setUri(photoUriList.get(i));
                                                list.add(vo);
                                            }
                                            addPageList(list);
                                        }

                                        @Override
                                        public void onPhotoNotAvailable() {
                                        }
                                    });
                                    break;
                                case 1: // 카메라 사진 촬영
                                    pickPhotoHelper.pickFromCamera(new IPickPhotoHelper.loadPhotoCallBack<Uri>() {
                                        @Override
                                        public void onPhotoLoaded(Uri photoUri) {

                                            adapter.setImagePicked(currentPage, photoUri);
                                            addPage();
                                        }

                                        @Override
                                        public void onPhotoNotAvailable() {

                                        }
                                    });
                                    break;
                                case 2: // 적용된 이미지 삭제
                                    break;
                            }
                        }
                    }
                });

        layoutManager = new ViewPagerLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                currentPage = layoutManager.findFirstVisibleItemPosition();
                currentViewHolder = (BoardWriteViewHolder) recyclerView.findViewHolderForAdapterPosition(currentPage);
                currentPageView.setText("" + (currentPage + 1));
            }
        });
    }

    /**
     * currentPage 다음에 페이지 하나 추가한 후, 해당 페이지로 이동
     */
    private void addPage(){
        // pageCount 가 15보다 작을 때만 페이지를 추가한다.
        if(pageCount < MAX_PAGE) {
            /* 페이지 추가 */
           BoardWriteVO vo = new BoardWriteVO();
            adapter.addItem(currentPage + 1, vo); // 어댑터에 새로운 페이지 추가
            pageCount = adapter.getItemCount(); // 페이지 개수 어댑터에서 받아옴
            totalPageView.setText("" + pageCount); // 페이지 개수 출력

            pageChangeTo(currentPage + 1);
        } else {
            Toast.makeText(this, "페이지 수 최대", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *  list 개수 만큼 페이지 추가 (앨범 이미지 다중 선택시 호출)
     * */
    private void addPageList(ArrayList<BoardWriteVO> list){
        if((pageCount + list.size()) <= MAX_PAGE){
            adapter.addItemList(currentPage, list);
            pageCount = adapter.getItemCount();
            totalPageView.setText("" + pageCount);
            
            pageChangeTo(currentPage);
        } else{
            Toast.makeText(this, "페이지 수 최대", Toast.LENGTH_SHORT).show();
        }
    }

    private void addPageList(int pageCount){
        ArrayList<BoardWriteVO> list = new ArrayList<>();
        for(int i = 0 ; i < pageCount ; i++){
            list.add(new BoardWriteVO());
        }
        addPageList(list);
    }

    /**
     * currentPage 삭제
     * */
    private void removePage(){
        // pageCount 가 1보다 클 때만 페이지를 삭제한다.
        if(pageCount > 1){
            adapter.removeItem(currentPage);
            pageCount = adapter.getItemCount();
            totalPageView.setText("" + pageCount);
            pageChangeTo(currentPage);
        }
    }

    /**
     * 해당 position 의 페이지로 이동
     * */
    private void pageChangeTo(int position){
        currentPage = position;
        recyclerView.smoothScrollToPosition(currentPage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        pickPhotoHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void insertBoardArticle() {
        RequestParams params = new RequestParams();
        params.put("bbs_id", "test_main");
        params.put("ntt_sj", "제목");
        params.put("ntt_cn ", "내용");
        params.put("ntcr_nm", "글쓴이");

        for(int i = 0 ; i < list.size() ; i++) {
            if(list.get(i).getUri() != null) {
                try {
                    String filePath = list.get(i).getUri().getPath();
                    Log.d("", filePath);
                    params.put("file", new File(filePath));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if(list.get(i).getText() != null) {
                params.put("fileCn_" + i, list.get(i).getText());
            }
        }

    }
}
