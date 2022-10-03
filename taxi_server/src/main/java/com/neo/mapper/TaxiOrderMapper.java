package com.neo.mapper;

import com.neo.model.TaxiOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface TaxiOrderMapper {

    @Select("select taxi_order.id, taxi_order.price " +
            "from taxi_order,driver,work " +
            "where taxi_order.work_id = work.id " +
            "and work.driver_id = driver.id " +
            "and taxi_order.is_settled = false " +
            "and driver.user_name = #{userName} ")
    public List<Map<String, Object>> getAllUnsettledOrderByDriverUserName(String userName);

    @Update("update taxi_order " +
            "set is_settled = true " +
            "where id = #{id}")
    public void updateToSettledByOrderId(Integer id);

    @Update("update taxi_order " +
            "set is_transing = false, end_time = #{endTime} " +
            "where  taxi_order.id in ( " +
            "    select id from " +
            "    (select taxi_order.id " +
            "     from taxi_order, work " +
            "     where taxi_order.work_id = work.id " +
            "       and taxi_order.is_transing = true " +
            "       and taxi_order.is_reserve = false " +
            "       and taxi_order.passenger_id = #{passengerId} " +
            "       and work.driver_id = #{driverId} " +
            "       and work.car_id = #{carId}) as t)")
    public void updateIsTransingAndEndTime(int driverId, int carId, int passengerId, String endTime);

    @Update("update taxi_order " +
            "set is_transing = false, end_time = #{endTime} " +
            "where  taxi_order.id in ( " +
            "    select id from " +
            "    (select taxi_order.id " +
            "     from taxi_order, work " +
            "     where taxi_order.work_id = work.id " +
            "       and taxi_order.is_transing = true " +
            "       and taxi_order.passenger_id = #{passengerId} " +
            "       and work.driver_id = #{driverId} " +
            "       and work.car_id = #{carId}) as t)")
    public void updateIsTransingAndEndTimeReserve(int driverId, int carId, int passengerId, String endTime);


    @Update("update taxi_order set remark = #{remark} where id = #{id}")
    public void addRemark(String id, String remark);

    @Insert("insert into taxi_order (work_id, passenger_id, start_lng, start_lat, " +
            "end_lng, end_lat, start_time, price, is_settled, is_transing, is_reserve) " +
            "value (#{workId},#{passengerId},#{startLng},#{startLat},#{endLng},#{endLat},#{startTime},#{price},false,true,true)")
    public void insertReserveOrder(int workId, int passengerId, Double startLng, Double startLat,
                                   Double endLng, Double endLat, String startTime, Double price);

    /*
    * orderId
    * workId
    * plate
    * userName
    * startLng.....
    * */
    @Select("select taxi_order.id order_id, taxi_order.work_id, car.plate,passenger.user_name,start_lng,start_lat,end_lng,end_lat,taxi_order.price,car.lng,car.lat " +
            "from taxi_order,work,car,passenger " +
            "where is_transing = true " +
            "and is_reserve = true " +
            "and taxi_order.work_id = work.id " +
            "and taxi_order.passenger_id = passenger.id " +
            "and work.car_id = car.id " +
            "and work.driver_id =#{driverId}")
    @Results({
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "workId", column = "work_id"),
            @Result(property = "plate", column = "plate"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "startLng", column = "start_lng"),
            @Result(property = "startLat", column = "start_lat"),
            @Result(property = "endLng", column = "end_lng"),
            @Result(property = "endLat", column = "end_lat")
    })
    public List<Map<String, Object>> driverSelectReserveOrder(int driverId);

    /*
    * id
    * plate
    * userName
    * startLng....
    * */
    @Select("select taxi_order.id order_id,car.plate,driver.user_name,start_lng, start_lat,end_lng,end_lat,taxi_order.price,car.lng,car.lat " +
            "from taxi_order,work,car,driver " +
            "where is_transing = true " +
            "and is_reserve = true " +
            "and taxi_order.work_id = work.id " +
            "and work.car_id = car.id " +
            "and work.driver_id = driver.id " +
            "and taxi_order.passenger_id =#{passengerId}")
    @Results({
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "plate", column = "plate"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "startLng", column = "start_lng"),
            @Result(property = "startLat", column = "start_lat"),
            @Result(property = "endLng", column = "end_lng"),
            @Result(property = "endLat", column = "end_lat")
    })
    public List<Map<String, Object>> passengerSelectReserveOrder(int passengerId);
}
