package com.indicar.indicar_community.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.model.vo.BoardCommentVO;
import com.indicar.indicar_community.view.adapter.viewHolder.CommentViewHolder;

import java.util.List;

/**
 * Created by yeseul on 2018-04-12.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    Context context;
    List<BoardCommentVO> commentList;

    public CommentListAdapter(Context context, List<BoardCommentVO> commentList){
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.board_comment_item, null);
        CommentViewHolder viewHolder = new CommentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        BoardCommentVO comment = getItem(position);
        holder.getBinding().setComment(comment);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public BoardCommentVO getItem(int position){
        return commentList.get(position);
    }
}
