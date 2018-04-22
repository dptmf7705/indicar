package com.indicar.indicar_community.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.BoardAllItemBinding;
import com.indicar.indicar_community.databinding.BoardPopularItemBinding;
import com.indicar.indicar_community.model.vo.BoardDetailVO;

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
        itemList.get(position).setMainImageUrl(imageUrl);
        notifyItemChanged(position);
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        BoardDetailVO vo = itemList.get(position);

        if(holder instanceof BoardPopularViewHolder) {

            ((BoardPopularViewHolder) holder).binding.setItem(vo);
            ((BoardPopularViewHolder) holder).binding.imageMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(view, pos);
                    }
                }
            });

            if((position % 2) == 0){ // 짝수
                ((BoardPopularViewHolder) holder).binding.container.setGravity(Gravity.RIGHT);
            } else {
                ((BoardPopularViewHolder) holder).binding.container.setGravity(Gravity.LEFT);
            }

        } else if(holder instanceof BoardAllViewHolder){

            ((BoardAllViewHolder) holder).binding.setItem(vo);
            ((BoardAllViewHolder) holder).binding.imageMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(view, pos);
                    }
                }
            });
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
