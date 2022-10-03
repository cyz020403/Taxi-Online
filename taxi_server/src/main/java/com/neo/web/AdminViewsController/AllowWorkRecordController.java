package com.neo.web.AdminViewsController;

import com.neo.mapper.AdminMapper;
import com.neo.mapper.DriverMapper;
import com.neo.mapper.AllowWorkRecordMapper;
import com.neo.model.Admin;
import com.neo.model.AllowWork;
import com.neo.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AllowWorkRecordController {

    @Autowired
    AllowWorkRecordMapper allowWorkRecordMapper;

    @Autowired
    DriverMapper driverMapper;

    @Autowired
    AdminMapper adminMapper;

    @RequestMapping("/getAllAllowWorkRecord")
    public List<Map<String, Object>> GetAllAllowWorkRecord() {
        return allowWorkRecordMapper.getAllAllowWorkRecord();
    }

    @RequestMapping("/givePermissionByDriverUserName")
    public void GivePermissionByDriverUserName(@RequestBody Map<String, String> myData) {
        Driver driver = driverMapper.getOneByUsername(myData.get("driverUserName"));
        Admin admin = adminMapper.getOneByUsername(myData.get("adminUserName"));
        driver.setAllowedWork("1");
        driverMapper.update(driver);

        AllowWork allowWork = new AllowWork();
        allowWork.setDriverId(driver.getId());
        allowWork.setAdminId(admin.getId());
        allowWorkRecordMapper.insertToAllowWork(allowWork);
    }
}
