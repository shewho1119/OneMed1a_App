package com.onemed1a.backend.dto.media.books;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;


/**
 * Represents detailed metadata about a book from the Google Books API.
 *
 * Includes title, authors, publication details, categories, and cover images.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VolumeInfo {
    private String title;
    private String subtitle;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private List<IndustryIdentifier> industryIdentifiers;
    private Integer pageCount;
    private String printType;
    private List<String> categories;
    private String maturityRating;
    private ImageLinks imageLinks;
    private String language;
    private String previewLink;
    private String infoLink;
    private String canonicalVolumeLink;
}


/**
 * Represents a book's industry identifier such as ISBN codes.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class IndustryIdentifier {

      /** Type of identifier (e.g., ISBN_13, ISBN_10). */
    private String type;       

    /** The actual identifier value. */
    private String identifier;
}

