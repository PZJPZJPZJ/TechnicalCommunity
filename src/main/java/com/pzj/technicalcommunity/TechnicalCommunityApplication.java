package com.pzj.technicalcommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pzj.technicalcommunity.mapper")
public class TechnicalCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechnicalCommunityApplication.class, args);
	}

}
