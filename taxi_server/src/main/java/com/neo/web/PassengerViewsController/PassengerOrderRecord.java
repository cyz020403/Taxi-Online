package com.neo.web.PassengerViewsController;

import com.neo.mapper.DriverMapper;
import com.neo.mapper.PassengerMapper;
import com.neo.mapper.TaxiOrderMapper;
import com.neo.mapper.mapperForQuery.OrderRecordMapper;
import com.neo.mapper.mapperForQuery.PassengerOrderRecordMapper;
import com.neo.model.Driver;
import com.neo.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
public class PassengerOrderRecord {

    @Autowired
    PassengerOrderRecordMapper passengerOrderRecordMapper;

    @Autowired
    PassengerMapper passengerMapper;

    @Autowired
    TaxiOrderMapper taxiOrderMapper;

    @RequestMapping("/getOrderRecordByPassengerUserName")
    public List<Map<String, Object>> getOrderRecordByPassengerUserName(@RequestBody Passenger passenger) {
        Passenger nowPassenger = passengerMapper.getOneByUsername(passenger.getUserName());
        List<Map<String, Object>> myData =
                passengerOrderRecordMapper.getOrderRecordByPassengerId(nowPassenger.getId());
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        for(Map<String, Object> i :myData) {
            if(i.get("startTime") != null)i.put("startTime", ft.format(i.get("startTime")));
            if(i.get("endTime") != null) i.put("endTime", ft.format(i.get("endTime")));
        }
        return myData;
    }

    @RequestMapping("/addRemark")
    public void addRemark(@RequestBody Map<String, Object> myData) {
        // map: orderId, remark
        String orderId = myData.get("orderId").toString();
        String remark = myData.get("remark").toString();

        taxiOrderMapper.addRemark(orderId, remark);
    }
}