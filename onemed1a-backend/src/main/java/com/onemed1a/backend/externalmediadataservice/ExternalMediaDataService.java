package com.onemed1a.backend.externalmediadataservice;

import com.onemed1a.backend.externalapiresponses.books.GoogleBooksResponse;
import com.onemed1a.backend.externalapiresponses.books.GoogleBooksResponseDTO;
import com.onemed1a.backend.externalapiresponses.books.VolumeInfo;
import com.onemed1a.backend.externalapiresponses.movies.TmdbMovieResponse;
import com.onemed1a.backend.externalapiresponses.movies.TmdbMovieResponseDTO;
import com.onemed1a.backend.externalapiresponses.tv.TmdbTVResponseDTO;
import com.onemed1a.backend.media.MediaData;
import com.onemed1a.backend.externalapiresponses.tv.TmdbTVResponse;
import com.onemed1a.backend.media.MediaDataRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


/*
    This component is used to load media types into the media database when the application
    starts up.
 */
@Service
public class ExternalMediaDataService {

    private final RestTemplate restTemplate;

    private final MediaDataRepository mediaDataRepository;

    // The bottom two api urls are taken from TMDB (The movie database) API.
    private final String MOVIE_API_URL = "https://api.themoviedb.org/3/discover/movie";
    private final String TV_API_URL = "https://api.themoviedb.org/3/discover/tv";
    private final String TMDB_BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMWI2M2NiM2U5NzgwZjU1ZjlkY2JmNThlZThjMTIwYyIsIm5iZiI6MTc1NTIyNjQ5MS41MTYsInN1YiI6IjY4OWVhMTdiYjZlZGVmMTEzMDhkYzEwZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PUKDRBPML6mXAmoNrlaoj1Skg_PGuaGqP-hHqqqYb-M";

//    private final String SPOTIFY_API_URL;
    private final String SPOTIFY_BEARER_TOKEN = "BQCSrd3wVikTypHKNS1IzvR374mCzS4t6yDzOOHb8Yg4J-sAcdnnWZpBD44wJIurJGTLL5q_c3VQAg_0epTYDwYXk38wb9K16dasfLPDpkwPA_jePs027luVwY619ryxGOim0wQyZ5c";

    private final String GOOGLE_BOOKS_API = "AIzaSyDqOWAcQRRW9NCQCC1nqqLuq55yF__bgcM";
    private final String GOOGLE_BOOKS_URI = "https://www.googleapis.com/books/v1/volumes";

    public ExternalMediaDataService(
            MediaDataRepository mediaDataRepository
    ) {
        this.restTemplate = new RestTemplate();
        this.mediaDataRepository = mediaDataRepository;
    }

    public ResponseEntity<List<MediaData>> getMovieMediaItems() {

        HttpEntity<Void> request = new HttpEntity<>(configureTMDBHeader());

        ResponseEntity<TmdbMovieResponseDTO> response =
                restTemplate.exchange(URI.create(MOVIE_API_URL), HttpMethod.GET, request, TmdbMovieResponseDTO.class);

        TmdbMovieResponseDTO body = response.getBody();
        List<MediaData> mediaDataList = new ArrayList<>();

        for (TmdbMovieResponse tmdbMovieResponse : body.getResults()) {

            MediaData newMediaDataItem = MediaData.builder()
                    .externalMediaId(String.valueOf(tmdbMovieResponse.getId()))
                    .type(MediaData.MediaType.MOVIE)
                    .title(tmdbMovieResponse.getTitle())
                    .releaseDate(tmdbMovieResponse.getReleaseDate())
                    .genres(tmdbMovieResponse.getGenreIds().stream().map(String::valueOf).toList())
                    .description(tmdbMovieResponse.getOverview())
                    .posterUrl(tmdbMovieResponse.getPosterPath())
                    .backdropUrl(tmdbMovieResponse.getBackdropPath())
                    .build();

            mediaDataList.add(newMediaDataItem);
        }

        return ResponseEntity.ok(mediaDataRepository.saveAll(mediaDataList));
    }

    public ResponseEntity<List<MediaData>> getTvMediaItems() {

        HttpEntity<Void> request = new HttpEntity<>(configureTMDBHeader());

        ResponseEntity<TmdbTVResponseDTO> response =
                restTemplate.exchange(URI.create(TV_API_URL), HttpMethod.GET, request, TmdbTVResponseDTO.class);

        TmdbTVResponseDTO body = response.getBody();
        List<MediaData> mediaDataList = new ArrayList<>();

        for (TmdbTVResponse tmdbTVResponse : body.getResults()) {
            MediaData newMediaDataItem = MediaData.builder()
                    .externalMediaId(String.valueOf(tmdbTVResponse.getId()))
                    .type(MediaData.MediaType.TV)
                    .title(tmdbTVResponse.getName())
                    .releaseDate(tmdbTVResponse.getFirstAirDate())
                    .genres(tmdbTVResponse.getGenreIds().stream().map(String::valueOf).toList())
                    .description(tmdbTVResponse.getOverview())
                    .posterUrl(tmdbTVResponse.getPosterPath())
                    .backdropUrl(tmdbTVResponse.getBackdropPath())
                    .build();

            mediaDataList.add(newMediaDataItem);
        }

        return ResponseEntity.ok(mediaDataRepository.saveAll(mediaDataList));

    }

    public ResponseEntity<List<MediaData>> getBooksMediaItems() {

        URI uri = UriComponentsBuilder.fromHttpUrl(GOOGLE_BOOKS_URI)
                .queryParam("q", "Money")
                .queryParam("subject:", "fiction")
                .queryParam("printType", "books")
                .queryParam("key", GOOGLE_BOOKS_API)
                .build(true) // keep RFC characters
                .toUri();

        ResponseEntity<GoogleBooksResponseDTO> resp = restTemplate.exchange(
                uri, HttpMethod.GET, new HttpEntity<>(configureDefaultHeader()), GoogleBooksResponseDTO.class);

        GoogleBooksResponseDTO body = resp.getBody();
        List<MediaData> mediaDataList = new ArrayList<>();

        for (GoogleBooksResponse item : body.getItems()) {

            VolumeInfo v = item.getVolumeInfo();

            MediaData media = MediaData.builder()
                    .externalMediaId(item.getId())
                    .type(MediaData.MediaType.BOOKS)
                    .title(v.getTitle())
                    .releaseDate(v.getPublishedDate())
                    .genres(v.getCategories())
                    .description(v.getDescription())
                    .posterUrl(v.getImageLinks().getThumbnail())
                    .backdropUrl(null)
                    .build();

            mediaDataList.add(media);
        }

        return ResponseEntity.ok(mediaDataRepository.saveAll(mediaDataList));

    }


    public HttpHeaders configureTMDBHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(TMDB_BEARER_TOKEN);
        return headers;
    }

    public HttpHeaders configureDefaultHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }


}