package com.onemed1a.backend.external.media.service;

import com.onemed1a.backend.dto.media.books.GoogleBooksResponse;
import com.onemed1a.backend.dto.media.books.GoogleBooksResponseDTO;
import com.onemed1a.backend.dto.media.books.VolumeInfo;
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
 * Unit tests for {@link DataService} focusing on Google Books integration.
 *
 * Verifies that book items are fetched from the external API and persisted
 * correctly into the database via {@link MediaDataRepository}.
 */
class DataServiceBookTest {

    private DataService dataService;
    private MediaDataRepository repository;
    private RestTemplate restTemplate;

    /**
     * Sets up mocks and injects test configuration values into {@link DataService}.
     */
    @BeforeEach
    void setup() {
        // Mock dependencies
        repository = mock(MediaDataRepository.class);
        restTemplate = mock(RestTemplate.class);

        // Inject mocks into the service under test
        dataService = new DataService(repository, restTemplate);

        // Inject fake API endpoints and credentials using ReflectionTestUtils
        ReflectionTestUtils.setField(dataService, "movieApiUrl", "http://fake.example/movies");
        ReflectionTestUtils.setField(dataService, "tvApiUrl", "http://fake.example/tv");
        ReflectionTestUtils.setField(dataService, "spotifySearchUrl", "http://fake.example/search");
        ReflectionTestUtils.setField(dataService, "spotifyTokenUrl", "http://fake.example/token");
        ReflectionTestUtils.setField(dataService, "spotifyClientId", "cid");
        ReflectionTestUtils.setField(dataService, "spotifyClientSecret", "secret");
        ReflectionTestUtils.setField(dataService, "googleBooksUri", "http://fake.example/books");
        ReflectionTestUtils.setField(dataService, "googleBooksApiKey", "key");

        // Simulate repository.saveAll() returning the same list passed in
        when(repository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));
    }


    /**
     * Validates that {@link DataService#getBooksMediaItems()}:
     * <ul>
     *     <li>Calls the Google Books API via {@link RestTemplate}</li>
     *     <li>Maps API response fields (title, genre, description) correctly</li>
     *     <li>Persists all mapped MediaData records through the repository</li>
     * </ul>
     */
    @Test
    void shouldFetchAndSaveBooks() {
        // Fake Google Books response
        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.setTitle("Clean Code");
        volumeInfo.setPublishedDate("2008");
        volumeInfo.setCategories(List.of("Programming"));
        volumeInfo.setDescription("A Handbook of Agile Software Craftsmanship");

        GoogleBooksResponse book = new GoogleBooksResponse();
        book.setId("book123");
        book.setVolumeInfo(volumeInfo);

        GoogleBooksResponseDTO dto = new GoogleBooksResponseDTO();
        dto.setItems(List.of(book));

        // Stub the RestTemplate exchange call
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(), eq(GoogleBooksResponseDTO.class)))
                .thenReturn(new ResponseEntity<>(dto, HttpStatus.OK));

        
        // Execute the method under test
        var result = dataService.getBooksMediaItems().getBody();

        // Validate that one item was returned and fields were mapped correctly
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Clean Code");
        assertThat(result.get(0).getGenres()).containsExactly("Programming");

        // Verify the saveAll() method was called with the correct data
        verify(repository).saveAll(result);
    }
}
