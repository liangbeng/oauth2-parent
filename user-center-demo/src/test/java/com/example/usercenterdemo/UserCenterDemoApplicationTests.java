package com.example.usercenterdemo;

import com.example.demo.UserCenterDemoApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserCenterDemoApplication.class)
class UserCenterDemoApplicationTests {

	@Test
	void contextLoads() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String password = bCryptPasswordEncoder.encode("123456");
		String secret = bCryptPasswordEncoder.encode("demo-secret");
		System.out.println(password);
		System.out.println(secret);

	}

}
