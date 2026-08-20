package com.test.assembly_voting_service.infrastructure.web.dto.response.app;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record SelectionResponse(
        @JsonProperty("tipo") String type,
        @JsonProperty("titulo") String title,
        @JsonProperty("itens") List<SelectionItem> items
) {
    public record SelectionItem(
            @JsonProperty("texto") String text,
            @JsonProperty("url") String url,
            @JsonProperty("body") Map<String, Object> body
    ) {
    }
}
