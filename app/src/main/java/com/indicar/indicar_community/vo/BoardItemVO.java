package com.indicar.indicar_community.vo;

/**
 * Created by yeseul on 2018-02-25.
 */

public class BoardItemVO {
    private int img;
    private String text;

    public BoardItemVO(int img, String text) {
        this.img = img;
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
