package com.onemed1a.backend.external.media.service;

import com.onemed1a.backend.dto.media.movies.TmdbMovieResponse;
import com.onemed1a.backend.dto.media.movies.TmdbMovieResponseDTO;
import com.onemed1a.backend.repository.MediaDataRepository;
import com.onemed1a.backend.service.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DataService} focused on TMDB (movie) media items.
 *
 * Ensures that movies fetched from TMDB are correctly parsed,
 * converted into MediaData entities, and persisted to the repository.
 */
class DataServiceMovieTest {

    private DataService dataService;
    private MediaDataRepository repository;
    private RestTemplate restTemplate;

    /**
     * Initializes mocks for {@link MediaDataRepository} and {@link RestTemplate}.
     * Also injects test API endpoints and credentials into {@link DataService}.
     */
    @BeforeEach
    void setup() {
        // Mock dependencies
        repository = mock(MediaDataRepository.class);
        restTemplate = mock(RestTemplate.class);

        // Inject mocks into the service under test
        dataService = new DataService(repository, restTemplate);

        // Mock API endpoint configuration via reflection
        ReflectionTestUtils.setField(dataService, "movieApiUrl", "http://fake.example/movies");
        ReflectionTestUtils.setField(dataService, "tvApiUrl", "http://fake.example/tv");
        ReflectionTestUtils.setField(dataService, "spotifySearchUrl", "http://fake.example/search");
        ReflectionTestUtils.setField(dataService, "spotifyTokenUrl", "http://fake.example/token");
        ReflectionTestUtils.setField(dataService, "spotifyClientId", "cid");
        ReflectionTestUtils.setField(dataService, "spotifyClientSecret", "secret");
        ReflectionTestUtils.setField(dataService, "googleBooksUri", "http://fake.example/books");
        ReflectionTestUtils.setField(dataService, "googleBooksApiKey", "key");

        // Simulate repository.saveAll() returning its input
        when(repository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));
    }


    /**
     * Verifies that {@link DataService#getMovieMediaItems()}:
     * <ul>
     *     <li>Fetches movie data from a TMDB endpoint via {@link RestTemplate}</li>
     *     <li>Parses and maps it into MediaData entities correctly</li>
     *     <li>Persists the mapped data using {@link MediaDataRepository#saveAll(List)}</li>
     * </ul>
     */
    @Test
    void shouldFetchAndSaveMovies() {
        
        // Fake TMDB response
        TmdbMovieResponse movie = new TmdbMovieResponse();
        movie.setId(1);
        movie.setTitle("Inception");
        movie.setReleaseDate("2010-07-16");
        movie.setOverview("Dream heist thriller");
        movie.setPosterPath("/poster.jpg");
        movie.setBackdropPath("/backdrop.jpg");
        movie.setGenreIds(List.of(28, 878)); // Action, Sci-Fi

        // Wrap in TMDB response DTO
        TmdbMovieResponseDTO dto = new TmdbMovieResponseDTO();
        dto.setResults(List.of(movie));

        // Mock the RestTemplate HTTP exchange call
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(), eq(TmdbMovieResponseDTO.class)))
                .thenReturn(new ResponseEntity<>(dto, HttpStatus.OK));

        // Execute method under test
        var result = dataService.getMovieMediaItems().getBody();

        // Assertions to verify correct mapping
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Inception");
        assertThat(result.get(0).getGenres()).containsExactly("28", "878");

        // Ensure saveAll() was called with expected results
        verify(repository).saveAll(result);
    }
}
