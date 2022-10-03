package com.neo.model;

public class Admin {

    private Integer id;

    private String userName;

    private String password;

    private Double balance;

    public Admin(String userName, String password, Double balance) {
        this.id = 0;
        this.userName = userName;
        this.password = password;
        this.balance = balance;
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
}
