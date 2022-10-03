package com.neo.mapper;

import com.neo.model.AllowWork;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface AllowWorkRecordMapper {

    @Select("select driver.user_name as driver_user_name,driver.allowed_work as driver_allowed_work," +
            "    admin.user_name as admin_user_name " +
            "from driver left join allow_work on driver.id = allow_work.driver_id " +
            "    left join admin on allow_work.admin_id = admin.id")
    @Results({
            @Result(property = "driverUserName", column = "driver_user_name"),
            @Result(property = "adminUserName", column = "admin_user_name"),
            @Result(property = "driverAllowedWork", column = "driver_allowed_work")
    })
    public List<Map<String, Object>> getAllAllowWorkRecord();

    @Insert("insert into allow_work(driver_id, admin_id)" +
            "value(#{driverId},#{adminId})")
    public void insertToAllowWork(AllowWork allowWork);
}
