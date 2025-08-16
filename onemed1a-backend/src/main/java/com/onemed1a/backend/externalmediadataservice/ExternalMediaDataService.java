package com.onemed1a.backend.externalmediadataservice;

import com.onemed1a.backend.externalapiresponses.movies.TmdbResponseDTO;
import com.onemed1a.backend.mediadata.MediaData;
import com.onemed1a.backend.externalapiresponses.movies.TmdbResponse;
import com.onemed1a.backend.mediadata.MediaDataRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
    This component is used to load media types into the media database when the application
    starts up.
 */
@Component
public class ExternalMediaDataService {

    private final RestTemplate restTemplate;

    private final MediaDataRepository mediaDataRepository;

    // The bottom two api urls are taken from TMDB (The movie database) API.
    private final String MOVIE_API_URL = "https://api.themoviedb.org/3/discover/movie";
    private final String TV_API_URL = "https://api.themoviedb.org/3/discover/tv";
    private final String TMDB_BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMWI2M2NiM2U5NzgwZjU1ZjlkY2JmNThlZThjMTIwYyIsIm5iZiI6MTc1NTIyNjQ5MS41MTYsInN1YiI6IjY4OWVhMTdiYjZlZGVmMTEzMDhkYzEwZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PUKDRBPML6mXAmoNrlaoj1Skg_PGuaGqP-hHqqqYb-M";


    public ExternalMediaDataService(
            MediaDataRepository mediaDataRepository
    ) {
        this.restTemplate = new RestTemplate();
        this.mediaDataRepository = mediaDataRepository;
    }

    public ResponseEntity<List<MediaData>> getMovieMediaItems() {

        // Headers
        HttpEntity<Void> request = new HttpEntity<>(configureTMDBHeader());

        ResponseEntity<TmdbResponseDTO> response =
                restTemplate.exchange(URI.create(MOVIE_API_URL), HttpMethod.GET, request, TmdbResponseDTO.class);

        TmdbResponseDTO body = response.getBody();
        List<MediaData> mediaDataList = new ArrayList<>();

        for (TmdbResponse tmdbResponse : body
                .getResults()) {
            MediaData newMediaDataItem = MediaData.builder()
                    .externalMediaId(tmdbResponse.getId())
                    .type(MediaData.MediaType.MOVIE)
                    .title(tmdbResponse.getName())
                    .releaseDate(tmdbResponse.getReleaseDate())
                    .genres(tmdbResponse.getGenreIds())
                    .description(tmdbResponse.getOverview())
                    .posterUrl(tmdbResponse.getPosterPath())
                    .backdropUrl(tmdbResponse.getBackdropPath())
                    .build();

            mediaDataList.add(newMediaDataItem);
//            mediaDataRepository.save(newMediaDataItem);
        }

        return ResponseEntity.ok(mediaDataList);
    }



    public HttpHeaders configureTMDBHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(TMDB_BEARER_TOKEN);
        return headers;
    }


}