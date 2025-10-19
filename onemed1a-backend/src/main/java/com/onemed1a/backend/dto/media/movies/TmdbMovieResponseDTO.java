package com.onemed1a.backend.dto.media.movies;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents the paginated response from the TMDB movie API.
 *
 * Contains metadata such as page number and the list of movie results.
 */
@Getter
@Setter
public class TmdbMovieResponseDTO {
    private int page;
    private List<TmdbMovieResponse> results;
}

