package com.onemed1a.backend.controller;

import java.util.List;
import java.util.UUID;

import com.onemed1a.backend.model.MediaData;
import com.onemed1a.backend.service.MediaDataService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for handling requests related to media data.
 *
 * Provides endpoints to list media items with optional filters
 * and to fetch a single media item by its ID.
 */
@RestController
@RequestMapping("/media")

public class MediaDataController {

    private final MediaDataService mediaDataService;

     /**
     * Creates a new MediaDataController with the given service.
     *
     * @param mediaDataService service for accessing media data
     */
    public MediaDataController(MediaDataService mediaDataService) {
        this.mediaDataService = mediaDataService;
    }

    /**
     * Returns all media items, optionally filtered by query, type, year, or genre.
     *
     * @param q optional text query for search
     * @param type optional media type filter (e.g., movie, book)
     * @param year optional release or publish year
     * @param genre optional genre filter
     * @param page page number (currently unused)
     * @param size page size (currently unused)
     * @param sort sort order (currently unused)
     * @return a list of media items matching the filters
     */
    @RequestMapping
    public List<MediaData> getAllMedia(
        @RequestParam(required = false) String q,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Integer year,
        @RequestParam(required = false) String genre,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "title,asc") String sort
    ) {
        return mediaDataService.getAllMedia(q, type, year, genre);
    }

    /**
     * Returns a single media item by its ID.
     *
     * @param id unique ID of the media item
     * @return the media item if found
     * @throws ResponseStatusException if the media item does not exist
     */
    @RequestMapping("/{id}")
    public MediaData getMediaById(@PathVariable UUID id) {
        return mediaDataService.getMediaById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Media with id " + id + " not found"));
    }
}

