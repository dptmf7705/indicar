package com.indicar.indicar_community.model.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;

import com.indicar.indicar_community.BR;


/**
 * Created by yeseul on 2018-04-23.
 */

public class BoardUpdateVO extends BaseObservable{

    private Uri imageUrl;
    private String filePath = "";
    private Boolean hasImage = false;
    private String command;
    private String content = "";
    private int fileIndex;
    private String writeText = "";

    @Bindable
    public Uri getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Uri imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    @Bindable
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
        notifyPropertyChanged(BR.imageUrl);
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
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
        notifyPropertyChanged(BR.command);
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }

    @Bindable
    public int getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(int fileIndex) {
        this.fileIndex = fileIndex;
        notifyPropertyChanged(BR.fileIndex);
    }

    @Bindable
    public String getWriteText() {
        return writeText;
    }

    public void setWriteText(String writeText) {
        this.writeText = writeText;
        notifyPropertyChanged(BR.writeText);
    }
}
