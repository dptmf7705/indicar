package com.indicar.indicar_community.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ScrollingView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.BoardListFragmentBinding;
import com.indicar.indicar_community.databinding.PopularBoardFragmentBinding;
import com.indicar.indicar_community.utils.ScrollBottonAction;
import com.indicar.indicar_community.view.adapter.BoardListAdapter;
import com.indicar.indicar_community.viewmodel.BoardListViewModel;

import java.util.Observable;

import static com.indicar.indicar_community.view.adapter.BoardListAdapter.BOARD_ALL;
import static com.indicar.indicar_community.view.adapter.BoardListAdapter.BOARD_POPULAR;

/**
 * Created by yeseul on 2018-04-17.
 */

public class BoardListFragment extends BaseFragment<BoardListFragmentBinding> {

    private static final String TAG = BoardListFragment.class.getSimpleName();

    BoardListViewModel viewModel;
    int boardType;

    @Override
    protected int getLayoutId() {
        return R.layout.board_list_fragment;
    }

    public BoardListFragment(){
        Log.d(TAG, "BoardListFragment() called...");
        viewModel = new BoardListViewModel();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardType = getArguments().getInt("boardType");

        Log.d(TAG, "onCreate() called... with boardType: " + boardType);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume() called...");
        super.onResume();

        viewModel.addObserver(this);
        viewModel.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause() called...");
        viewModel.deleteObserver(this);
        viewModel.onPause();

        super.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated() called...");
        super.onViewCreated(view, savedInstanceState);

        viewModel.boardType.set(boardType);
        if(boardType == BOARD_POPULAR) {
            viewModel.layoutManager.set(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        } else if(boardType == BOARD_ALL){
            viewModel.layoutManager.set(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }
        viewModel.adapter.set(new BoardListAdapter(context, boardType));
        viewModel.onCreate();

        binding.setViewModel(viewModel);

        new ScrollBottonAction(binding.recyclerViewBoardContainer).setOnScrollBottomListener(new ScrollBottonAction.onScrollEndListener() {
            @Override
            public void onScrollBottom(ScrollingView view) {
                viewModel.getBoardList();
            }
        });

    }

    @Override
    public void update(Observable observable, Object o) {
        if(viewModel.isRefreshing.get() == false){
            binding.refreshLayout.setRefreshing(false);
        }
    }
}
