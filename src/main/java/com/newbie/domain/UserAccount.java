package com.newbie.domain;

import java.io.Serializable;

public class UserAccount implements Serializable {
    private String accountId;

    private String userId;

    private Integer balance;

    private String remark;

    private static final long serialVersionUID = 1L;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "accountId='" + accountId + '\'' +
                ", userId='" + userId + '\'' +
                ", balance=" + balance +
                ", remark='" + remark + '\'' +
                '}';
    }
}