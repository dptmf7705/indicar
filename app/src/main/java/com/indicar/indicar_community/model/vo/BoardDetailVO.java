package com.indicar.indicar_community.model.vo;

import android.database.Observable;
import android.databinding.ObservableField;

import java.util.Date;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BoardDetailVO {
    public ObservableField<String> boardType = new ObservableField<>();
    public ObservableField<String> boardId = new ObservableField<>();
    public ObservableField<String> atchFileId = new ObservableField<>();
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> likeCount = new ObservableField<>();
    public ObservableField<String> commentCount = new ObservableField<>();
    public ObservableField<String> boardSubject = new ObservableField<>();
    public ObservableField<String> boardContent = new ObservableField<>();
    public ObservableField<Date> firstDate = new ObservableField<>();
    public ObservableField<Date> lastUpdateDate = new ObservableField<>();
    public ObservableField<String> readCount = new ObservableField<>();

    @Override
    public String toString() {
        return "BoardDetailVO{" +
                "boardType=" + boardType.get() +
                ", boardId=" + boardId.get() +
                ", atchFileId=" + atchFileId.get() +
                ", userName=" + userName.get() +
                ", likeCount=" + likeCount.get() +
                ", commentCount=" + commentCount.get() +
                ", boardSubject=" + boardSubject.get() +
                ", boardContent=" + boardContent.get() +
                ", firstDate=" + firstDate.get() +
                ", lastUpdateDate=" + lastUpdateDate.get() +
                ", readCount=" + readCount.get() +
                '}';
    }
}
