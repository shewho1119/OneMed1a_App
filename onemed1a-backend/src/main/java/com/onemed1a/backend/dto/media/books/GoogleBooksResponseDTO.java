package com.onemed1a.backend.dto.media.books;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

/**
 * Represents the top-level response from the Google Books API.
 *
 * Contains the total number of items and a list of book entries.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleBooksResponseDTO {

    private Integer totalItems;
    private List<GoogleBooksResponse> items;

}
