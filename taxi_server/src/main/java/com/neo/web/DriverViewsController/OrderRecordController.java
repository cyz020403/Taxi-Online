package com.neo.web.DriverViewsController;

import com.neo.mapper.DriverMapper;
import com.neo.mapper.mapperForQuery.OrderRecordMapper;
import com.neo.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
public class OrderRecordController {

    @Autowired
    OrderRecordMapper orderRecordMapper;

    @Autowired
    DriverMapper driverMapper;

    @RequestMapping("/getOrderRecordByDriverUserName")
    public List<Map<String, Object>> getOrderRecordByDriverUserName(@RequestBody Driver driver) {
        Driver nowDriver = driverMapper.getOneByUsername(driver.getUserName());
        List<Map<String, Object>> myData = orderRecordMapper.getOrderRecordByDriverId(nowDriver.getId());
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        for(Map<String, Object> i :myData) {
            i.put("startTime", ft.format(i.get("startTime")));
            if(i.get("endTime") != null) i.put("endTime", ft.format(i.get("endTime")));
        }
        return myData;
    }
}