package com.neo.web.test;

import com.neo.mapper.DriverMapper;
import com.neo.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DriverController {

    @Autowired
    private DriverMapper driverMapper;

    @RequestMapping("/getAllDriver")
    public List<Driver> getAllDriver() {
        return driverMapper.getAll();
    }

    @RequestMapping("/getDriverById")
    public Driver getDriverById(@RequestBody Driver driver) {
        return driverMapper.getById(driver.getId());
    }

    @RequestMapping("/getOneByUsernameDriver")
    public Driver getOneByUsernameDriver(@RequestBody Driver driver) {
        String userName = driver.getUserName();
        System.out.println(driverMapper.getOneByUsername(userName));
        return driverMapper.getOneByUsername(userName);
    }

    @RequestMapping("/insertDriver")
    public void insertDriver(@RequestBody Driver driver) {
        driverMapper.insert(driver);
    }

    @RequestMapping("/updateDriver") // 需要指名id
    public void updateDriver(@RequestBody Driver driver) {
        driverMapper.update(driver);
    }

    @RequestMapping("/deleteByUserNameDriver")
    public void deleteByUserNameDriver(@RequestBody Driver driver) {
        driverMapper.deleteByUsername(driver.getUserName());
    }
}