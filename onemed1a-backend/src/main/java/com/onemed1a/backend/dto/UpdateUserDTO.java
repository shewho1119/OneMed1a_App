package com.onemed1a.backend.dto;

import java.time.LocalDate;

import com.onemed1a.backend.model.User.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object used for updating an existing user's profile.
 *
 * Contains editable profile fields such as name, gender, and active status.
 */
@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class UpdateUserDTO {
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Boolean active;
}
