package com.neo.mapper;

import com.neo.enums.UserSexEnum;
import com.neo.model.Driver;
import com.neo.model.Passenger;
import com.neo.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PassengerMapper {

    @Select("SELECT * FROM passenger")
    @Results({
            @Result(property = "userName", column = "user_name")
    })
    List<Passenger> getAll();

    @Select("SELECT * FROM passenger WHERE user_name = #{userName}")
    @Results({
            @Result(property = "userName", column = "user_name")
    })
    Passenger getOneByUsername(String userName);

    @Insert("INSERT INTO passenger(user_name,password,balance,lng,lat) " +
            "VALUES(#{userName}, #{password}, #{balance}, #{lng}, #{lat})")
    void insert(Passenger passenger);

    @Update("UPDATE passenger SET user_name=#{userName},password=#{password},balance=#{balance},lng=#{lng},lat=#{lat} WHERE id =#{id}")
    void update(Passenger passenger);

    @Delete("DELETE FROM passenger WHERE user_name =#{userName}")
    void deleteByUsername(String userName);

    @Update("update passenger " +
            "set lng = #{lng}, lat = #{lat} " +
            "where user_name = #{userName}")
    public void modifyPosition(String lng, String lat, String userName);

    @Update("update passenger " +
            "set balance = balance - #{price} " +
            "where user_name = #{passengerUserName}")
    public void updateBalance(String passengerUserName, String price);
}