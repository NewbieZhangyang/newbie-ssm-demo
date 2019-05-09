package com.newbie.domain;

import java.io.Serializable;

public class UserStock implements Serializable {
    private String stockId;

    private String userId;

    private String stockName;

    private Integer countNum;

    private String remark;

    private static final long serialVersionUID = 1L;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId == null ? null : stockId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName == null ? null : stockName.trim();
    }

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "UserStock{" +
                "stockId='" + stockId + '\'' +
                ", userId='" + userId + '\'' +
                ", stockName='" + stockName + '\'' +
                ", countNum=" + countNum +
                ", remark='" + remark + '\'' +
                '}';
    }
}