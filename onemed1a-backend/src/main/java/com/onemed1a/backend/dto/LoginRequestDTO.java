package com.onemed1a.backend.dto;

import lombok.Data;


/**
 * Data Transfer Object for user login requests.
 *
 * Contains the user's email and password for authentication.
 */
@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}