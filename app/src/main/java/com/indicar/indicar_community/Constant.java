package com.indicar.indicar_community;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by yeseul on 2018-04-28.
 */

public class Constant {

    public static final ObservableField<String> DAY_LIFE = new ObservableField<>("daylife");
    public static final ObservableField<String> MARKET = new ObservableField<>("market");
    public static final ObservableField<String> DIY = new ObservableField<>("diy");

    public class BoardWrite{
        public static final int NEW = 0;
        public static final int UPDATE = 0;
    }

}
