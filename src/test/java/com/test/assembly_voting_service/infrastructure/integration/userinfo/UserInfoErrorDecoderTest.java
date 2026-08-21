package com.test.assembly_voting_service.infrastructure.integration.userinfo;

import com.test.assembly_voting_service.domain.exception.ResourceNotFoundException;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class UserInfoErrorDecoderTest {

    private final UserInfoErrorDecoder decoder = new UserInfoErrorDecoder();

    @Test
    @DisplayName("Deve retornar ResourceNotFoundException quando o status for 404")
    void shouldReturnResourceNotFoundExceptionWhenStatusIs404() {
        var request = Request.create(
                Request.HttpMethod.GET,
                "/users/123",
                Map.of(),
                null,
                StandardCharsets.UTF_8,
                new RequestTemplate()
        );

        var response = Response.builder()
                .request(request)
                .status(404)
                .reason("Not Found")
                .headers(Map.of())
                .build();

        var exception = decoder.decode("UserInfoClient#checkEligibility", response);

        assertInstanceOf(ResourceNotFoundException.class, exception);
        assertEquals("Member CPF is invalid or not found in external service", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar FeignException quando o status for diferente de 404")
    void shouldReturnFeignExceptionWhenStatusIsNot404() {
        var request = Request.create(
                Request.HttpMethod.GET,
                "/users/123",
                Map.of(),
                null,
                StandardCharsets.UTF_8,
                new RequestTemplate()
        );

        var response = Response.builder()
                .request(request)
                .status(500)
                .reason("Internal Server Error")
                .headers(Map.of())
                .build();

        var exception = decoder.decode("UserInfoClient#checkEligibility", response);

        assertInstanceOf(FeignException.class, exception);
    }
}
