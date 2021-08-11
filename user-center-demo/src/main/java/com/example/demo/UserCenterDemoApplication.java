package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@MapperScan("com.example.demo.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class UserCenterDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCenterDemoApplication.class, args);
	}

}
