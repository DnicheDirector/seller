package com.seller.usertransactionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserTransactionApplication.class, args);
	}

}
