package com.neo.web.DriverViewsController;

import com.neo.mapper.DriverMapper;
import com.neo.mapper.PassengerMapper;
import com.neo.mapper.TaxiOrderMapper;
import com.neo.mapper.WorkMapper;
import com.neo.model.Driver;
import com.neo.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DriverReserveOrder {

    @Autowired
    TaxiOrderMapper taxiOrderMapper;

    @Autowired
    PassengerMapper passengerMapper;

    @Autowired
    DriverMapper driverMapper;

    @Autowired
    WorkMapper workMapper;

    @RequestMapping("/carGetReserveOrder")
    public List<Map<String, Object>> carGetReserveOrder(@RequestBody Map<String, Object> myData) {
        // let MyData = {"userName":_this.userName};
        String nowDriverUserName = myData.get("userName").toString();
        Driver nowDriver = driverMapper.getOneByUsername(nowDriverUserName);
        return taxiOrderMapper.driverSelectReserveOrder(nowDriver.getId());
    }

    @RequestMapping("/startReserveOrder")
    public void startReserveOrder(@RequestBody Map<String, Object> myData) {
        // map: wordId
        int workId = Integer.parseInt(myData.get("wordId").toString());
        workMapper.startReserveOrder(workId);
    }
}

