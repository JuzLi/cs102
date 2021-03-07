package com.ahoy.ahoy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class AhoyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AhoyApplication.class, args);
		System.out.println("Your Spring Version is : " + SpringVersion.getVersion());
	}

}
