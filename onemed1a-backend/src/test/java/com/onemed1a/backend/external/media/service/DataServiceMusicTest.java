package com.onemed1a.backend.external.media.service;

import com.onemed1a.backend.external.media.responses.music.SpotifyAlbum;
import com.onemed1a.backend.external.media.responses.music.SpotifyAlbumsPage;
import com.onemed1a.backend.external.media.responses.music.SpotifyImage;
import com.onemed1a.backend.external.media.responses.music.SpotifyResponseDTO;
import com.onemed1a.backend.media.MediaDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DataServiceMusicTest {

    private DataService dataService;
    private MediaDataRepository repository;
    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        repository = mock(MediaDataRepository.class);
        restTemplate = mock(RestTemplate.class);

        dataService = new DataService(repository, restTemplate);

        when(repository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));
    }


    @Test
    void shouldFetchAndSaveMusicAlbums() {
        // Fake Spotify album
        SpotifyAlbum album = new SpotifyAlbum();
        album.setId("album1");
        album.setName("Test Album");
        album.setReleaseDate("2023-05-05");

        SpotifyImage img = new SpotifyImage();
        img.setUrl("/album.jpg");
        album.setImages(List.of(img));

        SpotifyAlbumsPage albums = new SpotifyAlbumsPage();
        albums.setItems(List.of(album));

        SpotifyResponseDTO dto = new SpotifyResponseDTO();
        dto.setAlbums(albums);

        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(), eq(SpotifyResponseDTO.class)))
                .thenReturn(new ResponseEntity<>(dto, HttpStatus.OK));

        var result = dataService.getMusicMediaItems().getBody();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Album");
        assertThat(result.get(0).getPosterUrl()).isEqualTo("/album.jpg");

        verify(repository).saveAll(result);
    }
}
