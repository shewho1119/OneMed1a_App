package com.onemed1a.backend.user;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import com.onemed1a.backend.model.User;
import com.onemed1a.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.onemed1a.backend.model.User.Gender;

/**
 * Unit tests for {@link UserRepository} verifying persistence and constraints.
 *
 * Runs with an in-memory test database using Spring Data JPA.
 */
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    /**
     * Verifies that a user can be created, persisted, and retrieved
     * both by ID and by email.
     */
    @Test
    void createAndFindById_andFindByEmail() {
        User toSave = User.builder()
                .firstName("Alice")
                .lastName("Ng")
                .email("alice@example.com")
                .password("password123")
                .gender(Gender.UNSPECIFIED)
                .dateOfBirth(LocalDate.of(2001, 7, 15))
                .active(true)
                .build();

        // Save the user
        User saved = repo.save(toSave);
        assertThat(saved.getId()).isNotNull();

        // Retrieve by ID
        Optional<User> byId = repo.findById(saved.getId());
        assertThat(byId).isPresent();
        assertThat(byId.get().getEmail()).isEqualTo("alice@example.com");

        // Retrieve by email
        Optional<User> byEmail = repo.findByEmail("alice@example.com");
        assertThat(byEmail).isPresent();
        assertThat(byEmail.get().getFirstName()).isEqualTo("Alice");
    }

    /**
     * Verifies that the email field is unique and that attempting
     * to insert a duplicate email triggers a database constraint exception.
     */
    @Test
    void emailIsUnique() {
        
        // Save the first user
        User u1 = User.builder()
                .firstName("A").lastName("B")
                .email("unique@example.com")
                .password("pw1")
                .gender(Gender.UNSPECIFIED)
                .active(true)
                .build();
        repo.saveAndFlush(u1);

        // Attempt to save another user with the same email
        User u2 = User.builder()
                .firstName("C").lastName("D")
                .email("unique@example.com") // duplicate
                .password("pw2")
                .gender(Gender.UNSPECIFIED)
                .active(true)
                .build();

        try {
            repo.saveAndFlush(u2);
            assertThat(true).as("Expected unique constraint to trigger").isFalse();
        } catch (Exception e) {
            assertThat(e.getMessage()).containsIgnoringCase("constraint");
        }
    }
}
