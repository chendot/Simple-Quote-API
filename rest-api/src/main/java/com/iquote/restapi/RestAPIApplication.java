package com.iquote.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestAPIApplication.class, args);
	}

}
