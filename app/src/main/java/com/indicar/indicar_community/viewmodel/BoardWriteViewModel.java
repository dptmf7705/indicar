package com.indicar.indicar_community.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.indicar.indicar_community.model.BoardModel;
import com.indicar.indicar_community.model.vo.BoardWriteVO;
import com.indicar.indicar_community.view.adapter.BaseRecyclerViewAdapter;

import java.util.HashMap;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by yeseul on 2018-04-14.
 */

public class BoardWriteViewModel extends BaseViewModel {

    private static final String TAG = BoardWriteViewModel.class.getSimpleName();

    private final int MAX_PAGE = 15;

    BoardModel model;

    public final ObservableField<String> boardType = new ObservableField<>();
    public final ObservableField<String> carType = new ObservableField<>();
    public final ObservableInt currentPage = new ObservableInt();
    public final ObservableInt totalPage = new ObservableInt();
    public final ObservableField<BaseRecyclerViewAdapter> adapter = new ObservableField<>();
    public final ObservableField<LinearLayoutManager> layoutManager = new ObservableField<>();
    public final ObservableField<RecyclerView.OnScrollListener> onScrollListener = new ObservableField<>();
    public final ObservableField<RecyclerView.OnChildAttachStateChangeListener> onChildChangeListener = new ObservableField<>();
    
    public BoardWriteViewModel(){
        this.model = new BoardModel();
    }

    public void start(String boardType, String carType){
        this.boardType.set(boardType);
//        this.carType.set(carType);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }


    @Override
    public void onCreate() {

        addPage();

        onScrollListener.set(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if(newState == SCROLL_STATE_IDLE) {
                    currentPage.set(layoutManager.get().findFirstVisibleItemPosition());
                }
            }
        });

        onChildChangeListener.set(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

                currentPage.set(layoutManager.get().findFirstCompletelyVisibleItemPosition());
                totalPage.set(adapter.get().getItemCount());
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {

                currentPage.set(layoutManager.get().findFirstCompletelyVisibleItemPosition());
                totalPage.set(adapter.get().getItemCount());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onBackPressed() {

    }

    /**
     * 이전 페이지 이동
     * */
    public void scrollToPrevPage(){
        if(currentPage.get() > 1) {

            currentPage.set(currentPage.get() - 1);

            notifyObservers();
        }
    }

    /**
     * 다음 페이지 이동
     * */
    public void scrollToNextPage(){
        if(currentPage.get() < totalPage.get()) {

            currentPage.set(currentPage.get() + 1);

            notifyObservers();
        }
    }

    /**
     * 페이지 추가
     * */
    public void addPage(){

        if (adapter.get().getItemCount() == 0){

                adapter.get().addItem(new BoardWriteVO());
                currentPage.set(0);
                totalPage.set(1);

        } else if (totalPage.get() < MAX_PAGE) {

            adapter.get().addItem(currentPage.get() + 1, new BoardWriteVO());
            totalPage.set(totalPage.get() + 1);

            scrollToNextPage();
        }
    }

    /**
     * 페이지 삭제
     * */
    public void deleteCurrentPage(){
        if(totalPage.get() > 0) {

            adapter.get().removeItem(currentPage.get());
            totalPage.set(totalPage.get() - 1);
        }

        if(adapter.get().getItemCount() == 0) addPage();
    }

    /**
     * 게시물 등록
     * */
    public void submitBoardInsert(){

        List<BoardWriteVO> dataList = adapter.get().getItemList();

        HashMap<String, Object> map = new HashMap<>();
        map.put("bbs_id", boardType.get());
        map.put("ntt_sj", "제목없음");
        map.put("ntt_cn", "내용없음");
        map.put("ntcr_nm", "작성자");
        map.put("item_list", dataList);

        model.insertData(map);

        notifyObservers(ACTIVITY_FINISH);
    }

    /**
     * 게시물 취소
     * */
    public void cancelBoardInsert(){

        notifyObservers(ACTIVITY_FINISH);
    }

}
