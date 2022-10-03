package com.neo.web;

import com.neo.mapper.AdminMapper;
import com.neo.mapper.DriverMapper;
import com.neo.mapper.PassengerMapper;
import com.neo.model.Admin;
import com.neo.model.Driver;
import com.neo.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;

@RestController
public class RegisterController {

    @Autowired
    private PassengerMapper passengerMapper;

    @Autowired
    private DriverMapper driverMapper;

    @Autowired
    private AdminMapper adminMapper;


    @RequestMapping("/register")
    public String PassengerRegister(@RequestBody AcceptData acceptData) {
        System.out.println(acceptData);
        String userType = acceptData.userType;
        if(userType.equals("1")) {
            Passenger passenger = passengerMapper.getOneByUsername(acceptData.userName);
            if(!acceptData.password.equals(acceptData.verifyPassword)) {
                // 两次密码不同
                return "0";
            }
            else if(passenger != null) {
                // 已经有此用户存在
                return "1";
            }
            // 山东大学软件园校区： longitude(经度), latitude(纬度):  117.139813,36.666842
            Passenger insertPassenger = new Passenger(acceptData.userName, acceptData.password, 100.00,
                    117.137305,36.668430);
            passengerMapper.insert(insertPassenger);
            return "2";
        }
        else if(userType.equals("2")) {
            Driver driver = driverMapper.getOneByUsername(acceptData.userName);
            if(!acceptData.password.equals(acceptData.verifyPassword)) {
                // 两次密码不同
                return "0";
            }
            else if(driver != null) {
                // 已经有此用户存在
                return "1";
            }
            Driver insertDriver = new Driver(acceptData.userName, acceptData.password, 100.00, "0");
            driverMapper.insert(insertDriver);
            return "2";
        }
        else {
            Admin admin = adminMapper.getOneByUsername(acceptData.userName);
            if(!acceptData.password.equals(acceptData.verifyPassword)) {
                // 两次密码不同
                return "0";
            }
            else if(admin != null) {
                // 已经有此用户存在
                return "1";
            }
            Admin insertAdmin = new Admin(acceptData.userName, acceptData.password, 100.00);
            adminMapper.insert(insertAdmin);
            return "2";
        }
    }
}

class AcceptData {
    String userName;
    String password;
    String verifyPassword;
    String userType;

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

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "userName " + this.userName + ", password " + this.password + ", verifyPassword " + this.verifyPassword
                + ", userType " + this.userType;
    }
}

