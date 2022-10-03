package com.neo.web.DriverViewsController;

import com.neo.mapper.DriverMapper;
import com.neo.mapper.WorkMapper;
import com.neo.mapper.mapperForQuery.WorkRecordMapper;
import com.neo.model.Driver;
import com.neo.model.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

@RestController
public class WorkRecordController {

    @Autowired
    WorkMapper workMapper;

    @Autowired
    DriverMapper driverMapper;

    @Autowired
    WorkRecordMapper workRecordMapper;

    @RequestMapping("/getFinishedWorkByDriverUserName")
    public List<Map<String, Object>> getFinishedWorkByDriverUserName(@RequestBody Driver driver) {
        Driver nowDriver = driverMapper.getOneByUsername(driver.getUserName());
        List<Map<String, Object>> myData = workRecordMapper.getFinishedWorkByDriverId(nowDriver.getId());
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        ft.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//        System.out.println(myData);
        for(Map<String, Object> i :myData) {
            if(i.get("startTime") != null) i.put("startTime", ft.format(i.get("startTime")));
            if(i.get("endTime") != null) i.put("endTime", ft.format(i.get("endTime")));
        }
        return myData;
    }
}
