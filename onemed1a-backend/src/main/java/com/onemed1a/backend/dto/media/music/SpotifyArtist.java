package com.onemed1a.backend.dto.media.music;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an artist object returned by the Spotify Web API.
 *
 * Contains basic information about an artist such as name and Spotify links.
 */
@Getter @Setter
public class SpotifyArtist {
    private String id;
    private String name;
    private String href;
    private String uri;
    // external_urls can be added if you need it
}
