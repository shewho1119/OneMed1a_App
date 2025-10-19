package com.onemed1a.backend.repository;

import com.onemed1a.backend.model.UserMediaStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing and managing {@link UserMediaStatus} entities.
 *
 * Provides custom queries for user-specific media statuses.
 */
public interface UserMediaStatusRepository extends JpaRepository<UserMediaStatus, UUID> {

    /**
     * Returns all media statuses for a given user.
     *
     * @param userId the user's UUID
     * @return a list of statuses linked to the user
     */
    List<UserMediaStatus> findByUser_Id(UUID userId);


    /**
     * Finds a user's media status by user ID and media ID.
     *
     * @param userId the user's UUID
     * @param mediaId the media's UUID
     * @return an optional containing the user's media status if it exists
     */
    Optional<UserMediaStatus> findByUser_IdAndMedia_MediaId(UUID userId, UUID mediaId);
}