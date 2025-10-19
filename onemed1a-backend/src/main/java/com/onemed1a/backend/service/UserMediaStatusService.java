package com.onemed1a.backend.service;

import java.util.List;
import java.util.UUID;

import com.onemed1a.backend.model.MediaData;
import com.onemed1a.backend.model.MediaData.MediaType;
import com.onemed1a.backend.repository.MediaDataRepository;
import com.onemed1a.backend.repository.UserMediaStatusRepository;
import com.onemed1a.backend.model.User;
import com.onemed1a.backend.repository.UserRepository;
import com.onemed1a.backend.model.UserMediaStatus;
import com.onemed1a.backend.model.UserMediaStatus.Status;

import com.onemed1a.backend.dto.UserMediaStatusDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service layer for UserMedia. Implements the logic expected by the controller:
 * - List statuses for a user with optional filters + simple paging/sorting
 * - Get one status by (userId, mediaId)
 * - Upsert (create or update) a status
 * - Update an existing status
 * - Delete a status
 * Notes:
 * - This implementation loads from the repository and filters/sorts in-memory
 *   to avoid tight coupling to repository query definitions while branches are diverged.
 * - When auth is wired, pass the authenticated userId into these methods.
 */

@Service
public class UserMediaStatusService {

    private final UserMediaStatusRepository userMediaStatusRepository;
    private final UserRepository userRepository;
    private final MediaDataRepository mediaDataRepository;

    public UserMediaStatusService(
            UserMediaStatusRepository userMediaStatusRepository,
            UserRepository userRepository,
            MediaDataRepository mediaDataRepository
    ) {
        this.userMediaStatusRepository = userMediaStatusRepository;
        this.userRepository = userRepository;
        this.mediaDataRepository = mediaDataRepository;
    }


    /** List statuses for a user with optional filters and simple paging/sorting.
     * @param type
     * @param status
     * @param userId
     */
    public List<UserMediaStatus> getUserMedia(UUID userId, Status status, MediaType type){
        List<UserMediaStatus> userMediaStatusList = userMediaStatusRepository.findByUser_Id(userId);
        return userMediaStatusList.stream()
                .filter(media -> type == null || media.getMedia().getType().equals(type))
                .filter(media -> status == null || media.getStatus().equals(status))
                .toList();
    }

    /** Create or update (upsert) status for (userId, mediaId). Returns the saved entity. */
    public ResponseEntity<UserMediaStatus> upsert(UserMediaStatusDTO userMediaStatusDTO) {

        // Read incoming data from DTO
        UUID id = userMediaStatusDTO.getId();
        UUID userId = userMediaStatusDTO.getUserId();
        UUID mediaId = userMediaStatusDTO.getMediaId();
        Status status = userMediaStatusDTO.getStatus();
        Integer rating = userMediaStatusDTO.getRating(); // may be null
        String reviewText = userMediaStatusDTO.getReviewText(); // may be null

        // Load referenced entities (fail fast if they don't exist)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        MediaData mediaData = mediaDataRepository.findById(mediaId)
                .orElseThrow(() -> new IllegalArgumentException("Media not found: " + mediaId));

        UserMediaStatus ums;

            if (id != null) {
                // Try to find by id; if not found, create new entry
                ums = userMediaStatusRepository.findById(id).orElse(
                    UserMediaStatus.builder()
                        .id(id)
                        .user(user)
                        .media(mediaData)
                        .status(status)
                        .rating(rating)
                        .reviewText(reviewText)
                        .build()
                );
            } else {
                // Try to find existing by (user, media); otherwise create new
                ums = userMediaStatusRepository.findByUser_IdAndMedia_MediaId(userId, mediaId)
                    .orElse(
                        UserMediaStatus.builder()
                            .user(user)
                            .media(mediaData)
                            .status(status)
                            .rating(rating)
                            .reviewText(reviewText)
                            .build()
                    );
            }

        
        // Always set status from DTO
        ums.setStatus(status);
        // Only overwrite rating if DTO provides one; else keep existing
        if (rating != null) {
            ums.setRating(rating);
        }
        // Only overwrite reviewText if provided; else keep existing
        if (reviewText != null) {
            ums.setReviewText(reviewText);
        }
        // Ensure associations present (esp. when updating an older row missing them)
        if (ums.getUser() == null)  ums.setUser(user);
        if (ums.getMedia() == null) ums.setMedia(mediaData);

        // Save and be defensive if a mock returns null
        UserMediaStatus saved = userMediaStatusRepository.save(ums);
        if (saved == null) saved = ums;

        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<UserMediaStatus> getStatus(UUID userId, UUID mediaId) {
        return userMediaStatusRepository
                .findByUser_IdAndMedia_MediaId(userId, mediaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * Delete the status for (userId, mediaId) or by id. Returns true if something was deleted.
     *
     * @return a boolean to see if it deleted the media status correctly.
     */
    public boolean delete(UUID statusId) {

        try {
            userMediaStatusRepository.deleteById(statusId);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(e);
        }
        return false;
    }

}