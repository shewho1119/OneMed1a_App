package com.onemed1a.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onemed1a.backend.dto.SearchRequest;
import com.onemed1a.backend.dto.SearchResultItem;
import com.onemed1a.backend.dto.SuggestResultItem;
import com.onemed1a.backend.service.SearchService;

import lombok.RequiredArgsConstructor;

/**
 * Controller for search-related endpoints.
 *
 * Provides APIs for full-text search and lightweight title suggestions
 * across different media types.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/media")
public class SearchController {

    private final SearchService service;

    /**
     * Performs a full search for media items using optional filters.
     * Supports pagination, sorting, and complex search queries.
     *
     * @param req the search request containing filters and query parameters
     * @param pageable pagination and sorting information
     * @return a paginated list of search result items
     */
    @GetMapping("/search")
    public Page<SearchResultItem> search(@ModelAttribute SearchRequest req, Pageable pageable) {
        return service.search(req, pageable);
    }

    /**
     * Provides quick autocomplete suggestions for media titles.
     *
     * @param q text prefix for suggestion search
     * @param limit maximum number of suggestions to return (default 5)
     * @return a list of suggested media titles
     */
    @GetMapping("/suggest")
    public List<SuggestResultItem> suggest(@RequestParam String q,
                                           @RequestParam(defaultValue = "5") int limit) {
        return service.suggest(q, limit);
    }
}