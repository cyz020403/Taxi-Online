package com.neo.web.PassengerViewsController;

import com.neo.mapper.*;
import com.neo.model.Car;
import com.neo.model.Driver;
import com.neo.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PassengerReserveOrder {

    @Autowired
    TaxiOrderMapper taxiOrderMapper;

    @Autowired
    PassengerMapper passengerMapper;

    @Autowired
    DriverMapper driverMapper;

    @Autowired
    CarMapper carMapper;

    @Autowired
    WorkMapper workMapper;

    @RequestMapping("/addReserveOrder")
    public void addReserveOrder(@RequestBody Map<String, Object> myData) {
        /*
           let myData = {
              "passengerUserName": _this.userName,
              "startLng": _this.startLng,
              "startLat": _this.startLat,
              "endLng": _this.endLng,
              "endLat": _this.endLat,
              "startTime": _this.addOrderForm.startTime,
              "price": _this.price,
            }
        */

        String nowPassengerUserName = myData.get("passengerUserName").toString();
        Passenger nowPassenger = passengerMapper.getOneByUsername(nowPassengerUserName);

        Double startLng = Double.parseDouble(myData.get("startLng").toString());
        Double startLat = Double.parseDouble(myData.get("startLat").toString());
        Double endLng = Double.parseDouble(myData.get("endLng").toString());
        Double endLat = Double.parseDouble(myData.get("endLat").toString());
        String startTime = myData.get("startTime").toString();
        Double price = Double.parseDouble(myData.get("price").toString());

        List<Car> allCar = carMapper.getAll();
        Car minCar = allCar.get(0);
        double minDis = Math.pow(startLng - minCar.getLng(), 2) +
                Math.pow(startLat - minCar.getLat() , 2);
        for(Car i : allCar) {
            double nowDis = Math.pow(startLng - i.getLng(), 2) +
                    Math.pow(startLat - i.getLat(), 2);

            if(nowDis < minDis) {
                minDis = nowDis;
                minCar = i;
            }
        }

        String driverUserName = minCar.getManager();
        Driver nowDriver = driverMapper.getOneByUsername(driverUserName);
        int workId = Integer.parseInt(workMapper.selectMaxId().get("maxId").toString()) + 1;

        workMapper.insertReserveWork(workId, nowDriver.getId(),minCar.getId(),startTime);

        taxiOrderMapper.insertReserveOrder(workId,nowPassenger.getId(),startLng,startLat,endLng,endLat,
                startTime,price);

        // 需要匹配一个 car, 然后找到司机，然后创建work
        // 什么时候创建work？ 这时候就要创建，order中有work，司机开始工作的时候，需要给他work，好更新work为working，
        // 可不可以在创建的时候就，isWorkingisTransing, 可以，即使有很多预约单，也可以通过workId区分
        // 结束工作的时候，会把所有的直接设置为 false, false, 为了不影响，这里必须为false, false
        // 创建work需要知道workId，不能知道，所以创建的时候需要指定
        //然后创建taxi_order, 需要标记是预约单
    }

    @RequestMapping("/passengerGetReserveOrder")
    public List<Map<String, Object>> passengerGetReserveOrder(@RequestBody Map<String, Object> myData) {
        // let MyData = {"userName":_this.userName};
        // 只要正在运输的单子， 表示没有完成
        String nowPassengerUserName = myData.get("userName").toString();
        Passenger nowPassenger = passengerMapper.getOneByUsername(nowPassengerUserName);
        return taxiOrderMapper.passengerSelectReserveOrder(nowPassenger.getId());
    }

    @RequestMapping("/setPassengerPosition")
    public void setPassengerPosition(@RequestBody Map<String, Object> myData) {
        // map: userName lng lat
        String userName = myData.get("userName").toString();
        String lng = myData.get("lng").toString();
        String lat = myData.get("lat").toString();
        passengerMapper.modifyPosition(lng, lat, userName);
    }
}
