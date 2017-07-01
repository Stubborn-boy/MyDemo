package com.example.mydemo.dingdingcontact.entity;

import android.text.TextUtils;
import java.io.Serializable;

/**
 * 这里是获取常用联系人信息用的
 */
public class FriendMesVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String friendId;// [好友id]
    private String friendAccount;//好友账户
    private String friendName;// [好友名称]
    private String picture;// [好友头像]
    private String avatarLocalPath;
    private String sex;// [好友性别]
    private String area;// [好友地区]
    private String sign;// [好友个性签名]
    private String group;// Cons.FRIEND_DEFAULT_GROUPNAME Cons.EXHIBITOR_GROUPNAME
    private String user;
    private String subscription; // 好友关系 to 等待验证 * from通过验证 * both 已添加 * none 加为好友
    private String isFriend;// 是否为当前登录用户的好友
    private int friendType;// 1.普通朋友 2.公众账号
    private String state;// 消息推送状态 0 未推送 1 已推送
    private String orgName;//所属部门名字
    private String orgId;//所属门户id
    private String email;
    private String phone;//电话号码
    private String incomeDate;//入职时间
    private String telephone;//固定电话
    private int chatMsgRemind; //0(default不开启免打扰) ,1(开启免打扰)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public FriendMesVo(String friendId, String friendAccount, String picture, String group, String friendName) {
        super();
        this.friendId = friendId;
        this.friendAccount = friendAccount;
        this.group = group;
        this.picture = picture;
        this.friendName = friendName;
    }
    public FriendMesVo(String friendId, String friendAccount, String picture, String group, String friendName,String orgName) {
        super();
        this.friendId = friendId;
        this.friendAccount = friendAccount;
        this.group = group;
        this.picture = picture;
        this.friendName = friendName;
        this.orgName = orgName;
    }
    public FriendMesVo() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public FriendMesVo(String account) {
        this.friendAccount = account;
    }

    public String getFriendId() {
        return friendId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAvatarLocalPath() {
        return avatarLocalPath;
    }

    public void setAvatarLocalPath(String avatarLocalPath) {
        this.avatarLocalPath = avatarLocalPath;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendAccount() {
        return friendAccount;
    }

    public void setFriendAccount(String friendAccount) {
        this.friendAccount = friendAccount;
    }

    public String getFriendName() {
        if (TextUtils.isEmpty(friendName)) {
            friendName = getFriendAccount();
        }
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * 1.普通朋友 2.公众账号
     *
     * @return
     */
    public int getFriendType() {
        return friendType;
    }

    /**
     * 1.普通朋友 2.公众账号
     *
     * @param friendType
     */
    public void setFriendType(int friendType) {
        this.friendType = friendType;
    }

    private boolean isAlphabet(char c) {
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
    }

    public int getChatMsgRemind() {
        return chatMsgRemind;
    }

    public void setChatMsgRemind(int chatMsgRemind) {
        this.chatMsgRemind = chatMsgRemind;
    }
}
