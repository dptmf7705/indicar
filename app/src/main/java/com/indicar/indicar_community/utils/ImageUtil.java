package com.indicar.indicar_community.utils;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ImageUtil {

    public static void loadImage(ImageView imageView, String url, Drawable errorDrawable){
        Glide.with(imageView.getContext())
                .load(url)
                .error(errorDrawable)
                .into(imageView);
    }

    public static void loadImage(ImageView imageView, int id, Drawable errorDrawable){
        Glide.with(imageView.getContext())
                .load(id)
                .error(errorDrawable)
                .into(imageView);
    }

    public static void loadImage(ImageView imageView, Uri url, Drawable errorDrawable){
        Glide.with(imageView.getContext())
                .load(url)
                .error(errorDrawable)
                .into(imageView);
    }

    public static void loadCircleImage(ImageView imageView, String url, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(errorDrawable)
                .bitmapTransform(new CropCircleTransformation(Glide.get(imageView.getContext()).getBitmapPool()))
                .into(imageView);
    }

    public static void loadCircleImage(ImageView imageView, int id, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(id)
                .error(errorDrawable)
                .bitmapTransform(new CropCircleTransformation(Glide.get(imageView.getContext()).getBitmapPool()))
                .into(imageView);
    }

    public static void loadCircleImage(ImageView imageView, Uri url, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(errorDrawable)
                .bitmapTransform(new CropCircleTransformation(Glide.get(imageView.getContext()).getBitmapPool()))
                .into(imageView);
    }
}
