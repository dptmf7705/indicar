package com.indicar.indicar_community.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.BoardDetailItemBinding;
import com.indicar.indicar_community.databinding.BoardDetailItemHeaderBinding;
import com.indicar.indicar_community.model.vo.BoardDetailVO;
import com.indicar.indicar_community.model.vo.BoardFileVO;
import com.indicar.indicar_community.view.BoardDetailActivity;

import java.util.List;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BoardDetailAdapter extends BaseRecyclerViewAdapter<BoardFileVO, RecyclerView.ViewHolder> {

    private static final String TAG = BoardDetailAdapter.class.getSimpleName();

    private static int TYPE_HEADER = 0;
    private static int TYPE_ITEM = 1;

    public BoardDetailVO boardHeader;

    public BoardDetailAdapter(Context context){
        super(context);
    }

    public BoardDetailAdapter(Context context, List<BoardFileVO> list){
        super(context, list);
    }

    public BoardDetailAdapter(Context context, List<BoardFileVO> list, BoardDetailVO boardHeader){
        super(context, list);
        this.boardHeader = boardHeader;
    }

    public void setBoardHeader(BoardDetailVO boardHeader){
        this.boardHeader = boardHeader;
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof BoardDetailHeaderViewHolder){

            Log.d(TAG, "onBindView() with ViewHolder: BoardDetailHeaderViewHolder");
            Log.d(TAG, "onBindView() board info: " + boardHeader.toString());

            ((BoardDetailHeaderViewHolder) holder).binding.setViewModel(((BoardDetailActivity)context).getBoardDetailViewModel());
            ((BoardDetailHeaderViewHolder) holder).binding.setBoard(boardHeader);
            ((BoardDetailHeaderViewHolder) holder).binding.setBoardItem(itemList.get(position));

        } else if (holder instanceof BoardDetailViewHolder){

            Log.d(TAG, "onBindView() with ViewHolder: BoardDetailViewHolder");

            ((BoardDetailViewHolder) holder).binding.setBoardItem(itemList.get(position));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_HEADER) {

            View view = LayoutInflater.from(context).inflate(R.layout.board_detail_item_header, null);
            return new BoardDetailHeaderViewHolder(view);

        } else {

            View view = LayoutInflater.from(context).inflate(R.layout.board_detail_item, null);
            return new BoardDetailViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public class BoardDetailViewHolder extends RecyclerView.ViewHolder {

        BoardDetailItemBinding binding;

        public BoardDetailViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    public class BoardDetailHeaderViewHolder extends RecyclerView.ViewHolder {

        BoardDetailItemHeaderBinding binding;

        public BoardDetailHeaderViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "BoardDetailHeaderViewHolder()");
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
