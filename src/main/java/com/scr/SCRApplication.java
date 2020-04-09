package com.scr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages ={ "com.scr.model"})
public class SCRApplication {
	public static void main(String[] args) {
		SpringApplication.run(SCRApplication.class, args);
	}
}
