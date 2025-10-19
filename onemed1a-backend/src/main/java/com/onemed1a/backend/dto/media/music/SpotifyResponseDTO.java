package com.onemed1a.backend.dto.media.music;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.*;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Represents the top-level Spotify API response for album searches.
 *
 * Wraps a paginated {@link SpotifyAlbumsPage} object containing album data.
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyResponseDTO {
    private SpotifyAlbumsPage albums;
}
