package com.neo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TaxiOrder  {

    private Integer id;
    private Integer workId;
    private Integer passengerId;
    private Double startLng;
    private Double startLat;
    private Double endLng;
    private Double endLat;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String remark;

    private double price;

    private boolean isSettled;

    private boolean isTransing;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    public Double getStartLng() {
        return startLng;
    }

    public void setStartLng(Double startLng) {
        this.startLng = startLng;
    }

    public Double getStartLat() {
        return startLat;
    }

    public void setStartLat(Double startLat) {
        this.startLat = startLat;
    }

    public Double getEndLng() {
        return endLng;
    }

    public void setEndLng(Double endLng) {
        this.endLng = endLng;
    }

    public Double getEndLat() {
        return endLat;
    }

    public void setEndLat(Double endLat) {
        this.endLat = endLat;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }

    public boolean isTransing() {
        return isTransing;
    }

    public void setTransing(boolean transing) {
        isTransing = transing;
    }

    @Override
    public String toString() {
        return "TaxiOrder{" +
                "id=" + id +
                ", workId=" + workId +
                ", passengerId=" + passengerId +
                ", startLng=" + startLng +
                ", startLat=" + startLat +
                ", endLng=" + endLng +
                ", endLat=" + endLat +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", remark='" + remark + '\'' +
                ", price=" + price +
                ", isSettled=" + isSettled +
                ", isTransing=" + isTransing +
                '}';
    }
}
