package com.indicar.indicar_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.indicar.indicar_community.R;
import com.indicar.indicar_community.viewHolder.BoardWriteViewHolder;
import com.indicar.indicar_community.vo.BoardWriteVO;

import java.util.ArrayList;

/**
 * Created by yeseul on 2018-03-25.
 *
 * TODO
 * 1. ViewHolder 클래스 파일 따로 빼기
 * 2. -> popular, all board 도 마찬가지로. ViewHolder 만 따로만들고 어댑터 하나로 합치기
 *
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//sdk 24 이상, 누가(7.0)
 photoUri = FileProvider.getUriForFile(getApplicationContext(),// 7.0에서 바뀐 부분은 여기다.
 BuildConfig.APPLICATION_ID + ".provider", photoFile);
 } else {//sdk 23 이하, 7.0 미만
 photoUri = Uri.fromFile(photoFile);
 }
 *
 */

public class BoardWriteListAdapter extends RecyclerView.Adapter<BoardWriteViewHolder>{
    private Context context;
    private int layoutSrc; // layout source
    private ArrayList<BoardWriteVO> items = new ArrayList<>();  // write item list
    private View.OnClickListener onClickListener;

    public BoardWriteListAdapter(Context context, int layoutSrc, View.OnClickListener onClickListener) {
        this.context = context;
        this.layoutSrc = layoutSrc;
        this.onClickListener = onClickListener;
    }

    @Override
    public BoardWriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutSrc, null);
        BoardWriteViewHolder viewHolder = new BoardWriteViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BoardWriteViewHolder holder, int position) {
        final BoardWriteVO item = items.get(position);

        Uri uri = item.getUri();
        if(uri != null){
            holder.setImageViewVisible();
            Glide.with(context).load(uri).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    holder.imagePicked.setImageBitmap(resource);
                    holder.setButtonClickListener(onClickListener);
                }
            });
        } else {
            holder.setPickButtonsVisible();
            holder.setButtonClickListener(onClickListener);
        }

        if(item.getText() != null) {
            holder.textWrite.setText("" + item.getText());
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(int index, BoardWriteVO item){
        items.add(index, item);
        notifyItemInserted(index);
    }

    public void addItemList(int index, ArrayList<BoardWriteVO> item){
        items.addAll(index, item);
        notifyItemRangeInserted(index, item.size());
    }

    public void removeItem(int index){
        items.remove(index);
        notifyItemRemoved(index);
    }

    public void setImagePicked(int index, Uri uri){
        items.get(index).setUri(uri);
        notifyItemChanged(index);
    }

    public ArrayList<BoardWriteVO> getItems() {
        return items;
    }

}
