package com.neo.mapper;

import com.neo.model.Driver;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface DriverMapper {

    @Select("SELECT * FROM driver")
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "allowedWork", column = "allowed_work")
    })
    List<Driver> getAll();

    @Select("select * from driver where id=#{id}")
    Driver getById(int id);

    @Select("SELECT * FROM driver WHERE user_name = #{userName}")
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "allowedWork", column = "allowed_work")
    })
    Driver getOneByUsername(String userName);

    @Insert("INSERT INTO driver(user_name,password,balance,allowed_work) VALUES(#{userName}, #{password}, #{balance}, #{allowedWork})")
    void insert(Driver driver);

    @Update("UPDATE driver SET user_name=#{userName},password=#{password},balance=#{balance},allowed_work=#{allowedWork} " +
            "WHERE id=#{id}")
    void update(Driver driver);

    @Delete("DELETE FROM driver WHERE user_name=#{userName}")
    void deleteByUsername(String userName);

    @Select("select allowed_work from driver where user_name = #{driverUserName}")
    @Results({
            @Result(property = "allowedWork", column = "allowed_work")
    })
    public Map<String, Object> getAllowedCondition(String driverUserName);
}
