package com.example.mydemo.dingdingcontact.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 71541 on 2017/5/23.
 */

public class BaseUserVo implements Serializable {
    @SerializedName(value = "uid", alternate = {"id", "userId"})
    protected String uid;
    protected String account;//账号
    protected String userName;// 好友姓名
    protected String picture;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        if (userName == null) {
            userName = account;
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
