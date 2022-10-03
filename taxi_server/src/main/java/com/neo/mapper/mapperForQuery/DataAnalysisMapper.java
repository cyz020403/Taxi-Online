package com.neo.mapper.mapperForQuery;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DataAnalysisMapper {

    @Select("select count(*) as admin_count\n" +
            "from admin")
    @Results({
            @Result(property = "adminCount", column = "admin_count")
    })
    public int getAdminCount();

    @Select("select count(*) as passenger_count\n" +
            "from passenger")
    @Results({
            @Result(property = "passengerCount", column = "passenger_count")
    })
    public int getPassengerCount();

    @Select("select count(*) as driver_count\n" +
            "from driver")
    @Results({
            @Result(property = "driverCount", column = "driver_count")
    })
    public int getDriverCount();

    @Select("select price from taxi_order")
    public List<Integer> getOrderPrice();

    @Select("select count(passenger_id)\n" +
            "from taxi_order right join passenger p on p.id = taxi_order.passenger_id\n" +
            "group by p.id")
    public List<Integer> getOrderCountGroupByPassenger();

    @Select("select count(*)\n" +
            "from car\n" +
            "group by manager\n")
    public List<Integer> getCarCountGroupByDriver();
}
