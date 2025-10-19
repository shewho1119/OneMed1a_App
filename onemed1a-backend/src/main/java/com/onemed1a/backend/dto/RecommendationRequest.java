package com.onemed1a.backend.dto;

/**
 * Data Transfer Object for requesting media recommendations.
 *
 * Used by the frontend to request related media based on a type and name.
 */
public class RecommendationRequest {
    private String mediaType;
    private String mediaName;

    /** Default constructor. */
    public RecommendationRequest() {}   

    // Getters & setters
    public String getMediaType() { return mediaType; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public String getMediaName() { return mediaName; }
    public void setMediaName(String mediaName) { this.mediaName = mediaName; }
}