package com.neo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Settle {

    private Integer id;

    private Integer adminId;

    private Integer driverId;

    private Integer orderCount;

    private double sumPrice;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    @Override
    public String toString() {
        return "Settle{" +
                "id=" + id +
                ", adminId=" + adminId +
                ", driverId=" + driverId +
                ", orderCount=" + orderCount +
                ", sumPrice=" + sumPrice +
                ", settleTime=" + settleTime +
                '}';
    }
}
