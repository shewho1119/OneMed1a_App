package com.onemed1a.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Main entry point for the OneMed1A backend application.
 *
 * Bootstraps the Spring context and registers global beans.
 */
@SpringBootApplication
public class Onemed1aBackendApplication {

	// Start the Spring Boot application
	public static void main(String[] args) {
		SpringApplication.run(Onemed1aBackendApplication.class, args);
	}

	/**
     * Creates a singleton {@link RestTemplate} bean used for HTTP requests
     * to external APIs (e.g., Google Books, Spotify, TMDB).
     *
     * @return a configured RestTemplate instance
     */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
