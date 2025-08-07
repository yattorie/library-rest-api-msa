package com.orlovandrei.usersearchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserSearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSearchServiceApplication.class, args);
	}

}
