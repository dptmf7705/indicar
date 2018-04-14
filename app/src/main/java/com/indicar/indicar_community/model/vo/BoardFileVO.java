package com.indicar.indicar_community.model.vo;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import java.util.Date;

/**
 * Created by yeseul on 2018-04-14.
 */

public class BoardFileVO {
    public ObservableField<String> atchFileId = new ObservableField<>();
    public ObservableInt fileIndex = new ObservableInt();
    public ObservableField<String> fileUrl = new ObservableField<>();
    public ObservableField<String> storeFileName = new ObservableField<>();
    public ObservableField<String> originalFileName = new ObservableField<>();
    public ObservableField<String> fileExtension = new ObservableField<>();
    public ObservableField<String> content = new ObservableField<>();
}
