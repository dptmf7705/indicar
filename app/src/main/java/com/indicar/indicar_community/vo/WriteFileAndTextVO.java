package com.indicar.indicar_community.vo;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yeseul on 2018-03-25.
 */

public class WriteFileAndTextVO implements Parcelable{
    private Bitmap bitmap;
    private String text;

    public WriteFileAndTextVO() {
    }

    public WriteFileAndTextVO(Bitmap bitmap, String text) {
        this.bitmap = bitmap;
        this.text = text;
    }

    protected WriteFileAndTextVO(Parcel in) {
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        text = in.readString();
    }

    public static final Creator<WriteFileAndTextVO> CREATOR = new Creator<WriteFileAndTextVO>() {
        @Override
        public WriteFileAndTextVO createFromParcel(Parcel in) {
            return new WriteFileAndTextVO(in);
        }

        @Override
        public WriteFileAndTextVO[] newArray(int size) {
            return new WriteFileAndTextVO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(bitmap, i);
        parcel.writeString(text);
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
