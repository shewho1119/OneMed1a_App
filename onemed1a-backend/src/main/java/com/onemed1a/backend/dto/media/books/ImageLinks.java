package com.onemed1a.backend.dto.media.books;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


/**
 * Represents the image links for a book returned by the Google Books API.
 *
 * Commonly includes small and standard thumbnails.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageLinks {
    private String smallThumbnail;
    private String thumbnail;   // commonly used as poster
}
