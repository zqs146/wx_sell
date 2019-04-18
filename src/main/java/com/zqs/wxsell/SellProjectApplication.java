package com.zqs.wxsell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SellProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellProjectApplication.class, args);
	}

}
