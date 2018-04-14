package com.indicar.indicar_community.model.vo;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by yeseul on 2018-03-24.
 */

public class BbsVO{
    private String _id;
    private String bbs_id;
    private String ntt_sj;
    private String ntt_cn;
    private String use_secret_at;
    private String use_notice_at;
    private String ntcr_nm;
    private String password;
    private String atch_file_id;
    private String frst_time;
    private String last_updt_time;
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String field5;
    private String use_at;
    private int rdcnt;
    private int like;
    private String ntt_id;
    private int __v;
    private String popPoint;
    private Bitmap mainImage;
    private int CntCOMMENT;
    private ArrayList<FileDetailVO> fileList = new ArrayList<>();

    public BbsVO() {

    }

    public int getCntCOMMENT() {
        return CntCOMMENT;
    }

    public void setCntCOMMENT(int cntCOMMENT) {
        CntCOMMENT = cntCOMMENT;
    }

    public Bitmap getMainImage() {
        return mainImage;
    }

    public void setMainImage(Bitmap mainImage) {
        this.mainImage = mainImage;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBbs_id() {
        return bbs_id;
    }

    public void setBbs_id(String bbs_id) {
        this.bbs_id = bbs_id;
    }

    public String getNtt_sj() {
        return ntt_sj;
    }

    public void setNtt_sj(String ntt_sj) {
        this.ntt_sj = ntt_sj;
    }

    public String getNtt_cn() {
        return ntt_cn;
    }

    public void setNtt_cn(String ntt_cn) {
        this.ntt_cn = ntt_cn;
    }

    public String getUse_secret_at() {
        return use_secret_at;
    }

    public void setUse_secret_at(String use_secret_at) {
        this.use_secret_at = use_secret_at;
    }

    public String getUse_notice_at() {
        return use_notice_at;
    }

    public void setUse_notice_at(String use_notice_at) {
        this.use_notice_at = use_notice_at;
    }

    public String getNtcr_nm() {
        return ntcr_nm;
    }

    public void setNtcr_nm(String ntcr_nm) {
        this.ntcr_nm = ntcr_nm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAtch_file_id() {
        return atch_file_id;
    }

    public void setAtch_file_id(String atch_file_id) {
        this.atch_file_id = atch_file_id;
    }

    public String getFrst_time() {
        return frst_time;
    }

    public void setFrst_time(String frst_time) {
        this.frst_time = frst_time;
    }

    public String getLast_updt_time() {
        return last_updt_time;
    }

    public void setLast_updt_time(String last_updt_time) {
        this.last_updt_time = last_updt_time;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5;
    }

    public String getUse_at() {
        return use_at;
    }

    public void setUse_at(String use_at) {
        this.use_at = use_at;
    }

    public int getRdcnt() {
        return rdcnt;
    }

    public void setRdcnt(int rdcnt) {
        this.rdcnt = rdcnt;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getNtt_id() {
        return ntt_id;
    }

    public void setNtt_id(String ntt_id) {
        this.ntt_id = ntt_id;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public ArrayList<FileDetailVO> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<FileDetailVO> fileList) {
        this.fileList = fileList;
    }

    public String getPopPoint() {
        return popPoint;
    }
    public void setPopPoint(String popPoint) {
        this.popPoint = popPoint;
    }

    @Override
    public String toString() {
        return "BbsVO{" +
                "_id='" + _id + '\'' +
                ", bbs_id='" + bbs_id + '\'' +
                ", ntt_sj='" + ntt_sj + '\'' +
                ", ntt_cn='" + ntt_cn + '\'' +
                ", use_secret_at='" + use_secret_at + '\'' +
                ", use_notice_at='" + use_notice_at + '\'' +
                ", ntcr_nm='" + ntcr_nm + '\'' +
                ", password='" + password + '\'' +
                ", atch_file_id='" + atch_file_id + '\'' +
                ", frst_time='" + frst_time + '\'' +
                ", last_updt_time='" + last_updt_time + '\'' +
                ", field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                ", field4='" + field4 + '\'' +
                ", field5='" + field5 + '\'' +
                ", use_at='" + use_at + '\'' +
                ", rdcnt=" + rdcnt +
                ", like=" + like +
                ", ntt_id='" + ntt_id + '\'' +
                ", __v=" + __v +
                ", popPoint='" + popPoint + '\'' +
                ", fileList=" + fileList +
                '}';
    }

}
