package com.indicar.indicar_community.vo;

import android.graphics.Bitmap;

/**
 * Created by yeseul on 2018-02-25.
 */

public class BoardItemVO {
    private int index;
    private Bitmap bitmap;
    private String text;

    public BoardItemVO() {

    }

    public BoardItemVO(int index, Bitmap bitmap, String text) {
        this.index = index;
        this.bitmap = bitmap;
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
