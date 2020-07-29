package com.eureka.ImageEureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ImageEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageEurekaApplication.class, args);
	}

}
