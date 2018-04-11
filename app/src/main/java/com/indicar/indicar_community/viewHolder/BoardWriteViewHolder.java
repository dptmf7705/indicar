package com.indicar.indicar_community.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.indicar.indicar_community.R;

/**
 * Created by yeseul on 2018-04-11.
 */


public class BoardWriteViewHolder extends RecyclerView.ViewHolder{
    public ImageButton buttonAlbum;
    public ImageButton buttonCamera;
    public ImageButton imagePicked;
    public EditText textWrite;
    public View view;

    public BoardWriteViewHolder(View view) {
        super(view);
        buttonAlbum = view.findViewById(R.id.buttonAlbum);
        buttonCamera = view.findViewById(R.id.buttonCamera);
        imagePicked = view.findViewById(R.id.imagePicked);
        textWrite = view.findViewById(R.id.textWrite);
        this.view = view;
    }

    public void setButtonClickListener(View.OnClickListener onClickListener){
        if(buttonAlbum.getVisibility() != View.GONE) {
            buttonAlbum.setTag(0);
            buttonAlbum.setOnClickListener(onClickListener);
        }
        if(buttonCamera.getVisibility() != View.GONE) {
            buttonCamera.setTag(1);
            buttonCamera.setOnClickListener(onClickListener);
        }
        if(imagePicked.getVisibility() != View.GONE) {
            imagePicked.setTag(2);
            imagePicked.setOnClickListener(onClickListener);
        }
    }

    public void setPickButtonsVisible(){
        buttonCamera.setVisibility(View.VISIBLE);
        buttonAlbum.setVisibility(View.VISIBLE);
        imagePicked.setVisibility(View.GONE);
    }

    public void setImageViewVisible(){
        buttonCamera.setVisibility(View.GONE);
        buttonAlbum.setVisibility(View.GONE);
        imagePicked.setVisibility(View.VISIBLE);
    }
}
