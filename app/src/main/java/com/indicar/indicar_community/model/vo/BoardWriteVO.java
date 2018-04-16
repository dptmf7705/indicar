package com.indicar.indicar_community.model.vo;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;

/**
 * Created by yeseul on 2018-02-26.
 */

public class BoardWriteVO{

    public final ObservableField<Uri> imageUrl = new ObservableField<>();

    public final ObservableField<String> writeText = new ObservableField<>();

    public final ObservableBoolean hasImage = new ObservableBoolean(false);

    public final ObservableField<String> filePath = new ObservableField<>();

}
