package com.finatiol.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinatiolUsuariosMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinatiolUsuariosMsApplication.class, args);
	}

}
