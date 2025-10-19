package com.onemed1a.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.onemed1a.backend.model.MediaData;
import com.onemed1a.backend.model.MediaData.MediaType;
import com.onemed1a.backend.repository.MediaDataRepository;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

/**
 * Service for generating media recommendations using OpenAI.
 *
 * Creates a prompt, calls the OpenAI chat completions API, parses the response,
 * and returns or persists recommended media items.
 */
@Service
public class OpenAIService {

    private final OpenAIClient client;
    private final MediaDataRepository mediaDataRepository;

    /**
     * Creates a new OpenAIService with the given API key and repository.
     *
     * @param apiKey OpenAI API key
     * @param mediaDataRepository repository used to find or save media items
     */
    public OpenAIService(@Value("${open.api.key}") String apiKey, 
                         MediaDataRepository mediaDataRepository) {
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
        this.mediaDataRepository = mediaDataRepository;
    }

    /**
     * Generates up to five similar media recommendations for a given input.
     *
     * The response is parsed from a pipe-separated format. For each item:
     * - If a matching title and type already exist, it is reused.
     * - Otherwise, a new MediaData is created and saved.
     *
     * @param mediaType type of the source media (MOVIE, TV, MUSIC, BOOKS)
     * @param mediaName title or name of the source media
     * @return a list of recommended MediaData items (existing or newly saved)
     */
    public List<MediaData> getRecommendation(String mediaType, String mediaName) {
        String prompt = String.format("""
                You are a recommendation engine.

                Task:
                Given a media titled "%s", return 5 similar recommendations of type "%s".

                Rules:
                - Each recommendation must include exactly 5 fields in this order:
                Title | Genre | Description | Release Year | Media Type
                - Use the pipe character (`|`) as a separator between fields.
                - Separate recommendations with a newline.
                - Media Type must be one of: MOVIE, TV, MUSIC, BOOKS.
                - Do not include any commentary or formatting.

                Output format (example for 2 items):
                <title1> | <genre1> | <description1> | <release_year1> | <mediaType1>
                <title2> | <genre2> | <description2> | <release_year2> | <mediaType2>
                ...and so on until 5 items.
                """, mediaName, mediaType);

        // Prepare the API request to the OpenAI client
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(prompt)
                .model("gpt-5-nano") 
                .build();

        // Call the OpenAI API to generate recommendations
        ChatCompletion response = client.chat().completions().create(params);
        String res = response.choices().get(0).message().content().orElse("No content available");

        // List to hold all parsed and stored recommendation results
        List<MediaData> recommendations = new ArrayList<>();
        String[] items = res.split("\\r?\\n"); // split by newline
        for (String item : items) {
                String[] fields = item.split("\\|");
                
                if (fields.length == 5) {
                        String title = fields[0].trim();
                        String genre = fields[1].trim();
                        String description = fields[2].trim();
                        String releaseYear = fields[3].trim();
                        String type = fields[4].trim().toUpperCase();
                        MediaType mediaTypeEnum;
                        try {
                                // Convert type string to enum (skip invalid types)
                                mediaTypeEnum = MediaType.valueOf(type);
                        } catch (IllegalArgumentException e) {
                                continue; // Skip this item if the type is invalid
                        }
                        // Check if the media already exists in the repository
                        if (mediaDataRepository.findByTitleAndType(title, mediaTypeEnum).isPresent()) {
                                mediaDataRepository.findByTitleAndType(title, mediaTypeEnum).ifPresent(recommendations::add);
                        }else{
                                MediaData newMedia = MediaData.builder()
                                        .externalMediaId(java.util.UUID.randomUUID().toString()) // Generate a random UUID
                                        .title(title)
                                        .type(mediaTypeEnum)
                                        .genres(List.of(genre))
                                        .description(description)
                                        .releaseDate(releaseYear)
                                        .posterUrl("") // Placeholder, can be updated later
                                        .backdropUrl("") // Placeholder, can be updated later
                                        .createdAt(java.time.Instant.now())
                                        .build();
                                mediaDataRepository.save(newMedia);
                                MediaData add = mediaDataRepository.findByTitleAndType(title, mediaTypeEnum)
                                        .orElse(newMedia); // Ensure we get the saved entity
                                recommendations.add(add);
                        }
                }
        }
        return recommendations;

    }
}
