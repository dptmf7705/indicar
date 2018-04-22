package com.indicar.indicar_community.view.binding;

import android.databinding.BindingConversion;
import android.util.Log;
import android.view.View;

import com.indicar.indicar_community.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BindConversion {

    @BindingConversion
    public static int convertBooleanToVisibility(boolean visible){
        Log.d("", "convertBooleanToVisibility() called ... with visible: " + visible);
        return visible ? View.VISIBLE : View.GONE;
    }

}
