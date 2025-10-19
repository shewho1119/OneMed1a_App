package com.onemed1a.backend;

import org.springframework.boot.SpringApplication;

/**
 * Test entry point that launches the main application with Testcontainers support.
 *
 * Useful for local/manual runs where you want the app to start against a
 * containerized Postgres configured by {@link TestcontainersConfiguration}.
 */
public class TestOnemed1aBackendApplication {

	/**
     * Boots the application using {@link Onemed1aBackendApplication} plus the
     * {@link TestcontainersConfiguration} for database connectivity.
     *
     * @param args command line args
     */
	public static void main(String[] args) {
		SpringApplication.from(Onemed1aBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
