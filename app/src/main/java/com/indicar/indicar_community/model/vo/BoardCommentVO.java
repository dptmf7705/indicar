package com.indicar.indicar_community.model.vo;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import java.util.Date;

/**
 * Created by yeseul on 2018-04-12.
 *
 */

public class BoardCommentVO {

    public final ObservableField<String> boardType = new ObservableField<>();
    public final ObservableField<String> boardId = new ObservableField<>();
    public final ObservableInt commentIndex = new ObservableInt();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<Date> firstTime = new ObservableField<>();
    public final ObservableField<Date> lastUpdateTime = new ObservableField<>();

}
