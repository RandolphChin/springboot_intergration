package com.shaphar.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PfUsers implements Serializable {
    private BigDecimal userId;

    private BigDecimal partyId;

    private String userName;

    private String loginName;

    private String loginNickname;

    private String loginMd5Password;

    private String loginInitpwd;

    private String admin;

    private String isGroup;

    private String userStatus;

    private Date expireDate;

    private String linkMan;

    private String tele;

    private String email;

    private String userType;

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public BigDecimal getPartyId() {
        return partyId;
    }

    public void setPartyId(BigDecimal partyId) {
        this.partyId = partyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginNickname() {
        return loginNickname;
    }

    public void setLoginNickname(String loginNickname) {
        this.loginNickname = loginNickname == null ? null : loginNickname.trim();
    }

    public String getLoginMd5Password() {
        return loginMd5Password;
    }

    public void setLoginMd5Password(String loginMd5Password) {
        this.loginMd5Password = loginMd5Password == null ? null : loginMd5Password.trim();
    }

    public String getLoginInitpwd() {
        return loginInitpwd;
    }

    public void setLoginInitpwd(String loginInitpwd) {
        this.loginInitpwd = loginInitpwd == null ? null : loginInitpwd.trim();
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin == null ? null : admin.trim();
    }

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup == null ? null : isGroup.trim();
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele == null ? null : tele.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }
}