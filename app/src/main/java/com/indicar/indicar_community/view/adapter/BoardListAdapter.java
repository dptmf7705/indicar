package com.indicar.indicar_community.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.view.BoardDetailActivity;
import com.indicar.indicar_community.model.vo.BbsVO;
import com.indicar.indicar_community.model.vo.FileDetailVO;

import java.util.ArrayList;

/**
 * TODO
 *
 * Created by yeseul on 2018-02-23.
 */

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.ViewHolder>{
    private Context context;
    private ArrayList<BbsVO> items; // 게시글 리스트
    private int layoutSrc;

    public BoardListAdapter(Context context, int layoutSrc) {
        this.context = context;
        this.layoutSrc = layoutSrc;
        items = new ArrayList<>(); // 게시글 리스트 생성
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutSrc, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 게시글 내용을 뷰에 띄워준다.
     * */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BbsVO item = items.get(position);
        Bitmap bitmap = items.get(position).getMainImage();
        if(bitmap != null) {
            holder.iv_board_img.setImageBitmap(bitmap);
        } else{
            holder.iv_board_img.setImageResource(R.drawable.btn_category_2_sale_unclicked); // 기본 이미지
        }

        holder.tv_user_name.setText(item.getNtcr_nm());
        holder.tv_board_category.setText(item.getBbs_id());
        holder.tv_board_content.setText(item.getNtt_cn());
        holder.tv_comment.setText("" + item.getCntCOMMENT());
        holder.tv_like.setText("" + item.getLike());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BoardDetailActivity.class);
                intent.putExtra("ntt_id", item.getNtt_id());
                intent.putExtra("bbs_id", item.getBbs_id());
                intent.putParcelableArrayListExtra("files", item.getFileList());
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.enter_no_anim_start, R.anim.exit_no_anim_start);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(items != null) {
            return items.size();
        }else{
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public ImageView iv_board_img;
        public TextView tv_user_name;
        public TextView tv_board_content;
        public TextView tv_like;
        public TextView tv_comment;
        public TextView tv_board_category;
        public View view;

        public ViewHolder(View view) {
            super(view);
            iv_board_img = view.findViewById(R.id.iv_board_img);
            tv_user_name = view.findViewById(R.id.tv_user_name);
            tv_board_content = view.findViewById(R.id.tv_board_content);
            tv_like = view.findViewById(R.id.tv_like);
            tv_comment = view.findViewById(R.id.tv_comment);
            tv_board_category = view.findViewById(R.id.tv_board_category);
            this.view = view;
        }
    }

    public void addList(ArrayList<BbsVO> list){
        int currentIndex = this.items.size();
        this.items.addAll(list);

        notifyItemRangeInserted(currentIndex, list.size());
    }

    public void addImage(BbsVO item, Bitmap bitmap){
        int index = this.items.indexOf(item);
        item.setMainImage(bitmap);
        notifyItemChanged(index);
    }

    public void addFiles(BbsVO item, ArrayList<FileDetailVO> files){
        int index = this.items.indexOf(item);
        items.get(index).setFileList(files);
    }

    public void removeAll(){
        while(items.size() > 0) {
            int pos = items.size() - 1;
            items.remove(pos);
            notifyItemRemoved(pos);
        }
    }
}
