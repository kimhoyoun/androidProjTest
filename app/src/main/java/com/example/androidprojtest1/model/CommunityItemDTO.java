package com.example.androidprojtest1.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.material.dialog.InsetDialogOnTouchListener;

import java.io.Serializable;

public class CommunityItemDTO implements Serializable {
    private int no;
    private String userID;
    private String title;
    private String mainText;
    private int likeNum;
    private int cName;
    private String date;

    public CommunityItemDTO(){
        this(0,null,null,null,0,0,null);
    }

    public CommunityItemDTO(int no, String userID, String title, String mainText, int likeNum, int cName, String date){
        this.no = no;
        this.userID = userID;
        this.title = title;
        this.mainText = mainText;
        this.likeNum = likeNum;
        this.cName = cName;
        this.date = date;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getcName() {
        return cName;
    }

    public void setcName(int cName) {
        this.cName = cName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CommunityItemDTO{" +
                "no=" + no +
                ", userID='" + userID + '\'' +
                ", title='" + title + '\'' +
                ", mainText='" + mainText + '\'' +
                ", likeNum=" + likeNum +
                ", cName=" + cName +
                ", date='" + date + '\'' +
                '}';
    }

}
