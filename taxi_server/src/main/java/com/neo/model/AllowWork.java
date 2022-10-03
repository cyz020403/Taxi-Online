package com.neo.model;

public class AllowWork {

    private Integer id;

    private Integer driverId;

    private Integer adminId;

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

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "allowWork{" +
                "id=" + id +
                ", driverId=" + driverId +
                ", adminId=" + adminId +
                '}';
    }
}
