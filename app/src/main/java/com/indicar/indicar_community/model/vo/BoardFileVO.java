package com.indicar.indicar_community.model.vo;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import java.util.Date;

/**
 * Created by yeseul on 2018-04-14.
 */

public class BoardFileVO {

    public final ObservableField<String> atchFileId = new ObservableField<>();

    public final ObservableInt fileIndex = new ObservableInt();

    public final ObservableField<String> fileUrl = new ObservableField<>();

    public final ObservableField<String> storeFileName = new ObservableField<>();

    public final ObservableField<String> originalFileName = new ObservableField<>();

    public final ObservableField<String> fileExtension = new ObservableField<>();

    public final ObservableField<String> content = new ObservableField<>();

}
