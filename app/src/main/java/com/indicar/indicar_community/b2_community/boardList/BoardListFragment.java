package com.indicar.indicar_community.b2_community.boardList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ScrollingView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.indicar.indicar_community.BaseFragment;
import com.indicar.indicar_community.R;
import com.indicar.indicar_community.adapter.BoardListAdapter;
import com.indicar.indicar_community.b2_community.boardDetail.BoardDetailActivity;
import com.indicar.indicar_community.databinding.BoardListFragmentBinding;
import com.indicar.indicar_community.utils.RecyclerViewDecoration;
import com.indicar.indicar_community.utils.ScrollBottonAction;

import java.util.HashMap;
import java.util.Observable;

import static com.indicar.indicar_community.adapter.BoardListAdapter.BOARD_ALL;
import static com.indicar.indicar_community.adapter.BoardListAdapter.BOARD_POPULAR;


/**
 * Created by yeseul on 2018-04-17.
 */

public class BoardListFragment extends BaseFragment<BoardListFragmentBinding> {

    private static final String TAG = BoardListFragment.class.getSimpleName();

    BoardListViewModel viewModel;
    int boardType;

    float scrollY = 0;
    float oldScrollY = 0;
    float scrollUp = 0;
    float scrollDown = 0;

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
            binding.recyclerViewBoardContainer.addItemDecoration(new RecyclerViewDecoration(5, 0, 5, 5));
        }

        viewModel.adapter.set(new BoardListAdapter(context, boardType));

        new ScrollBottonAction(binding.recyclerViewBoardContainer).setOnScrollBottomListener(new ScrollBottonAction.onScrollEndListener() {
            @Override
            public void onScrollBottom(ScrollingView view) {
                Toast.makeText(context, "onScrollBottom", Toast.LENGTH_SHORT).show();
                viewModel.getBoardList();
            }
        });

        viewModel.onCreate();

        binding.setViewModel(viewModel);
    }

    @Override
    public void update(Observable observable, Object o) {

        if(o == null) {
            if (viewModel.isRefreshing.get() == false) {
                binding.refreshLayout.setRefreshing(false);
            }
        } else if (o != null){
            if(o instanceof HashMap){
                openBoardDetail((HashMap<String, String>) o);
            }
        }
    }

    private void openBoardDetail(HashMap<String, String> map){
        Intent intent = new Intent(context, BoardDetailActivity.class);
        intent.putExtra("bbs_id", map.get("bbs_id"));
        intent.putExtra("ntt_id", map.get("ntt_id"));
        intent.putExtra("commentCount", map.get("commentCount"));
        startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
    }
}
