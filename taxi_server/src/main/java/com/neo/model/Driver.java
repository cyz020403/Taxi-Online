package com.neo.model;

public class Driver {

    private Integer id;

    private String userName;

    private String password;

    private Double balance;

    private String allowedWork;

    public Driver(String userName, String password, Double balance, String allowedWork) {
        this.id = 0;
        this.userName = userName;
        this.password = password;
        this.balance = balance;
        this.allowedWork = allowedWork;
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

    public String getAllowedWork() {
        return allowedWork;
    }

    public void setAllowedWork(String allowedWork) {
        this.allowedWork = allowedWork;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "userName " + this.userName + ", password " + this.password + ", balance " + this.balance
                + ", allowedWork " + this.allowedWork;
    }
}
