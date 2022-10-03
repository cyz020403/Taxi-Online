package com.neo.web.test;

import com.neo.mapper.PassengerMapper;
import com.neo.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PassengerController {

    @Autowired
    private PassengerMapper passengerMapper;

    @RequestMapping("/getOneByUsernamePassenger")
    public Passenger getOneByUsernamePassenger(@RequestBody Passenger passenger) {
        String userName = passenger.getUserName();
        return passengerMapper.getOneByUsername(userName);
    }

    @RequestMapping("/insertPassenger")
    public void insertPassenger(@RequestBody Passenger passenger) {
        passengerMapper.insert(passenger);
    }

    @RequestMapping("/updatePassenger")
    public void updatePassenger(@RequestBody Passenger passenger) {
        passengerMapper.update(passenger);
    }

    @RequestMapping("/deleteByUserNamePassenger")
    public void deleteByUserNamePassenger(@RequestBody Passenger passenger) {
        passengerMapper.deleteByUsername(passenger.getUserName());
    }
}