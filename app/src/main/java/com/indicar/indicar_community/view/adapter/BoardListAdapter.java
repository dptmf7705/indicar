package com.indicar.indicar_community.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.BoardAllItemBinding;
import com.indicar.indicar_community.databinding.BoardPopularItemBinding;
import com.indicar.indicar_community.model.vo.BoardDetailVO;

import java.util.List;

/**
 * Created by yeseul on 2018-04-17.
 */

public class BoardListAdapter extends BaseRecyclerViewAdapter<BoardDetailVO, RecyclerView.ViewHolder> {

    public static final int BOARD_POPULAR = 0;
    public static final int BOARD_ALL = 1;

    private int viewType;

    public BoardListAdapter(Context context) {
        super(context);
    }

    public BoardListAdapter(Context context, int viewType){
        super(context);
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType(int position) {
        if(viewType == BOARD_POPULAR){
            return BOARD_POPULAR;
        } else {
            return BOARD_ALL;
        }
    }

    public void setBoardFile(int position, String imageUrl){
        itemList.get(position).mainImageUrl.set(imageUrl);
        notifyItemChanged(position);
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position) {
        BoardDetailVO vo = itemList.get(position);
        if(holder instanceof BoardPopularViewHolder) {
            ((BoardPopularViewHolder) holder).binding.setItem(vo);
        } else if(holder instanceof BoardAllViewHolder){
            ((BoardAllViewHolder) holder).binding.setItem(vo);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == BOARD_POPULAR) {
            View view = LayoutInflater.from(context).inflate(R.layout.board_popular_item, null);
            return new BoardPopularViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.board_all_item, null);
            return new BoardAllViewHolder(view);
        }
    }

    public class BoardPopularViewHolder extends BaseViewHolder<BoardPopularItemBinding>{

        public BoardPopularViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class BoardAllViewHolder extends BaseViewHolder<BoardAllItemBinding>{

        public BoardAllViewHolder(View itemView) {
            super(itemView);
        }
    }
}
