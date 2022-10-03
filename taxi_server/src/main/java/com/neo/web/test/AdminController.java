package com.neo.web.test;

import com.neo.mapper.AdminMapper;
import com.neo.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AdminMapper adminMapper;

    @RequestMapping("/getAllAdmin")
    public List<Admin> getAllDriver() {
        return adminMapper.getAll();
    }

    @RequestMapping("/getOneByUsernameAdmin")
    public Admin getOneByUsernameDriver(@RequestBody Admin admin) {
        String userName = admin.getUserName();
        return adminMapper.getOneByUsername(userName);
    }

    @RequestMapping("/insertAdmin")
    public void insertDriver(@RequestBody Admin admin) {
        adminMapper.insert(admin);
    }

    @RequestMapping("/updateAdmin") // 需要指明id
    public void updateDriver(@RequestBody Admin admin) {
        adminMapper.update(admin);
    }

    @RequestMapping("/deleteByUserNameAdmin")
    public void deleteByUserNameAdmin(@RequestBody Admin admin) {
        adminMapper.deleteByUsername(admin.getUserName());
    }
}