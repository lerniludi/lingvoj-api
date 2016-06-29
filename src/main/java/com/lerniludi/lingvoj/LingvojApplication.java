package com.lerniludi.lingvoj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:config/applicationContext.xml")
public class LingvojApplication {

	public static void main(String[] args) {
		SpringApplication.run(LingvojApplication.class, args);
	}
}