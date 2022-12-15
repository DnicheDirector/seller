package com.seller.usertransactionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableCaching
@EnableReactiveFeignClients
public class UserTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserTransactionApplication.class, args);
	}

}
