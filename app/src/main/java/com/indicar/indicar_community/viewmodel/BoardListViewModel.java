package com.indicar.indicar_community.viewmodel;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.indicar.indicar_community.model.BaseModel;
import com.indicar.indicar_community.model.BoardFileModel;
import com.indicar.indicar_community.model.BoardModel;
import com.indicar.indicar_community.model.vo.BoardDetailVO;
import com.indicar.indicar_community.model.vo.BoardFileVO;
import com.indicar.indicar_community.view.adapter.BaseRecyclerViewAdapter;
import com.indicar.indicar_community.view.adapter.BoardListAdapter;

import java.util.HashMap;
import java.util.List;

import static com.indicar.indicar_community.view.adapter.BoardListAdapter.BOARD_ALL;
import static com.indicar.indicar_community.view.adapter.BoardListAdapter.BOARD_POPULAR;

/**
 * Created by yeseul on 2018-04-14.
 */

public class BoardListViewModel extends BaseViewModel {

    private static final String TAG = BoardListViewModel.class.getSimpleName();

    public final ObservableField<BoardListAdapter> adapter = new ObservableField<>();
    public final ObservableField<SwipeRefreshLayout.OnRefreshListener> onRefreshListener = new ObservableField<>();
    public final ObservableField<RecyclerView.LayoutManager> layoutManager = new ObservableField<>();
    public final ObservableBoolean isRefreshing = new ObservableBoolean(false);
    public final ObservableInt boardType = new ObservableInt();

    BoardModel boardModel;
    BoardFileModel fileModel;

    Boolean isEndOfPage = false;
    int pageCount = 1;

    public BoardListViewModel(){
        Log.d(TAG, "BoardListViewModel() called...");
        boardModel = new BoardModel();
        fileModel = new BoardFileModel();
    }

    @Override
    public void onCreate() {

        Log.d(TAG, "BoardListViewModel() called...");

        onRefreshListener.set(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.get().clearItems();
                isEndOfPage = false;
                pageCount = 1;
                getBoardList();
            }
        });

        adapter.get().setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                BoardDetailVO vo = adapter.get().getItem(position);
                HashMap<String, String> map = new HashMap<>();
                map.put("atch_file_id", vo.getAtchFileId());
                map.put("bbs_id", vo.getBoardType());
                map.put("ntt_id", vo.getBoardId());
                map.put("commentCount", vo.getCommentCount());

                Log.d("onItemClick", map.toString());
                notifyObservers(map);
            }
        });

        getBoardList();
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

    }

    public void getBoardList(){

        if(isEndOfPage){
            return;
        }

        Log.d(TAG, "getBoardList() called...");

        HashMap<String, String> map = new HashMap<>();
        map.put("bbs_id", "all");

        if (boardType.get() == BOARD_POPULAR) map.put("searchCnd", "pop");
        else if (boardType.get() == BOARD_ALL) map.put("searchCnd", "");

        map.put("pageIndex", String.valueOf(pageCount));

        boardModel.getDataList(map, new BaseModel.LoadDataListCallBack() {
            @Override
            public void onDataListLoaded(List list) {
                int size = list.size();
                if(size != 15) {
                    isEndOfPage = true;
                }

                isRefreshing.set(false);
                notifyObservers();

                // 메인 사진을 받아온다
                getFile(list);
                pageCount++;
            }

            @Override
            public void onDataNotAvailable() {
                isEndOfPage = true;
            }
        });
    }

    private void getFile(List list) {

        for(int i = 0 ; i <  list.size() ; i++){
            final BoardDetailVO board = (BoardDetailVO) list.get(i);

            String atchFileId = board.getAtchFileId();

            if(!atchFileId.equals("")) {
                HashMap<String, String> map = new HashMap<>();
                map.put("atch_file_id", atchFileId);
                map.put("file_sn", String.valueOf(0));

                fileModel.getData(map, new BaseModel.LoadDataCallBack() {
                    @Override
                    public void onDataLoaded(Object data) {
                        BoardFileVO file = (BoardFileVO) data;

                        board.setMainImageUrl(file.getFileUrl());
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
            }
        }

        adapter.get().addItems(list);
    }

}
