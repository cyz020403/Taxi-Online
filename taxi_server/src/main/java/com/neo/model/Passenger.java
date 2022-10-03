package com.neo.model;

import com.neo.enums.UserSexEnum;

import java.io.Serializable;

public class Passenger{

    private Integer id;

    private String userName;

    private String password;

    private Double balance;

    private Double lat;

    private Double lng;


    public Passenger(String userName, String password, Double balance, Double lng, Double lat) {
        this.id = 0;
        this.userName = userName;
        this.password = password;
        this.balance = balance;
        this.lat = lat;
        this.lng = lng;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "userName " + this.userName + ", pasword " + this.password + ", balance " + balance;
    }
}
