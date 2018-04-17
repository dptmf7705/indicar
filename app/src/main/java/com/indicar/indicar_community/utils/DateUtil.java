package com.indicar.indicar_community.utils;

import android.databinding.BindingConversion;
import android.util.Log;

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
    public static String convertStringToDate(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            Date result = dateFormat.parse(dateString);
            Log.d("convertStringToDate", "convertStringToDate() called.... result: " + dateFormat.format(result).toString());
            return result.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("convertStringToDate", "convertStringToDate() called.... error: " + e.getLocalizedMessage());
            return null;
        }
    }

}
