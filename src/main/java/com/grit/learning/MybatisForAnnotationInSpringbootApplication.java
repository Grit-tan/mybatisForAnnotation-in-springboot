package com.grit.learning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.grit.learning.mapper")
public class MybatisForAnnotationInSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisForAnnotationInSpringbootApplication.class, args);
	}
}
