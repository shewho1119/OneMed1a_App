package com.onemed1a.backend.dto.media.music;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an image object used in Spotify API responses.
 *
 * Typically used for album covers or artist profile images.
 */
@Getter @Setter
public class SpotifyImage {
    private String url;
    private int height;
    private int width;
}
