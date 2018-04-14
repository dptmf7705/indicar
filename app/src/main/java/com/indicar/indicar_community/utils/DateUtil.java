package com.indicar.indicar_community.utils;

import android.databinding.BindingConversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yeseul on 2018-04-13.
 */

public class DateUtil {

    /**
     * json 파싱할 때 사용
     * */
    public static Date convertStringToDate(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
