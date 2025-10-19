package com.onemed1a.backend.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a registered user in the system.
 *
 * Stores authentication details and personal profile information.
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /** Unique identifier for the user. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="first_name", nullable=false)
    private String firstName;

    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    /** Gender of the user (defaults to UNSPECIFIED). */
    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=16)
    @Builder.Default
    private Gender gender = Gender.UNSPECIFIED;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    /** Timestamp when the user was created. */
    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private OffsetDateTime createdAt;

    /** Whether the account is currently active. */
    @Column(nullable=false)
    @Builder.Default
    private boolean active = true;

    /** Enum representing available gender options. */
    public enum Gender {
        MALE, FEMALE, NON_BINARY, UNSPECIFIED
    }
}
