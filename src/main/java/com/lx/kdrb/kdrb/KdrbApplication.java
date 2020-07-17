package com.lx.kdrb.kdrb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan("com.lx.kdrb.kdrb.mapper")
public class KdrbApplication {

	public static void main(String[] args) {
		SpringApplication.run(KdrbApplication.class, args);
	}

}
