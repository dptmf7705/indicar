package com.indicar.indicar_community.model.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;

import com.google.gson.annotations.SerializedName;
import com.indicar.indicar_community.BR;

/**
 * Created by yeseul on 2018-02-26.
 */

public class BoardWriteVO extends BaseObservable{
    private Uri imageUrl;
    private String writeText = "";
    private Boolean hasImage = false;
    private String filePath = "";

    @Bindable
    public Uri getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Uri imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    @Bindable
    public String getWriteText() {
        return writeText;
    }

    public void setWriteText(String writeText) {
        this.writeText = writeText;
        notifyPropertyChanged(BR.writeText);
    }

    @Bindable
    public Boolean getHasImage() {
        return hasImage;
    }

    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
        notifyPropertyChanged(BR.hasImage);
    }

    @Bindable
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
        notifyPropertyChanged(BR.filePath);
    }
}
