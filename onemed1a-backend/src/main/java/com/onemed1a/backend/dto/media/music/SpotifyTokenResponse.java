package com.onemed1a.backend.dto.media.music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents the response returned from Spotify's OAuth token API.
 *
 * Contains the access token and its expiration information.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyTokenResponse {
    @JsonProperty("access_token") private String accessToken;
    @JsonProperty("token_type")   private String tokenType;
    @JsonProperty("expires_in")   private int expiresIn;
}

