package com.indicar.indicar_community.model.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.indicar.indicar_community.BR;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BoardDetailVO extends BaseObservable {

    private String userProfileUrl = "";
    private String mainImageUrl = "";
    @SerializedName("bbs_id")       private String boardType = "";
    @SerializedName("ntt_id") private String boardId = "";
    @SerializedName("atch_file_id") private String atchFileId = "";
    @SerializedName("ntcr_nm") String userName = "";
    @SerializedName("ntcr_id") private String userKey = "";
    @SerializedName("like") private String likeCount = "";
    @SerializedName("ntt_sj") private String boardTitle = "";
    @SerializedName("ntt_cn") private String boardContent = "";
    @SerializedName("frst_time") private String firstDate = "";
    @SerializedName("last_updt_time") private String lastUpdateDate = "";
    @SerializedName("rdcnt") private String readCount = "";
    @SerializedName("CntCOMMENT") private String commentCount = "0";

    @Override
    public String toString() {
        return "BoardDetailVO{" +
                "userProfileUrl='" + userProfileUrl + '\'' +
                ", mainImageUrl='" + mainImageUrl + '\'' +
                ", boardType='" + boardType + '\'' +
                ", boardId='" + boardId + '\'' +
                ", atchFileId='" + atchFileId + '\'' +
                ", userName='" + userName + '\'' +
                ", userKey='" + userKey + '\'' +
                ", likeCount='" + likeCount + '\'' +
                ", boardTitle='" + boardTitle + '\'' +
                ", boardContent='" + boardContent + '\'' +
                ", firstDate='" + firstDate + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                ", readCount='" + readCount + '\'' +
                ", commentCount='" + commentCount + '\'' +
                '}';
    }

    @Bindable
    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
        notifyPropertyChanged(BR.userProfileUrl);
    }

    @Bindable
    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
        notifyPropertyChanged(BR.mainImageUrl);
    }

    @Bindable
    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
        notifyPropertyChanged(BR.boardType);
    }

    @Bindable
    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
        notifyPropertyChanged(BR.boardId);
    }

    @Bindable
    public String getAtchFileId() {
        return atchFileId;
    }

    public void setAtchFileId(String atchFileId) {
        this.atchFileId = atchFileId;
        notifyPropertyChanged(BR.atchFileId);
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
        notifyPropertyChanged(BR.userKey);
    }

    @Bindable
    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
        notifyPropertyChanged(BR.likeCount);
    }

    @Bindable
    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
        notifyPropertyChanged(BR.boardTitle);
    }

    @Bindable
    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
        notifyPropertyChanged(BR.boardContent);
    }

    @Bindable
    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
        notifyPropertyChanged(BR.firstDate);
    }

    @Bindable
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        notifyPropertyChanged(BR.lastUpdateDate);
    }

    @Bindable
    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
        notifyPropertyChanged(BR.readCount);
    }

    @Bindable
    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
        notifyPropertyChanged(BR.commentCount);
    }
}
