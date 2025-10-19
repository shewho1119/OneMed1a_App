package com.onemed1a.backend.controller;


import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onemed1a.backend.dto.UserMediaStatusDTO;
import com.onemed1a.backend.model.MediaData;
import com.onemed1a.backend.model.UserMediaStatus;
import com.onemed1a.backend.service.UserMediaStatusService;

import jakarta.validation.Valid;


/**
 * Controller for managing a user's media statuses.
 *
 * Handles creation, retrieval, and deletion of user media interactions
 * such as ratings, statuses, and reviews.
 */
@RestController
@RequestMapping("/api/v1/usermedia")
public class UserMediaStatusController {

    private final UserMediaStatusService userMediaService;


    /**
     * Creates a new UserMediaStatusController with the given service.
     *
     * @param userMediaService service for handling user media statuses
     */
    public UserMediaStatusController(UserMediaStatusService userMediaService) {
        this.userMediaService = userMediaService;
    }



     /**
     * Returns all media statuses for a given user.
     *
     * @param userId ID of the user
     * @param status optional filter by media status
     * @param type optional filter by media type
     * @param page page number (currently unused)
     * @param size page size (currently unused)
     * @param sort sort order (currently unused)
     * @return a list of the user's media statuses
     */
    @GetMapping("/user/{userId}") 
    public List<UserMediaStatus> getUserMediaByUserId(
            @PathVariable UUID userId,
            @RequestParam(required = false) UserMediaStatus.Status status,
            @RequestParam(required = false) MediaData.MediaType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt,desc") String sort
    ) {
        return userMediaService.getUserMedia(userId, status, type);
    }

    /**
     * Creates or updates a user's media status (upsert).
     *
     * @param userMediaStatus media status data to create or update
     * @return the created or updated media status
     */
    @PostMapping
    public ResponseEntity<UserMediaStatus> createUserMediaStatus(@Valid @RequestBody UserMediaStatusDTO userMediaStatus) {
        return userMediaService.upsert(userMediaStatus);
    }

    /**
     * Deletes a user's media status by ID.
     *
     * @param statusId ID of the media status to delete
     * @return the deleted status ID
     */
    @DeleteMapping("/{statusId}")
    public ResponseEntity<UUID> deleteUserMediaStatus(@PathVariable ("statusId") UUID statusId) {
        userMediaService.delete(statusId);
        return ResponseEntity.ok(statusId);
    }


    /**
     * Retrieves the media status for a specific user-media combination.
     *
     * @param userId ID of the user
     * @param mediaId ID of the media item
     * @return the user's media status if found
     */
    @GetMapping("/{userId}/{mediaId}")
    public ResponseEntity<UserMediaStatus> getStatus(
            @PathVariable UUID userId,
            @PathVariable UUID mediaId) {
        return userMediaService.getStatus(userId, mediaId);
    }

    /**
     * Returns a count of user media statuses grouped by media type.
     *
     * @param userId ID of the user
     * @return a map of media type to count
     */
    @GetMapping("/user/{userId}/stats")
    public Map<String, Long> getUserMediaStats(@PathVariable UUID userId) {
        return userMediaService.getUserMediaCountsByType(userId);
    }
}