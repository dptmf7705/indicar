package com.indicar.indicar_community.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.vo.BoardItemVO;

import java.util.ArrayList;

/**
 * Created by yeseul on 2018-02-25.
 */

public class BoardDetailAdapter extends RecyclerView.Adapter<BoardDetailAdapter.ViewHolder>  {
    private Context context;
    private ArrayList<BoardItemVO> items;

    public BoardDetailAdapter(Context context, ArrayList<BoardItemVO> items) {
        this.context = context;
        this.items = items;
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
        holder.iv_img.setImageResource(item.getImg());
        holder.tv_text.setText(item.getText());

//        holder.iv_img.setImageResource(R.drawable.car_img_2);
//        holder.tv_text.setText("android:text=\"반디캠은 월등한 성능을 보여주는 고성능 동영상 녹화 프로그램으로, 언제든 설치파일을 다운로드 받아 설치 및 사용이 가능하며, 정품을 구매하시고 정품 등록을 하시면 모든 기능을 제한 없이 사용하실 수 있습니다.");
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
}
