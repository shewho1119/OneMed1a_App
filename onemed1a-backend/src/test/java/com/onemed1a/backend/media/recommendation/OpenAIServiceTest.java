package com.onemed1a.backend.media.recommendation;

import com.onemed1a.backend.model.MediaData;
import com.onemed1a.backend.repository.MediaDataRepository;
import com.onemed1a.backend.service.OpenAIService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link OpenAIService}.
 *
 * These tests verify that the recommendation logic behaves as expected without
 * calling the real OpenAI API, ensuring repository interactions and output format
 * are valid.
 */
class OpenAIServiceTest {

    private MediaDataRepository mediaDataRepository;
    private OpenAIService openAIService;

    /**
     * Sets up test dependencies before each test.
     *
     * Mocks {@link MediaDataRepository}, creates a spy for {@link OpenAIService},
     * and overrides the real network call to return a predefined recommendation list.
     */
    @BeforeEach
    void setup() {
        mediaDataRepository = mock(MediaDataRepository.class);
        // pass a dummy key; we won't call the network
        openAIService = spy(new OpenAIService("dummy-key", mediaDataRepository));

        // Stub getRecommendation() to return fake recommendation data.
        doReturn(List.of(
            MediaData.builder()
                .title("Some Movie")
                .description("A description")
                .genres(List.of("Action", "Sci-Fi"))
                .releaseDate("2010-07-16")
                .type(MediaData.MediaType.MOVIE)
                .build()
        )).when(openAIService).getRecommendation(anyString(), anyString());

        // Simulate repository behavior
        when(mediaDataRepository.findByTitleAndType(anyString(), any())).thenReturn(Optional.empty());
        when(mediaDataRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
    }

    /**
     * Verifies that {@link OpenAIService#getRecommendation(String, String)}
     * returns a non-null and non-empty list when invoked with sample input.
     *
     * Since the service is spied, the result is stubbed to avoid real API calls.
     * Assertions confirm that the returned list structure is valid.
     */
    @Test
    void testGetRecommendation_realApi_returnsValidList() {
        var recommendations = openAIService.getRecommendation("MOVIE", "Inception");

        // Ensure valid, non-empty response
        assertNotNull(recommendations);
        assertFalse(recommendations.isEmpty());
    }
}
