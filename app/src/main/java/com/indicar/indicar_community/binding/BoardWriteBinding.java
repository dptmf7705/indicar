package com.indicar.indicar_community.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageButton;

/**
 * Created by yeseul on 2018-04-28.
 */

public class BoardWriteBinding {

    @BindingAdapter({"setSelected"})
    public static void setSelected(ImageButton imageView, Boolean bool){
        imageView.setSelected(bool);
    }

}
