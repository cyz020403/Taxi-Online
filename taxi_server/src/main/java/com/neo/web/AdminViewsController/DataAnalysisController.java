package com.neo.web.AdminViewsController;

import com.neo.mapper.mapperForQuery.DataAnalysisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DataAnalysisController {

    @Autowired
    DataAnalysisMapper dataAnalysisMapper;

    @RequestMapping("/dataAnalysis")
    public Map<String, Integer> dataAnalysis() {
        Map<String, Integer> returnData = new HashMap<>();
        int passengerCount = dataAnalysisMapper.getPassengerCount();
        int driverCount = dataAnalysisMapper.getDriverCount();
        int adminCount = dataAnalysisMapper.getAdminCount();
        returnData.put("passengerCount", passengerCount);
        returnData.put("driverCount", driverCount);
        returnData.put("adminCount", adminCount);
        return returnData;
    }

    @RequestMapping("/getOrderPrice")
    public Map<String, Integer> getOrderPrice() {
        Map<String, Integer> returnData = new HashMap<>();
        List<Integer> orderPrice = dataAnalysisMapper.getOrderPrice();
        int int1 = 0;
        int int2 = 0;
        int int3 = 0;
        int int4 = 0;
        int int5 = 0;

        for(Integer i : orderPrice) {
            if(i <= 10) int1 ++;
            else if(i >= 11 && i <= 15) int2 ++;
            else if(i >= 16 && i <= 20) int3 ++;
            else if(i >= 21 && i <= 25) int4 ++;
            else int5 ++;
        }
        returnData.put("int1", int1);
        returnData.put("int2", int2);
        returnData.put("int3", int3);
        returnData.put("int4", int4);
        returnData.put("int5", int5);

        return returnData;
    }

    @RequestMapping("/getOrderCountGroupByPassenger")
    public Map<String, Integer> getOrderCountGroupByPassenger() {
        Map<String, Integer> returnData = new HashMap<>();
        List<Integer> orderCount = dataAnalysisMapper.getOrderCountGroupByPassenger();
        int int1 = 0;
        int int2 = 0;
        int int3 = 0;
        int int4 = 0;
        int int5 = 0;

        for(Integer i : orderCount) {
            if(i <= 5) int1 ++;
            else if(i >= 6 && i <= 10) int2 ++;
            else if(i >= 11 && i <= 15) int3 ++;
            else if(i >= 16 && i <= 20) int4 ++;
            else int5 ++;
        }

        returnData.put("int1", int1);
        returnData.put("int2", int2);
        returnData.put("int3", int3);
        returnData.put("int4", int4);
        returnData.put("int5", int5);

        return returnData;
    }

    @RequestMapping("/getCarCountGroupByDriver")
    public Map<String, Integer> getCarCountGroupByDriver() {
        Map<String, Integer> returnData = new HashMap<>();
        List<Integer> carCount = dataAnalysisMapper.getCarCountGroupByDriver();
        int int1 = 0;
        int int2 = 0;
        int int3 = 0;
        int int4 = 0;
        int int5 = 0;

        for(Integer i : carCount) {
            if(i == 1) int1 ++;
            else if(i == 2) int2 ++;
            else if(i == 3) int3 ++;
            else if(i == 4) int4 ++;
            else int5 ++;
        }

        returnData.put("int1", int1);
        returnData.put("int2", int2);
        returnData.put("int3", int3);
        returnData.put("int4", int4);
        returnData.put("int5", int5);

        return returnData;
    }
}
