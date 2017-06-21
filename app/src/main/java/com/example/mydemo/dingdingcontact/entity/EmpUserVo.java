package com.example.mydemo.dingdingcontact.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.mydemo.dingdingcontact.OrgContactAdapter;


public class EmpUserVo extends BaseUserVo implements MultiItemEntity, java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String sex;
    private String email;
    private String phone;
    private String sign;
    private String orgId;
    private String orgName;
    private String isSync;//同步标识：0：表示否; 1：表示是
    private String incomeDate;//入职时间
    private String telephone;//固定电话
    private String forbiddenAttribute;// 0显示添加到个人通讯录，1显示

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIsSync() {
        return isSync;
    }

    public void setIsSync(String isSync) {
        this.isSync = isSync;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getForbiddenAttribute() {
        return forbiddenAttribute;
    }

    public void setForbiddenAttribute(String forbiddenAttribute) {
        this.forbiddenAttribute = forbiddenAttribute;
    }

    @Override
    public int getItemType() {
        return OrgContactAdapter.EMP;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EmpUserVo) {
            EmpUserVo vo = (EmpUserVo) obj;
            return (uid.equals(vo.uid));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}
