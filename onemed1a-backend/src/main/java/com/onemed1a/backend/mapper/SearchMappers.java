package com.onemed1a.backend.mapper;

import com.onemed1a.backend.model.MediaData;
import com.onemed1a.backend.dto.SearchResultItem;
import com.onemed1a.backend.dto.SuggestResultItem;


/**
 * Utility class for mapping {@link MediaData} entities to lightweight DTOs
 * used in search and suggestion responses.
 */
public final class SearchMappers {

    private SearchMappers() {}

     /**
     * Converts a {@link MediaData} entity to a {@link SearchResultItem}.
     *
     * @param m the media entity to convert
     * @return a search result item containing key media information
     */
    public static SearchResultItem toResultItem(MediaData m) {
        return SearchResultItem.builder()
                .id(m.getMediaId())
                .title(m.getTitle())
                .type(m.getType())
                .releaseDate(m.getReleaseDate())
                .genres(m.getGenres())
                .build();
    }


    /**
     * Converts a {@link MediaData} entity to a {@link SuggestResultItem}.
     *
     * @param m the media entity to convert
     * @return a suggestion item containing minimal media information
     */
    public static SuggestResultItem toSuggestItem(MediaData m) {
        return SuggestResultItem.builder()
                .id(m.getMediaId())
                .title(m.getTitle())
                .type(m.getType())
                .build();
    }
}