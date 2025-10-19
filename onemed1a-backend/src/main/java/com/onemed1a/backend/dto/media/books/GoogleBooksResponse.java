package com.onemed1a.backend.dto.media.books;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Represents a single item returned from the Google Books API.
 *
 * Contains the book ID and metadata contained in {@link VolumeInfo}.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleBooksResponse {

    /** Unique identifier of the book from Google Books. */
    private String id;
    
    /** Detailed metadata of the book, such as title, authors, and description. */
    private VolumeInfo volumeInfo;
}
