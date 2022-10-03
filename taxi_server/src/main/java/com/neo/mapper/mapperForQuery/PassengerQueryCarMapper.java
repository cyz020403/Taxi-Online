package com.neo.mapper.mapperForQuery;

import com.neo.model.Work;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PassengerQueryCarMapper {

    @Select("select work.id, driver.user_name driver_user_name, car.plate car_plate, car.lng,car.lat " +
            "from work, driver, car " +
            "where is_working = true " +
            "and is_trans = false " +
            "and work.driver_id = driver.id " +
            "and work.car_id = car.id")
    @Results({
            @Result(property = "driverUserName", column = "driver_user_name"),
            @Result(property = "carPlate", column = "car_plate"),
    })
    public List<Map<String, Object>> queryAllWorkingWorkAndUntransing();

    @Insert("insert into taxi_order (work_id,passenger_id,start_lng,start_lat,end_lng,end_lat,start_time,price,is_settled,is_transing) " +
            "value (#{workId},#{passengerId},#{startLng},#{startLat},#{endLng},#{endLat},#{startTime},#{price},false,true);")
    public void InsertNewOrder(int workId, int passengerId, double startLng, double startLat, double endLng,
                               double endLat, String startTime, double price);
}
