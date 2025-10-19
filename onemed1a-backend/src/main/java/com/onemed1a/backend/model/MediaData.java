package com.onemed1a.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Entity representing a media item (movie, TV show, music, or book).
 *
 * Stores metadata such as title, release date, genres, and image URLs.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media_data")
public class MediaData {


    /** Enum defining the supported media types. */
    public enum MediaType {
        MOVIE, TV, MUSIC, BOOKS
    }

    /** Unique identifier for the media record. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID mediaId;

    /** External API ID for cross-referencing (e.g., TMDB, Spotify, Google Books). */
    @Column(name = "external_media_id", nullable = false, updatable = false)
    private String externalMediaId;

    /** Type of media (e.g., MOVIE, TV, MUSIC, BOOKS). */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 16)
    private MediaType type; // MOVIE, TV, MUSIC, BOOKS

    /** Title of the media item. */
    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    /** Release year or date of the media item. */
    @Column(name = "release_year")
    private String releaseDate;

    /** Stores genres in a simple join table: media_genres(media_id, genre) */
    @ElementCollection
    @Builder.Default
    @Column(name = "genre", nullable = false)
    private List<String> genres = new ArrayList<>();

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "poster_url", columnDefinition = "TEXT")
    private String posterUrl;

    @Column(name = "backdrop_url", columnDefinition = "TEXT")
    private String backdropUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /** Automatically sets the creation timestamp before saving. */
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = Instant.now();
    }
}
