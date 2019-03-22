package com.shaphar.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TestUsers implements Serializable{
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

    private Integer loginTimes;

    private Date lastPasswordChangeDate;

    private String passwordLog;

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
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginNickname() {
        return loginNickname;
    }

    public void setLoginNickname(String loginNickname) {
        this.loginNickname = loginNickname;
    }

    public String getLoginMd5Password() {
        return loginMd5Password;
    }

    public void setLoginMd5Password(String loginMd5Password) {
        this.loginMd5Password = loginMd5Password;
    }

    public String getLoginInitpwd() {
        return loginInitpwd;
    }

    public void setLoginInitpwd(String loginInitpwd) {
        this.loginInitpwd = loginInitpwd;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }


    public Date getLastPasswordChangeDate() {
        return lastPasswordChangeDate;
    }

    public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
        this.lastPasswordChangeDate = lastPasswordChangeDate;
    }

    public String getPasswordLog() {
        return passwordLog;
    }

    public void setPasswordLog(String passwordLog) {
        this.passwordLog = passwordLog;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "TestUsers{" +
                "userId=" + userId +
                ", partyId=" + partyId +
                ", userName='" + userName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", loginNickname='" + loginNickname + '\'' +
                ", loginMd5Password='" + loginMd5Password + '\'' +
                ", loginInitpwd='" + loginInitpwd + '\'' +
                ", admin='" + admin + '\'' +
                ", isGroup='" + isGroup + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", expireDate=" + expireDate +
                ", loginTimes=" + loginTimes +
                ", lastPasswordChangeDate=" + lastPasswordChangeDate +
                ", passwordLog='" + passwordLog + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}