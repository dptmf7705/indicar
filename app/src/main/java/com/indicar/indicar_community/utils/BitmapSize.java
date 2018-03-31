package com.indicar.indicar_community.utils;

import android.graphics.BitmapFactory;

/**
 * Created by yeseul on 2018-04-01.
 *
 * TODO
 * decodeByte 로 고치기
 */

public class BitmapSize {

    /** Get Bitmap's Width **/
    public static int getBitmapOfWidth(String fileName){
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;


//            BitmapFactory.decodeByteArray();
            BitmapFactory.decodeFile(fileName, options);


            return options.outWidth;
        } catch(Exception e) {
            return 0;
        }
    }

    /** Get Bitmap's height **/
    public static int getBitmapOfHeight(String fileName){
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(fileName, options);
            return options.outHeight;
        } catch(Exception e) {
            return 0;
        }
    }
}
