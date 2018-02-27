package com.indicar.indicar_community.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.view.activity.AddPhotoActivity;
import com.indicar.indicar_community.vo.BoardWriteVO;

import java.util.ArrayList;

/**
 * Created by yeseul on 2018-02-26.
 */

public class BoardWriteAdapter extends RecyclerView.Adapter<BoardWriteAdapter.ViewHolder>{
    private Context context;
    private ArrayList<BoardWriteVO> items;

    public BoardWriteAdapter(Context context, ArrayList<BoardWriteVO> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_board_write_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //////////////////// 흠........어렵군......... ////////////////////
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public LinearLayout ll_write_btn_container;
        public ImageView iv_from_album;
        public ImageView iv_from_camera;
        public ImageView iv_write_photo;
        public EditText et_write_text;

        public ViewHolder(View view) {
            super(view);

            ll_write_btn_container = view.findViewById(R.id.ll_write_btn_container);
            iv_from_album = view.findViewById(R.id.iv_from_album);
            iv_from_camera = view.findViewById(R.id.iv_from_camera);
            iv_write_photo = view.findViewById(R.id.iv_write_photo);
            et_write_text = view.findViewById(R.id.et_write_text);
            iv_from_album.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddPhotoActivity.class);
                    context.startActivity(intent);
                }
            });
        }

    }
}
