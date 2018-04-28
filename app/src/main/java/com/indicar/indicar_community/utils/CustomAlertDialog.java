package com.indicar.indicar_community.utils;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.DialogLayoutBinding;

/**
 * Created by yeseul on 2018-04-28.
 */

public class CustomAlertDialog extends Dialog {

    private DialogLayoutBinding binding;
    private Context context;

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> subTitle = new ObservableField<>();
    public final ObservableInt imageId = new ObservableInt(R.drawable.ic_action_alert);
    public final ObservableInt positiveImageId = new ObservableInt(R.drawable.btn_agree);
    public final ObservableInt negativeImageId = new ObservableInt(R.drawable.btn_cancel);

    private int width = 0;
    private int height = 0;

    private View.OnClickListener positiveButtonListener = null;
    private View.OnClickListener negativeButtonListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 외부 화면 흐리게
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_layout, null, false);
        binding.setDialog(this);

        setContentView(binding.getRoot());

        if(positiveButtonListener != null){
            binding.positiveButton.setOnClickListener(positiveButtonListener);
        }

        if(negativeButtonListener == null){
            negativeButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            };
        }
        binding.negativeButton.setOnClickListener(negativeButtonListener);

        if(width != 0 && height != 0){

            WindowManager.LayoutParams params = this.getWindow().getAttributes();
            params.width = width;
            params.height = height;

            this.getWindow().setAttributes(params);
        }
    }

    public CustomAlertDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public CustomAlertDialog setImageId(int imageId){
        this.imageId.set(imageId);
        return this;
    }

    public CustomAlertDialog setTitle(String title){
        this.title.set(title);
        return this;
    }

    public CustomAlertDialog setSubTitle(String subTitle){
        this.subTitle.set(subTitle);
        return this;
    }

    public CustomAlertDialog setPositiveButtonListener(View.OnClickListener onClickListener){
        this.positiveButtonListener = onClickListener;
        return this;
    }

    public CustomAlertDialog setPositiveButton(int buttonId, View.OnClickListener onClickListener){
        this.positiveButtonListener = onClickListener;
        this.positiveImageId.set(buttonId);
        return this;
    }

    public CustomAlertDialog setNegativeButtonListener(View.OnClickListener onClickListener){
        this.negativeButtonListener = onClickListener;
        return this;
    }

    public CustomAlertDialog setNegativeButton(int buttonId, View.OnClickListener onClickListener){
        this.negativeButtonListener = onClickListener;
        this.negativeImageId.set(buttonId);
        return this;
    }

    public CustomAlertDialog setSize(int width, int height){
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
