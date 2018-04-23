package com.indicar.indicar_community.view.binding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.ImageUtil;

import java.io.File;

/**
 * Created by yeseul on 2018-04-22.
 */

public class ImageViewBinding {

    @BindingAdapter(value = {"imageUrl", "boardType"}, requireAll = false)
    public static void loadImage(final ImageView imageView, String url, String boardType){

        int placeHolderId = R.drawable.button_share;

        if(boardType != null) {
            if (boardType.equals("daylife")) placeHolderId = R.drawable.button_daylife_deactive;
            else if (boardType.equals("market")) placeHolderId = R.drawable.button_market_deactive;
            else if (boardType.equals("diy")) placeHolderId = R.drawable.button_diy_deactive;
        }

        Glide.with(imageView.getContext())
                .load(url)
                .asBitmap()
                .into(imageView);

//        ImageUtil.loadImage(imageView, url, placeHolderId);
    }

    @BindingAdapter(value = {"imageUrl"}, requireAll = false)
    public static void loadImage(final ImageView imageView, String url){

        ImageUtil.loadImage(imageView, url, R.drawable.app_icon);
    }

    @BindingAdapter(value = {"imageUrl", "error"}, requireAll = false)
    public static void loadImage(ImageView imageView, Uri url, Drawable errorDrawable){

        ImageUtil.loadImage(imageView, url, errorDrawable);
    }

    @BindingAdapter(value = {"imageUrl", "error"}, requireAll = false)
    public static void loadImage(ImageView imageView, int id, Drawable errorDrawable){

        Log.d("", "loadImage() with id: " + id);
        ImageUtil.loadImage(imageView, id, errorDrawable);
    }

    @BindingAdapter(value = {"circleImageUrl", "error"}, requireAll = false)
    public static void loadCircleImage(ImageView imageView, String url, Drawable errorDrawable){

        ImageUtil.loadCircleImage(imageView, url, errorDrawable);
    }

    @BindingAdapter(value = {"circleImageUrl", "error"}, requireAll = false)
    public static void loadCircleImage(ImageView imageView, int id, Drawable errorDrawable){

        ImageUtil.loadCircleImage(imageView, id, errorDrawable);
    }

    @BindingAdapter(value = {"circleImageUrl", "error"}, requireAll = false)
    public static void loadCircleImage(ImageView imageView, Uri url, Drawable errorDrawable){

        ImageUtil.loadCircleImage(imageView, url, errorDrawable);
    }


}
