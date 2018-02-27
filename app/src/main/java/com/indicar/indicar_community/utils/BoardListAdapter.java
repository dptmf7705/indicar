package com.indicar.indicar_community.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.view.activity.BoardDetailActivity;
import com.indicar.indicar_community.vo.BoardVO;

import java.util.ArrayList;

/**
 * Created by yeseul on 2018-02-23.
 */

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BoardVO> items;

    public BoardListAdapter(Context context, ArrayList<BoardVO> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_board_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BoardVO item = items.get(position);
        holder.iv_board_img.setImageResource(item.getImage());
        holder.tv_user_name.setText(item.getUserName());
        holder.tv_board_category.setText(item.getBoardCategory());
        holder.tv_board_content.setText(item.getBoardText());
        holder.tv_comment.setText(""+item.getComment());
        holder.tv_like.setText(""+item.getLike());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView iv_board_img;
        public TextView tv_user_name;
        public TextView tv_board_content;
        public TextView tv_like;
        public TextView tv_comment;
        public TextView tv_board_category;

        public ViewHolder(View view) {
            super(view);
            iv_board_img = view.findViewById(R.id.iv_board_img);
            tv_user_name = view.findViewById(R.id.tv_user_name);
            tv_board_content = view.findViewById(R.id.tv_board_content);
            tv_like = view.findViewById(R.id.tv_like);
            tv_comment = view.findViewById(R.id.tv_comment);
            tv_board_category = view.findViewById(R.id.tv_board_category);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, BoardDetailActivity.class);
            context.startActivity(intent);
        }
    }

}
