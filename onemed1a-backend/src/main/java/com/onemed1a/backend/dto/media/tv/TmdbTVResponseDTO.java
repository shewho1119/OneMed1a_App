package com.onemed1a.backend.dto.media.tv;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents the paginated response returned by the TMDB API for TV shows.
 *
 * Contains the current page number and list of show results.
 */
@Getter
@Setter
public class TmdbTVResponseDTO {
    private int page;
    private List<TmdbTVResponse> results;
}

