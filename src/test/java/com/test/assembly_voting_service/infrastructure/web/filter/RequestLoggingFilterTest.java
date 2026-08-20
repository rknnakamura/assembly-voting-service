package com.test.assembly_voting_service.infrastructure.web.filter;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestLoggingFilterTest {

    @Test
    @DisplayName("Deve logar a request e response e seguir o filter chain normalmente")
    void shouldLogRequestAndResponseAndContinueFilterChain() throws ServletException, IOException {
        var filter = new RequestLoggingFilter();
        
        var request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/api/v1/agendas");

        var response = new MockHttpServletResponse();
        response.setStatus(201);
        
        var filterChain = new MockFilterChain();

        filter.doFilter(request, response, filterChain);

        assertNotNull(filterChain.getRequest(), "Request deveria ter sido repassada no chain");
        assertNotNull(filterChain.getResponse(), "Response deveria ter sido repassada no chain");
        assertEquals(request, filterChain.getRequest());
        assertEquals(response, filterChain.getResponse());
    }
}
