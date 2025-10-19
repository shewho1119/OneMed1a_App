package com.onemed1a.backend;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Test configuration that provisions a PostgreSQL container for integration tests.
 *
 * The {@code @ServiceConnection} annotation wires this container into Spring Boot's
 * DataSource auto-configuration so tests can use a real Postgres instance.
 */
@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	/**
     * Starts a PostgreSQL Testcontainers instance for the test context.
     *
     * @return a managed {@link PostgreSQLContainer} instance
     */
	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
	}

}
