package com.cloud.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class Server2Application {

	public static void main(String[] args) {
		SpringApplication.run(Server2Application.class, args);
	}
	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
}
