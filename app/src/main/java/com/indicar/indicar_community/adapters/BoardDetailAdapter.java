package com.indicar.indicar_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.vo.BbsVO;
import com.indicar.indicar_community.vo.BoardItemVO;

import java.util.ArrayList;

/**
 * Created by yeseul on 2018-02-25.
 */

public class BoardDetailAdapter extends RecyclerView.Adapter<BoardDetailAdapter.ViewHolder>  {
    private Context context;
    private ArrayList<BoardItemVO> items;

    public BoardDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_board_detail_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BoardItemVO item = items.get(position);
        holder.iv_img.setImageBitmap(item.getBitmap());
        holder.tv_text.setText(item.getText());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public ImageView iv_img;
        public TextView tv_text;

        public ViewHolder(View view) {
            super(view);
            iv_img = view.findViewById(R.id.iv_img);
            tv_text = view.findViewById(R.id.tv_text);
        }
    }

    public void addImage(int i, Bitmap bitmap){
        items.get(i).setBitmap(bitmap);
    }

    public void addList(ArrayList<BoardItemVO> items){
        this.items = items;
    }

}
