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

@RestController
public class LoginController {

    @Autowired
    private PassengerMapper passengerMapper;

    @Autowired
    private DriverMapper driverMapper;

    @Autowired
    private AdminMapper adminMapper;

    @RequestMapping("/passengerLogin")
    public boolean passengerLogin(@RequestBody Passenger passenger) {
//        String  rightPassword = passengerMapper.getOneByUsername(passenger.getUserName()).getPassword();
        Passenger rightPassenger = passengerMapper.getOneByUsername(passenger.getUserName());
        if(rightPassenger == null) {
            return false;
        }
        String rightPassword = rightPassenger.getPassword();

        // 验证密码
        return rightPassword.equals(passenger.getPassword());
    }

    @RequestMapping("/driverLogin")
    public boolean driverLogin(@RequestBody Driver driver) {
//        String  rightPassword = driverMapper.getOneByUsername(driver.getUserName()).getPassword();
        Driver rightDriver = driverMapper.getOneByUsername(driver.getUserName());
        if(rightDriver == null) {
            return false;
        }
        String rightPassword = rightDriver.getPassword();

        // 验证密码
        return rightPassword.equals(driver.getPassword());
    }

    @RequestMapping("/adminLogin")
    public boolean driverLogin(@RequestBody Admin admin)  {
        Admin rightDriver = adminMapper.getOneByUsername(admin.getUserName());
        if(rightDriver == null) {
            return false;
        }
        String rightPassword = rightDriver.getPassword();

        // 验证密码
        return rightPassword.equals(admin.getPassword());
    }
}
