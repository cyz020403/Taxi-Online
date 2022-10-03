package com.neo.mapper.mapperForQuery;

import com.neo.model.Work;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface WorkRecordMapper {

    @Select("select car.plate, work.start_time, work.end_time " +
            "from car, work " +
            "where car.id = work.car_id " +
            "and work.is_working = 0 "+
            "and work.driver_id = #{driverId}")
    @Results({
            @Result(property = "carPlate", column = "plate"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time")

    })
    public List<Map<String, Object>> getFinishedWorkByDriverId(int driverId);
}
