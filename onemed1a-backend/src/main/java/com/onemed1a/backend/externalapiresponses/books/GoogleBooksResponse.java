package com.onemed1a.backend.externalapiresponses.books;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleBooksResponse {
    private String id;
    private VolumeInfo volumeInfo;
}
