package com.indicar.indicar_community.view.binding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.indicar.indicar_community.utils.ImageUtil;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BindAdapter {


    @BindingAdapter(value = {"imageUrl", "error"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, Drawable errorDrawable){
        String TAG = BindAdapter.class.getSimpleName();
        Log.d(TAG, "loadImage() with url: " + url);
        ImageUtil.loadImage(imageView, url, errorDrawable);
    }

    @BindingAdapter(value = {"imageResource", "error"}, requireAll = false)
    public static void loadImage(ImageView imageView, int id, Drawable errorDrawable){
        String TAG = BindAdapter.class.getSimpleName();
        Log.d(TAG, "loadImage() with resource id: " + id);
        ImageUtil.loadImage(imageView, id, errorDrawable);
    }

    @BindingAdapter(value = {"imageUrl", "error"}, requireAll = false)
    public static void loadCircleImage(ImageButton imageView, String url, Drawable errorDrawable){
        String TAG = BindAdapter.class.getSimpleName();
        Log.d(TAG, "loadCircleImage() with url: "+ url);
        ImageUtil.loadCircleImage(imageView, url, errorDrawable);
    }

}
