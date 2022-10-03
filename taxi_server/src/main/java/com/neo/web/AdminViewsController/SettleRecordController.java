package com.neo.web.AdminViewsController;

import com.neo.mapper.mapperForQuery.SettleRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@RestController
public class SettleRecordController {

    @Autowired
    SettleRecordMapper settleRecordMapper;

    @RequestMapping("/getAllSettleRecord")
    public List<Map<String, Object>> GetAllSettleRecord() {
        List<Map<String, Object>> myData = settleRecordMapper.getAllSettleRecord();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        ft.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        for(Map<String, Object> i :myData) {
            i.put("settleTime", ft.format(i.get("settleTime")));
        }
        return myData;
    }
}
