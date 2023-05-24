package com.ezen.mannamatna;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class MannamatnaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MannamatnaApplication.class, args);
	}

}
