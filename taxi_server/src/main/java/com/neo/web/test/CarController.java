package com.neo.web.test;

import com.neo.mapper.CarMapper;
import com.neo.model.Car;
import com.neo.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController {

    @Autowired
    private CarMapper carMapper;

    @RequestMapping("/getAllCars")
    public List<Car> getAllCars () {
        return carMapper.getAll();
    }

    @RequestMapping("/getCarById")
    public Car getCarById(@RequestBody Car car) {
        return carMapper.getById(car.getId());
    }

    @RequestMapping("/getAllCarsByManager")
    public List<Car> getAllCarsByManager (@RequestBody Driver driver) {
        return carMapper.getAllByManager(driver.getUserName());
    }

    @RequestMapping("/deleteCarByPlate")
    public void deleteCarByPlate(@RequestBody Car car) {
        carMapper.DeleteByPlate(car.getPlate());
    }

    @RequestMapping("/insertCar")
    public int insertCar(@RequestBody Car car) {
        if(car.getPlate().equals("") || car.getLat() == null || car.getPlate() == null) {
            return 0;
        }
        Car test = carMapper.getCarByPlate(car.getPlate());
        if(test != null) {
            return 0;
        }
        else {
            carMapper.insertCar(car);
            return 1;
        }
    }
}