package com.onemed1a.backend.repository;

import com.onemed1a.backend.model.MediaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.onemed1a.backend.model.MediaData.MediaType;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing {@link MediaData} entities.
 *
 * Provides standard CRUD operations and custom queries for media lookup.
 */
public interface MediaDataRepository
        extends JpaRepository<MediaData, UUID>, JpaSpecificationExecutor<MediaData> {

    /**
     * Finds a media record by its title and type.
     *
     * @param title the media title
     * @param mediaTypeEnum the media type (MOVIE, TV, etc.)
     * @return an optional containing the matching media if found
     */
    Optional<MediaData> findByTitleAndType(String title, MediaType mediaTypeEnum);
}
