package com.onemed1a.backend.external.media.service;

import com.onemed1a.backend.dto.media.music.*;
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
 * Unit tests for {@link DataService} focused on Spotify (music) integration.
 *
 * Validates that albums are fetched, parsed, and saved correctly
 * after authenticating with the Spotify API.
 */
class DataServiceMusicTest {

    private DataService dataService;
    private MediaDataRepository repository;
    private RestTemplate restTemplate;

    /**
     * Initializes the mocked {@link MediaDataRepository} and {@link RestTemplate}
     * and injects fake configuration properties into {@link DataService}.
     */
    @BeforeEach
    void setup() {
        // Mock dependencies
        repository = mock(MediaDataRepository.class);
        restTemplate = mock(RestTemplate.class);

        // DataService should have a constructor that accepts (MediaDataRepository, RestTemplate)
        dataService = new DataService(repository, restTemplate);

        // Provide non-null config for @Value fields
        ReflectionTestUtils.setField(dataService, "movieApiUrl", "http://fake.example/movies");
        ReflectionTestUtils.setField(dataService, "tvApiUrl", "http://fake.example/tv");
        ReflectionTestUtils.setField(dataService, "spotifySearchUrl", "http://fake.example/search");
        ReflectionTestUtils.setField(dataService, "spotifyTokenUrl", "http://fake.example/token");
        ReflectionTestUtils.setField(dataService, "spotifyClientId", "cid");
        ReflectionTestUtils.setField(dataService, "spotifyClientSecret", "secret");
        ReflectionTestUtils.setField(dataService, "googleBooksUri", "http://fake.example/books");
        ReflectionTestUtils.setField(dataService, "googleBooksApiKey", "key");

        when(repository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));
    }


    /**
     * Ensures that {@link DataService#getMusicMediaItems()}:
     * <ul>
     *     <li>Retrieves an access token via Spotify’s token endpoint</li>
     *     <li>Fetches album metadata using the authenticated request</li>
     *     <li>Maps response fields (title, image, release date) correctly</li>
     *     <li>Saves the resulting list to {@link MediaDataRepository}</li>
     * </ul>
     */
    @Test
    void shouldFetchAndSaveMusicAlbums() {

        // Mock token retrieval response
        when(restTemplate.exchange(
                eq("http://fake.example/token"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(SpotifyTokenResponse.class)
        )).thenReturn(new ResponseEntity<>(
                new SpotifyTokenResponse("token_code", "Bearer", 3600),
                HttpStatus.OK
        ));

        // Prepare fake album data
        SpotifyAlbum album = new SpotifyAlbum();
        album.setId("album1");
        album.setName("Test Album");
        album.setReleaseDate("2023-05-05");

        // Add album image
        SpotifyImage img = new SpotifyImage();
        img.setUrl("/album.jpg");
        album.setImages(List.of(img));

        // Add album image
        SpotifyAlbumsPage albums = new SpotifyAlbumsPage();
        albums.setItems(List.of(album));

        SpotifyResponseDTO dto = new SpotifyResponseDTO();
        dto.setAlbums(albums);

        // Mock the GET request to Spotify API
        when(restTemplate.exchange(
                any(URI.class),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyResponseDTO.class)
        )).thenReturn(new ResponseEntity<>(dto, HttpStatus.OK));

        // Execute DataService method
        var result = dataService.getMusicMediaItems().getBody();

        // Verify correct transformation
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Album");
        assertThat(result.get(0).getPosterUrl()).isEqualTo("/album.jpg");

        // Verify persistence
        verify(repository).saveAll(result);
    }
}
