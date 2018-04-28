package com.indicar.indicar_community.b2_community.boardWrite;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;

import com.indicar.indicar_community.BaseViewModel;
import com.indicar.indicar_community.Constant;
import com.indicar.indicar_community.R;
import com.indicar.indicar_community.adapter.BoardWriteAdapter;
import com.indicar.indicar_community.data.dao.BaseDao;
import com.indicar.indicar_community.data.dao.BoardDao;
import com.indicar.indicar_community.data.vo.BoardWriteVO;
import com.indicar.indicar_community.utils.CustomAlertDialog;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import io.apptik.multiview.layoutmanagers.ViewPagerLayoutManager;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by yeseul on 2018-04-28.
 */

public class BoardWriteViewModel extends BaseViewModel {

    private static final int MAX_PAGE = 15;

    public final ObservableField<String> boardType = new ObservableField<>(Constant.DAY_LIFE.get());

    public final ObservableBoolean isIntroPage = new ObservableBoolean(true);

    public final ObservableList<BoardWriteVO> items = new ObservableArrayList<>();

    public final ObservableInt currentPage = new ObservableInt(0);

    private BoardDao model;

    @NonNull
    private BoardWriteNavigator navigator;

    public BoardWriteViewModel(){
        model = new BoardDao();
    }

    public void setNavigator(BoardWriteNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onBackPressed() {
        navigator.onCancelWrite();
    }

    public void setBoardType(View view) {
        String tag = view.getTag().toString();
        boardType.set(tag);
    }

    public void onNextButtonClicked(){
        navigator.onPageChangedToDetail();
        isIntroPage.set(false);
    }

    public void onCancelButtonClicked(){
        navigator.onCancelWrite();
    }

    public void onSubmitButtonClicked(){
        navigator.onSubmitWrite();
    }

    public void uploadBoard(){
        File[] fileList = new File[items.size()];

        RequestParams params = new RequestParams();
        params.put("bbs_id", boardType.get());
        params.put("ntt_sj", "제목을 입력하세요.");
        params.put("ntt_cn", "내용을 입력하세요.");
        params.put("ntcr_nm", "이예슬");
        params.put("ntcr_id", "admin");

        for(int i = 0 ; i < items.size() ; i++){
            params.put("fileCn_" + i, items.get(i).getWriteText());
            fileList[i] = new File(items.get(i).getFilePath());
        }

        try {
            params.put("file", fileList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        model.insertData(params, new BaseDao.LoadDataCallBack() {
            @Override
            public void onDataLoaded(Object data) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    public void addNewPage(RecyclerView recyclerView){
        BoardWriteAdapter adapter = (BoardWriteAdapter) recyclerView.getAdapter();

        if(items.size() == 0){ // add first page
            adapter.addItem(new BoardWriteVO());
            currentPage.set(0);
        } else if(items.size() < MAX_PAGE){ // add next to current page
            adapter.addItem(currentPage.get() + 1, new BoardWriteVO());
        }
    }

    public void addPageList(RecyclerView recyclerView, List<BoardWriteVO> list){
        BoardWriteAdapter adapter = (BoardWriteAdapter) recyclerView.getAdapter();

        adapter.addItems(list);
    }

    public void deleteCurrentPage(RecyclerView recyclerView){
        BoardWriteAdapter adapter = (BoardWriteAdapter) recyclerView.getAdapter();

        if(items.size() > 0) {
            adapter.removeItem(currentPage.get());
        }

        if(items.size() == 0) { // add page if list is empty
            addNewPage(recyclerView);
        }
    }

    public void onDeletePageClicked(final RecyclerView recyclerView){
        BoardWriteVO vo = items.get(currentPage.get());
        if(vo.getHasImage() == false && vo.getWriteText().equals("")){
            deleteCurrentPage(recyclerView);
            return;
        }

        Display display = ((Activity)recyclerView.getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        final CustomAlertDialog dialog = new CustomAlertDialog(recyclerView.getContext());
        dialog.setImageId(R.drawable.button_trash)
                .setTitle("이 슬라이드를 정말로 삭제하시겠습니까?")
                .setSubTitle("delete this page.")
                .setPositiveButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteCurrentPage(recyclerView);
                        dialog.dismiss();
                    }
                })
                .setSize((int) (size.x * 0.9), (int) (size.y * 0.25))
                .show();
    }

    public void onScrollChanged(RecyclerView recyclerView, int state){
        if(state == SCROLL_STATE_IDLE) {
            getCurrentItem(recyclerView.getLayoutManager());
        }
    }

    public void getCurrentItem(RecyclerView.LayoutManager layoutManager){
        int current = ((ViewPagerLayoutManager) layoutManager).findFirstVisibleItemPosition();
        currentPage.set(current);
    }

    public void scrollToNextPage(RecyclerView recyclerView){
        if(currentPage.get() < items.size())
            recyclerView.smoothScrollToPosition(currentPage.get() + 1);
    }

    public void scrollToPrevPage(RecyclerView recyclerView){
        if(currentPage.get() > 0)
            recyclerView.smoothScrollToPosition(currentPage.get() - 1);
    }
}
