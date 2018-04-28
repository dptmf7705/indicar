package com.indicar.indicar_community.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.data.vo.BoardCommentVO;
import com.indicar.indicar_community.databinding.BoardCommentItemBinding;

import java.util.List;

/**
 * Created by yeseul on 2018-04-14.
 */

public class BoardCommentAdapter extends BaseRecyclerViewAdapter<BoardCommentVO, BoardCommentAdapter.BoardCommentViewHolder> {

    public BoardCommentAdapter(Context context){
        super(context);
    }

    public BoardCommentAdapter(Context context, List<BoardCommentVO> list){
        super(context, list);
    }

    @Override
    protected void onBindView(BoardCommentViewHolder holder, int position) {
        final int pos = position;
        holder.binding.setComment(itemList.get(pos));

        holder.binding.textContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if(onItemLongClickListener != null){
                    onItemLongClickListener.onItemLongClick(view, pos);
                }

                return false;
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.board_comment_item, null);
        return new BoardCommentViewHolder(view);
    }

    public class BoardCommentViewHolder extends BaseViewHolder<BoardCommentItemBinding> {

        public BoardCommentViewHolder(View itemView) {
            super(itemView);
        }
    }

}
