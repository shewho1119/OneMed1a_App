package com.onemed1a.backend.external.media.controller;


import com.onemed1a.backend.external.media.service.DataService;
import com.onemed1a.backend.media.MediaData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/externalMediaData")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MediaData>> getMovieMediaItems() {
        try {
            return dataService.getMovieMediaItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tv")
    public ResponseEntity<List<MediaData>> getTvMediaItems() {
        try {
            return dataService.getTvMediaItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/books")
    public ResponseEntity<List<MediaData>> getBookMediaItems() {
        try {
            return dataService.getBooksMediaItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/music")
    public ResponseEntity<List<MediaData>> getMusicMediaItems() {
        try {
            return dataService.getMusicMediaItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }



}
