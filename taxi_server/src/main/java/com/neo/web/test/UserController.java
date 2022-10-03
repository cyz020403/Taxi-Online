package com.neo.web.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo.model.User;
import com.neo.mapper.UserMapper;

@RestController
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("/getUsers")//get
	public List<User> getUsers() {
		List<User> users=userMapper.getAll();
		return users;
	}
	
    @RequestMapping("/getUser")
    public User getUser(Long id) {
    	User user=userMapper.getOne(id);
        return user;
    }
    
    @RequestMapping("/addUser")
    public void save(User user) {
    	userMapper.insert(user);
    }
    
    @RequestMapping(value="/updateUser") // 形如‘/update/yaya’等等以update为根路径的都能访问到这里
    public void update(User user) { // 这种接收参数的方式，路径上不写参数也能执行
    	userMapper.update(user);
    }
    
    @RequestMapping(value="/deleteUser/{id}")
    public void delete(@PathVariable("id") Long id) {
    	userMapper.delete(id);
    }

    @RequestMapping("/test")
    public String test() {
        return "gaga";
    }
}