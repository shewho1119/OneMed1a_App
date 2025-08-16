package com.onemed1a.backend.externalmediadataservice;


import com.onemed1a.backend.mediadata.MediaData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/externalMediaData")
public class ExternalMediaDataController {

    private final ExternalMediaDataService externalMediaDataService;

    public ExternalMediaDataController(ExternalMediaDataService externalMediaDataService) {
        this.externalMediaDataService = externalMediaDataService;
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MediaData>> getMovies() {
        try {
            return externalMediaDataService.getMovieMediaItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }

}
