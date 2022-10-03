package com.neo.web.test;

import com.neo.mapper.WorkMapper;
import com.neo.model.Driver;
import com.neo.model.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkController {

    @Autowired
    WorkMapper workMapper;

    @RequestMapping("/getAllWork")
    public List<Work> getAllWork() {
        return workMapper.getAll();
    }

    @RequestMapping("/getWorkByDriverAndCar")
    public List<Work> getWorkByDriverAndCar(@RequestBody Work work) {
        return workMapper.getByDriverAndCar(work.getDriverId(), work.getCarId());
    }

    @RequestMapping("/getWorkByDriver")
    public List<Work> getWorkByDriver(@RequestBody Work work) {
    return workMapper.getByDriver(work.getDriverId());
    }
}