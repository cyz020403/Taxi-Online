package com.neo.mapper;

import com.neo.model.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AdminMapper {

    @Select("SELECT * FROM admin")
    @Results({
            @Result(property = "userName", column = "user_name")
    })
    List<Admin> getAll();

    @Select("SELECT * FROM admin WHERE user_name = #{userName}")
    @Results({
            @Result(property = "userName", column = "user_name")
    })
    Admin getOneByUsername(String userName);

    @Insert("INSERT INTO admin(user_name,password,balance) VALUES(#{userName}, #{password}, #{balance})")
    void insert(Admin admin);

    @Update("UPDATE admin SET user_name=#{userName},password=#{password},balance=#{balance} WHERE id=#{id}")
    void update(Admin admin);

    @Delete("DELETE FROM admin WHERE user_name=#{userName}")
    void deleteByUsername(String userName);
}