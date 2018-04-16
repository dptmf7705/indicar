package com.indicar.indicar_community.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.BoardCommentItemBinding;
import com.indicar.indicar_community.model.vo.BoardCommentVO;

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
        holder.binding.setComment(itemList.get(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.board_comment_item, null);
        return new BoardCommentViewHolder(view);
    }



    public class BoardCommentViewHolder extends BaseViewHolder<BoardCommentItemBinding>{

        public BoardCommentViewHolder(View itemView) {
            super(itemView);
        }
    }

}
