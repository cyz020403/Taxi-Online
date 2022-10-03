package com.neo.mapper;

import com.neo.model.Settle;
import org.apache.ibatis.annotations.Insert;

public interface SettleMapper {

    @Insert("insert into settle (admin_id,driver_id,order_count,sum_price,settle_time) " +
            "value(#{adminId},#{driverId},#{orderCount},#{sumPrice},#{settleTime})")
    public void InsertToSettle(Settle settle);
}
