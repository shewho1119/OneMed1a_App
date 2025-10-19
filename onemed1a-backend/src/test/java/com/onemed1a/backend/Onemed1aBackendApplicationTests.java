package com.onemed1a.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Basic context load test for the Spring Boot application.
 *
 * Verifies that the application context starts successfully with the "test" profile.
 */
@SpringBootTest(properties = "spring.profiles.active=test")
@ActiveProfiles("test")
class Onemed1aBackendApplicationTests {

	/**
     * Ensures that the Spring application context loads without errors.
     */
	@Test
	void contextLoads() {
		// No assertions needed; failure to load context will fail the test
	}

}
