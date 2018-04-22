package com.indicar.indicar_community.model.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.google.gson.annotations.SerializedName;
import com.indicar.indicar_community.BR;

import java.util.Date;

/**
 * Created by yeseul on 2018-04-14.
 */

public class BoardFileVO extends BaseObservable {

    @SerializedName("atch_file_id") private String atchFileId = "";
    @SerializedName("file_sn") private int fileIndex;
    @SerializedName("file_stre_cours") private String fileUrl = "";
    @SerializedName("stre_file_nm") private String storeFileName = "";
    @SerializedName("orignl_file_nm") private String originalFileName = "";
    @SerializedName("file_extsn") private String fileExtension = "";
    @SerializedName("file_cn") private String content = "";

    @Bindable
    public String getAtchFileId() {
        return atchFileId;
    }

    public void setAtchFileId(String atchFileId) {
        this.atchFileId = atchFileId;
        notifyPropertyChanged(BR.atchFileId);
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
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        notifyPropertyChanged(BR.fileUrl);
    }

    @Bindable
    public String getStoreFileName() {
        return storeFileName;
    }

    public void setStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
        notifyPropertyChanged(BR.storeFileName);
    }

    @Bindable
    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
        notifyPropertyChanged(BR.originalFileName);
    }

    @Bindable
    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
        notifyPropertyChanged(BR.fileExtension);
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }
}
