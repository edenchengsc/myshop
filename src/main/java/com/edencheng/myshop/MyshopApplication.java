package com.edencheng.myshop;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan("com.edencheng.myshop.db.mappers")
@ComponentScan(basePackages = {"com.edencheng"})
public class MyshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyshopApplication.class, args);
	}

}
