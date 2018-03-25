package com.indicar.indicar_community.utils;

import android.view.View;
import android.widget.ImageButton;

/**
 * Created by yeseul on 2018-03-25.
 */

public class WriteFilterRadio implements View.OnClickListener{
    private ImageButton btnLife;
    private ImageButton btnMarket;
    private ImageButton btnDIY;
    private int checked = -1;

    public WriteFilterRadio(ImageButton btnLife, ImageButton btnMarket, ImageButton btnDIY) {
        this.btnLife = btnLife;
        this.btnMarket = btnMarket;
        this.btnDIY = btnDIY;

        btnLife.setOnClickListener(this);
        btnLife.setTag(0);
        btnMarket.setOnClickListener(this);
        btnMarket.setTag(1);
        btnDIY.setOnClickListener(this);
        btnDIY.setTag(2);
    }

    @Override
    public void onClick(View view) {
        int tag = (int)view.getTag();
        if(tag != checked) {
            switch (tag) {
                case 0: //life
                    btnLife.setSelected(true);
                    btnMarket.setSelected(false);
                    btnDIY.setSelected(false);
                    setChecked(0);
                    break;
                case 1:
                    btnLife.setSelected(false);
                    btnMarket.setSelected(true);
                    btnDIY.setSelected(false);
                    setChecked(1);
                    break;
                case 2:
                    btnLife.setSelected(false);
                    btnMarket.setSelected(false);
                    btnDIY.setSelected(true);
                    setChecked(2);
                    break;
            }
        }
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
