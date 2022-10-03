package com.neo.mapper.mapperForQuery;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SettleOrderMapper {

    @Select("select driver.user_name driver_user_name,sum(price) sum_price,count(*) as count " +
            "from driver,taxi_order,work " +
            "where taxi_order.work_id=work.id " +
            "and work.driver_id=driver.id " +
            "and taxi_order.is_settled = false " +
            "group by driver.user_name;")
    @Results({
            @Result(property = "driverUserName", column = "driver_user_name"),
            @Result(property = "sumPrice", column = "sum_price")
    })
    public List<Map<String, Object>> getAllUnsettledOrderGroupByDriver();
}
