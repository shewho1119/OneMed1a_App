package com.onemed1a.backend.externalapiresponses.movies;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TmdbResponseDTO {
    private int page;
    private List<TmdbResponse> results;
}

