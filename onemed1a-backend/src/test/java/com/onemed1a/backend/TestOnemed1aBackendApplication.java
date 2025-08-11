package com.onemed1a.backend;

import org.springframework.boot.SpringApplication;

public class TestOnemed1aBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(Onemed1aBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
