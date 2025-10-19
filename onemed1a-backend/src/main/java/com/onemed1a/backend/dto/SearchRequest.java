package com.onemed1a.backend.dto;

import com.onemed1a.backend.model.MediaData;
import com.onemed1a.backend.model.UserMediaStatus;
import lombok.*;

import java.util.UUID;


/**
 * Represents the search filters and parameters for querying media items.
 *
 * Used in /media/search endpoint to filter results by title, type, year, genre, or user status.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SearchRequest {
    private String q;                         // text in title
    private MediaData.MediaType type;         // MOVIE / TV / BOOK / MUSIC (whatever you use)
    private String genre;                     // exact match on a genre element
    private Integer yearFrom;                 // inclusive (compared against releaseDate string)
    private Integer yearTo;                   // inclusive
    private UUID userId;                      // optional: filter by this user's status
    private UserMediaStatus.Status status;    // COMPLETED / WATCHING / PLAN_TO_WATCH
}