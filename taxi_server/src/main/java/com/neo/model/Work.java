package com.neo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Work {

    private Integer id;

    private Integer driverId;

    private Integer carId;

    // 用 int 接受数据库中的 int 类型，实际上是用于判断的 bool 类型
    private Integer isWorking;

    private Integer isTrans;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getIsWorking() {
        return isWorking;
    }

    public void setIsWorking(Integer isWorking) {
        this.isWorking = isWorking;
    }

    public Integer getIsTrans() {
        return isTrans;
    }

    public void setIsTrans(Integer isTrans) {
        this.isTrans = isTrans;
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

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", driver_id=" + driverId +
                ", car_id=" + carId +
                ", isWorking=" + isWorking +
                ", isTrans=" + isTrans +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
