package com.indicar.indicar_community.vo;

import android.graphics.Bitmap;

/**
 * Created by yeseul on 2018-02-26.
 */

public class BoardWriteVO {
    private Bitmap bitmap;
    private String text;

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
