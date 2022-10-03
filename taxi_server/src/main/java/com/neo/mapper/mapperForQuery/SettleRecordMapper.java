package com.neo.mapper.mapperForQuery;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SettleRecordMapper {
        
    @Select("select settle.id as id,driver.user_name as driver_user_name,\n" +
            "            admin.user_name as admin_user_name,\n" +
            "            order_count,sum_price,settle_time\n" +
            "from settle,driver,admin\n" +
            "where settle.driver_id=driver.id\n" +
            "and settle.admin_id=admin.id\n")
    @Results({
            @Result(property = "driverUserName", column = "driver_user_name"),
            @Result(property = "adminUserName", column = "admin_user_name"),
            @Result(property = "orderCount", column = "order_count"),
            @Result(property = "sumPrice", column = "sum_price"),
            @Result(property = "settleTime", column = "settle_time")
    })
    public List<Map<String, Object>> getAllSettleRecord();
}
