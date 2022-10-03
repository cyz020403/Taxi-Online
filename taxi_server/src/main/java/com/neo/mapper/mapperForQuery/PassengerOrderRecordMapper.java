package com.neo.mapper.mapperForQuery;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PassengerOrderRecordMapper {

    @Select("select plate,driver.user_name,taxi_order.start_time,taxi_order.end_time, " +
            "       start_lng,start_lat,end_lng,end_lat,price, taxi_order.id " +
            "from taxi_order,work,car, driver,passenger " +
            "where taxi_order.work_id=work.id " +
            "and work.car_id=car.id " +
            "and work.driver_id = driver.id " +
            "and taxi_order.passenger_id = passenger.id " +
            "and passenger.id = #{id}")
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "startLng", column = "start_lng"),
            @Result(property = "startLat", column = "start_lat"),
            @Result(property = "endLng", column = "end_lng"),
            @Result(property = "endLat", column = "end_lat")
    })
    public List<Map<String, Object>> getOrderRecordByPassengerId(int id);
}
