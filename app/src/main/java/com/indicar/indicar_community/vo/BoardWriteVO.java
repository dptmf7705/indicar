package com.indicar.indicar_community.vo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yeseul on 2018-02-26.
 */

public class BoardWriteVO{
    private Uri uri;
    private String text;

    public BoardWriteVO(){

    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
