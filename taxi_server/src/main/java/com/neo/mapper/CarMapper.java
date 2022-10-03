package com.neo.mapper;

import com.neo.model.Car;
import com.neo.model.Driver;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface CarMapper {

    @Select("SELECT * FROM car")
    List<Car> getAll();

    @Select("SELECT * FROM car WHERE manager = #{manager}")
    List<Car> getAllByManager(String manager);

    @Select("select * from car where id=#{id}")
    Car getById(int id);

    @Select("SELECT * FROM car WHERE plate = #{plate}")
    Car getCarByPlate(String plate);

    @Delete("Delete from car where plate = #{plate}")
    void DeleteByPlate(String plate);



    @Insert("insert into car(plate,manager,lng,lat)" +
            "values(#{plate},#{manager},#{lng},#{lat})")
    void insertCar(Car car);

    @Update("update car " +
            "set lng = #{lng}, lat = #{lat} " +
            "where plate = #{carPlate}")
    public void modifyCarPosition(String lng, String lat, String carPlate);

    @Select("select lng, lat " +
            "from car " +
            "where plate = #{carPlate}")
    public Map<String, Object> searchCarPosition(String carPlate);
}
