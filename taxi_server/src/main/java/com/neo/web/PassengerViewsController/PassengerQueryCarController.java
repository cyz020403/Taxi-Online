package com.neo.web.PassengerViewsController;

import com.neo.mapper.CarMapper;
import com.neo.mapper.DriverMapper;
import com.neo.mapper.PassengerMapper;
import com.neo.mapper.WorkMapper;
import com.neo.mapper.mapperForQuery.PassengerQueryCarMapper;
import com.neo.model.Car;
import com.neo.model.Driver;
import com.neo.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@RestController
public class PassengerQueryCarController {

    @Autowired
    PassengerQueryCarMapper passengerQueryCarMapper;

    @Autowired
    PassengerMapper passengerMapper;

    @Autowired
    WorkMapper workMapper;

    @Autowired
    CarMapper carMapper;

    @Autowired
    DriverMapper driverMapper;

    // 重要的一个函数，完成2 3 4步骤
    @RequestMapping("/passengerQueryCar")
    public Map<String,Object> PassengerQueryCar(@RequestBody Map<String, Object> myData) {
        String passengerUserName = myData.get("userName").toString();
        double startLng = Double.parseDouble(myData.get("startLng").toString());
        double startLat = Double.parseDouble(myData.get("startLat").toString());
        double endLng = Double.parseDouble(myData.get("endLng").toString());
        double endLat = Double.parseDouble(myData.get("endLat").toString());
        double price = Double.parseDouble(myData.get("price").toString());

        List<Map<String, Object>> availableWork =
                passengerQueryCarMapper.queryAllWorkingWorkAndUntransing();

        if(availableWork.size() == 0) {
            return null;
        }

        /*
        * map里面的信息有
        * id (是work的Id
        * driverUserName
        * carPlate
        * lng (carLng)
        * lat (carLat)
        * */

        Map<String, Object> returnData = availableWork.get(0);
        double minDis = Math.pow(startLng - Double.parseDouble(returnData.get("lng").toString()), 2) +
                Math.pow(startLat - Double.parseDouble(returnData.get("lat").toString()), 2);
        for(Map<String, Object> i : availableWork) {
            double nowDis = Math.pow(startLng - Double.parseDouble(i.get("lng").toString()), 2) +
                    Math.pow(startLat - Double.parseDouble(i.get("lat").toString()), 2);
//            System.out.println(nowDis);
//            System.out.println(minDis);
            if(nowDis < minDis) {
                minDis = nowDis;
                returnData = i;
            }
        }

        // 乘客匹配到了找到了确定的车辆，需要向数据库中添加order记录，以及更改work为isTransing
        // 新建的order设置为了isTransng，所以不需要更改
        Passenger nowPassenger = passengerMapper.getOneByUsername(passengerUserName);
        int workId = Integer.parseInt(returnData.get("id").toString());
        String driverUserName = returnData.get("driverUserName").toString();
        String carPlate = returnData.get("carPlate").toString();
        Driver nowDriver = driverMapper.getOneByUsername(driverUserName);
        Car nowCar = carMapper.getCarByPlate(carPlate);

        Date nowDate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        ft.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        passengerQueryCarMapper.InsertNewOrder(workId,nowPassenger.getId(),startLng,startLat,endLng,endLat,
                ft.format(nowDate),price);
        workMapper.updateWorkingWorkToTransing(nowDriver.getId(), nowCar.getId());

        return returnData;
    }
}
