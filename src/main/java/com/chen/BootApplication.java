package com.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

// 启动时排除druid数据源自动配置，由 mybatis plus 动态数据源配置
//@SpringBootApplication
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

}
