package com.neo;

import com.neo.mapper.CarMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisAnnotationApplicationTests {

	@Autowired
	private CarMapper carMapper;

//	@Test
//	public void test() {
//		System.out.println(carMapper.getAll());
//	}
}
