package com.onemed1a.backend.user;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import com.onemed1a.backend.dto.CreateUserDTO;
import com.onemed1a.backend.dto.UserDTO;
import com.onemed1a.backend.model.User;
import com.onemed1a.backend.repository.UserRepository;
import com.onemed1a.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Unit tests for {@link UserService}.
 *
 * Verifies that user creation encodes passwords, checks email uniqueness,
 * and persists the entity before mapping to {@link UserDTO}.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;


    /**
     * Ensures {@link UserService#create(CreateUserDTO)}:
     * - Rejects duplicate emails (via existsByEmail check)
     * - Encodes the provided password
     * - Saves the user and returns a populated {@link UserDTO}
     */
    @Test
    void create_savesUser() {
        UUID userId = UUID.randomUUID();

        // Arrange
        String plainPassword = "Test123!";
        String hashedPassword = "$2a$10$hashedPasswordExample"; 

        CreateUserDTO dto = new CreateUserDTO(
                "Jane", 
                "Smith", 
                "jane@example.com",
                User.Gender.FEMALE,
                LocalDate.of(1990, 1, 1),
                plainPassword);

        when(repo.existsByEmail("jane@example.com")).thenReturn(false);
        when(passwordEncoder.encode(plainPassword)).thenReturn(hashedPassword);

        User saved = User.builder()
                .id(userId)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .dateOfBirth(dto.getDateOfBirth())
                .password(hashedPassword)
                .build();

        when(repo.save(any(User.class))).thenReturn(saved);

        // Act
        UserDTO result = service.create(dto);

        // Assert
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getEmail()).isEqualTo("jane@example.com");
        verify(repo).save(any(User.class));
    }
}
