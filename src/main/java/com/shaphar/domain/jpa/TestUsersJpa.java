package com.shaphar.domain.jpa;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  注解@Proxy(lazy = false)用以解决 jpa的 org.hibernate.lazyinitializationexception could not initialize proxy - no session
 */
@Entity
@Table(name="test_users")
@Proxy(lazy = false)
public class TestUsersJpa implements Serializable{
    @Id
    @Column(name = "user_id")
    private BigDecimal userId;
    @Column(name = "party_id")
    private BigDecimal partyId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "login_name")
    private String loginName;
    @Column(name = "login_nickname")
    private String loginNickname;
    @Column(name = "login_md5_password")
    private String loginMd5Password;
    @Column(name = "login_initpwd")
    private String loginInitpwd;
    @Column(name = "admin")
    private String admin;
    @Column(name = "is_group")
    private String isGroup;
    @Column(name = "user_status")
    private String userStatus;
    @Column(name = "expire_date")
    private Date expireDate;
    @Column(name = "login_times")
    private Integer loginTimes;
    @Column(name = "last_password_change_date")
    private Date lastPasswordChangeDate;
    @Column(name = "password_log")
    private String passwordLog;
    @Column(name = "user_type")
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