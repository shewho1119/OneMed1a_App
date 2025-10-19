
package com.onemed1a.backend.dto.media.music;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * Represents a paginated page of albums returned by the Spotify Web API.
 *
 * Includes metadata such as paging links, limits, and total results.
 */
@Getter 
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyAlbumsPage {
    private String href;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;
    private List<SpotifyAlbum> items;
}
