package com.test.assembly_voting_service.infrastructure.web.dto.response.app;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record FormResponse(
        @JsonProperty("tipo") String type,
        @JsonProperty("titulo") String title,
        @JsonProperty("itens") List<FormItem> items,
        @JsonProperty("botaoAcao") ActionButton actionButton
) {
    public record FormItem(
            @JsonProperty("tipo") String type,
            @JsonProperty("id") String id,
            @JsonProperty("titulo") String title,
            @JsonProperty("valor") Object value
    ) {
    }

    public record ActionButton(
            @JsonProperty("texto") String text,
            @JsonProperty("url") String url,
            @JsonProperty("body") Map<String, Object> body
    ) {
    }
}
