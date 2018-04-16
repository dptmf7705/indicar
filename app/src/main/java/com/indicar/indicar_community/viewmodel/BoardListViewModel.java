package com.indicar.indicar_community.viewmodel;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.indicar.indicar_community.model.BaseModel;
import com.indicar.indicar_community.model.BoardModel;
import com.indicar.indicar_community.view.adapter.BaseRecyclerViewAdapter;

import java.util.HashMap;
import java.util.List;

import static com.indicar.indicar_community.view.adapter.BoardListAdapter.BOARD_ALL;
import static com.indicar.indicar_community.view.adapter.BoardListAdapter.BOARD_POPULAR;

/**
 * Created by yeseul on 2018-04-14.
 */

public class BoardListViewModel extends BaseViewModel {

    private static final String TAG = BoardListViewModel.class.getSimpleName();

    public final ObservableField<BaseRecyclerViewAdapter> adapter = new ObservableField<>();
    public final ObservableField<SwipeRefreshLayout.OnRefreshListener> onRefreshListener = new ObservableField<>();
    public final ObservableField<RecyclerView.LayoutManager> layoutManager = new ObservableField<>();
    public final ObservableBoolean isRefreshing = new ObservableBoolean(false);
    public final ObservableInt boardType = new ObservableInt();

    BoardModel model;

    public BoardListViewModel(){
        Log.d(TAG, "BoardListViewModel() called...");
        model = new BoardModel();
    }

    @Override
    public void onCreate() {

        Log.d(TAG, "BoardListViewModel() called...");

        onRefreshListener.set(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.get().clearItems();
                getBoardList();
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

        Log.d(TAG, "getBoardList() called...");

        HashMap<String, String> map = new HashMap<>();
        map.put("bbs_id", "all");

        if (boardType.get() == BOARD_POPULAR) map.put("searchCnd", "pop");
        else if (boardType.get() == BOARD_ALL) map.put("searchCnd", "");

        map.put("pageIndex", String.valueOf(getCurrentIndex()));

        model.getDataList(map, new BaseModel.LoadDataListCallBack() {
            @Override
            public void onDataListLoaded(List list) {
                if(list != null) {
                    Log.d("onDataListLoaded", list.toString());
                    adapter.get().addItems(list);
                    isRefreshing.set(false);
                    notifyObservers();
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    /**
     * 현재 게시물 페이지 index 계산
     *
     * 현재 보이는 페이지 개수 / 15
     * */
    private int getCurrentIndex() {
        int itemCount = adapter.get().getItemCount();
        int index = (itemCount / 15) + 1;
        Log.d(TAG, "getCurrentIndex() called... with index: " + index);
        return index;
    }


}
