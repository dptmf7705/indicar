package com.indicar.indicar_community.vo;

/**
 * Created by yeseul on 2018-02-23.
 */

public class BoardVO {
    private int image;
    private String boardCategory;
    private String userName;
    private String boardText;
    private int like;
    private int comment;

    public BoardVO() {

    }

    public BoardVO(int image, String boardCategory, String userName, String boardText, int like, int comment) {
        this.image = image;
        this.boardCategory = boardCategory;
        this.userName = userName;
        this.boardText = boardText;
        this.like = like;
        this.comment = comment;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getBoardCategory() {

        return boardCategory;
    }

    public void setBoardCategory(String boardCategory) {
        this.boardCategory = boardCategory;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBoardText() {
        return boardText;
    }

    public void setBoardText(String boardText) {
        this.boardText = boardText;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "BoardVO{" +
                "boardCategory='" + boardCategory + '\'' +
                ", userName='" + userName + '\'' +
                ", boardText='" + boardText + '\'' +
                ", like=" + like +
                ", comment=" + comment +
                '}';
    }

}
