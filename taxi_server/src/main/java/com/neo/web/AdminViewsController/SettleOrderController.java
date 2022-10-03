package com.neo.web.AdminViewsController;

import com.neo.mapper.AdminMapper;
import com.neo.mapper.DriverMapper;
import com.neo.mapper.SettleMapper;
import com.neo.mapper.TaxiOrderMapper;
import com.neo.mapper.mapperForQuery.SettleOrderMapper;
import com.neo.mapper.mapperForQuery.SettleRecordMapper;
import com.neo.model.Admin;
import com.neo.model.Driver;
import com.neo.model.Settle;
import com.neo.web.test.AdminController;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class SettleOrderController {

    @Autowired
    SettleOrderMapper settleOrderMapper;

    @Autowired
    TaxiOrderMapper taxiOrderMapper;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    DriverMapper driverMapper;

    @Autowired
    SettleMapper settleMapper;

    @RequestMapping("/getAllUnsettledOrderGroupByDriver")
    public List<Map<String, Object>> GetAllUnsettledOrderGroupByDriver() {
        return settleOrderMapper.getAllUnsettledOrderGroupByDriver();
    }

    @RequestMapping("/settleByDriverUserName")
    public void SettleByDriverUserName(@RequestBody Map<String, Object> MyData) {
        List<Map<String, Object>> orderIdAndPrice =
                taxiOrderMapper.getAllUnsettledOrderByDriverUserName((String)MyData.get("driverUserName"));
        Admin nowAdmin = adminMapper.getOneByUsername((String)MyData.get("adminUserName"));
        Driver nowDriver = driverMapper.getOneByUsername((String)MyData.get("driverUserName"));
        double sumPrice = 0.0;
        for(Map<String, Object> nowOrder : orderIdAndPrice) {
            sumPrice += ((BigDecimal)nowOrder.get("price")).doubleValue();
            taxiOrderMapper.updateToSettledByOrderId(((Long)nowOrder.get("id")).intValue());
        }

        nowAdmin.setBalance(nowAdmin.getBalance() - sumPrice);
        adminMapper.update(nowAdmin);

        nowDriver.setBalance(nowDriver.getBalance() + sumPrice);
        driverMapper.update(nowDriver);

        Settle settle = new Settle();
        settle.setDriverId(nowDriver.getId());
        settle.setAdminId(nowAdmin.getId());
        settle.setOrderCount(orderIdAndPrice.size());
        settle.setSumPrice(sumPrice);
        Date date = new Date();
        settle.setSettleTime(date);
        settleMapper.InsertToSettle(settle);
    }
}