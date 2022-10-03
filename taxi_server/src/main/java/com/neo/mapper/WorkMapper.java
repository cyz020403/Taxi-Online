package com.neo.mapper;

import com.neo.model.Work;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WorkMapper {

    @Select("select * from work")
    @Results({
            @Result(property = "driverId", column = "driver_id"),
            @Result(property = "carId", column = "car_id"),
            @Result(property = "isWorking", column = "is_working"),
            @Result(property = "isTrans", column = "is_trans"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time")

    })
    public List<Work> getAll();

    @Select("select * from work " +
            "where driver_id=#{driverId} and car_id = #{carId}")
    @Results({
            @Result(property = "driverId", column = "driver_id"),
            @Result(property = "carId", column = "car_id"),
            @Result(property = "isWorking", column = "is_working"),
            @Result(property = "isTrans", column = "is_trans"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time")

    })
    public List<Work> getByDriverAndCar(int driverId, int carId);

    @Select("select * from work " +
            "where driver_id=#{driverId}")
    @Results({
            @Result(property = "driverId", column = "driver_id"),
            @Result(property = "carId", column = "car_id"),
            @Result(property = "isWorking", column = "is_working"),
            @Result(property = "isTrans", column = "is_trans"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time")

    })
    public List<Work> getByDriver(int driverId);

    @Insert("insert into work (driver_id, car_id, is_working, is_trans,start_time) " +
            "value (#{driverId},#{carId},true,false,#{startTime})")
    public void insertNewWorkingUntransing(int driverId, int carId, String startTime);

    @Update("update work " +
            "set is_working = false " +
            "where driver_id =  #{driverId} " +
            "and car_id = #{carId}")
    public void updateToUnworkingByDriverAndCarId(int driverId, int carId);

    @Update("update work " +
            "set end_time = #{endTime} " +
            "where driver_id =  #{driverId} " +
            "and car_id = #{carId} " +
            "and is_working = true")
    public void updateEndtimeByDriverAndCarId(int driverId, int carId, String endTime);

    @Select("select passenger.user_name,taxi_order.start_lng,taxi_order.start_lat,taxi_order.end_lng,taxi_order.end_lat,taxi_order.price " +
            "from taxi_order, work, driver, car, passenger " +
            "where driver.id = work.driver_id " +
            "and car.id = work.car_id " +
            "and taxi_order.work_id = work.id " +
            "and taxi_order.passenger_id = passenger.id " +
            "and taxi_order.is_transing = true " +
            "and taxi_order.is_reserve = false " +
            "and driver.user_name = #{driverUserName} " +
            "and car.plate = #{carPlate} " +
            "and work.is_working = true")
    @Results({
            @Result(property = "passengerUserName", column = "user_name"),
            @Result(property = "startLng", column = "start_lng"),
            @Result(property = "startLat", column = "start_lat"),
            @Result(property = "endLng", column = "end_lng"),
            @Result(property = "endLat", column = "end_lat")
    })
    public List<Map<String, Object>> searchMyOrder(String driverUserName, String carPlate);

    @Update("update work " +
            "set is_trans = true " +
            "where driver_id = #{driverId} " +
            "and car_id = #{carId} " +
            "and is_working = true " +
            "and is_trans = false")
    public void updateWorkingWorkToTransing(int driverId, int carId);
    @Update("update work " +
            "set is_trans = false " +
            "where driver_id = #{driverId} " +
            "and car_id = #{carId} " +
            "and is_working = true " +
            "and is_trans = true")
    public void updateWorkingWorkToUnTransing(int driverId, int carId);

    @Select("select max(id) as max_id from work")
    @Results({
            @Result(property = "maxId", column = "max_id")
    })
    public Map<String, Object> selectMaxId();

    @Insert("insert work (id, driver_id, car_id, is_working, is_trans, start_time) " +
            "value (#{id},#{driverId},#{carId},false, false,#{startTime})")
    public void insertReserveWork(int id, int driverId, int carId, String startTime);

    @Update("update work " +
            "set is_working = true,is_trans = true " +
            "where id = #{id}")
    public void startReserveOrder(int id);
}
