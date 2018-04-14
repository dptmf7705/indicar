package com.indicar.indicar_community.view.adapter.viewHolder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.indicar.indicar_community.databinding.BoardCommentItemBinding;

/**
 * Created by yeseul on 2018-04-13.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder {

    BoardCommentItemBinding binding;

    public CommentViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public BoardCommentItemBinding getBinding() {
        return binding;
    }
}
