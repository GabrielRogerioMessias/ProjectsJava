package com.example.apiviacep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiViaCepApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiViaCepApplication.class, args);
    }

}
