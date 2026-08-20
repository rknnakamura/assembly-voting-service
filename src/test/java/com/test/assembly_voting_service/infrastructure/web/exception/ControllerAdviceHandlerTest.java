package com.test.assembly_voting_service.infrastructure.web.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerAdviceHandlerTest {

    private ControllerAdviceHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ControllerAdviceHandler();
    }

    @Test
    void shouldHandleValidationExceptions() {
        var bindingResult = mock(BindingResult.class);
        var fieldError1 = new FieldError("request", "title", "must not be blank");
        var fieldError2 = new FieldError("request", "duration", "must be greater than zero");
        
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
        
        var ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        var response = handler.handleValidationExceptions(ex);

        assertNotNull(response);
        assertNotNull(response.timestamp());
        assertEquals("must not be blank, must be greater than zero", response.message());
    }

    @Test
    void shouldHandleDomainExceptions() {
        var ex = new IllegalArgumentException("Voto já registrado para este membro.");

        var response = handler.handleDomainExceptions(ex);

        assertNotNull(response);
        assertNotNull(response.timestamp());
        assertEquals("Voto já registrado para este membro.", response.message());
    }

    @Test
    void shouldHandleHttpMessageNotReadableException() {
        var ex = mock(HttpMessageNotReadableException.class);

        var response = handler.handleHttpMessageNotReadableException(ex);

        assertNotNull(response);
        assertNotNull(response.timestamp());
        assertEquals("Formato de dado inválido. Verifique se os valores informados estão corretos.", response.message());
    }

    @Test
    void shouldHandleGenericException() {
        var ex = new Exception("Falha de conexão com banco de dados");

        var response = handler.handleGenericException(ex);

        assertNotNull(response);
        assertNotNull(response.timestamp());
        assertEquals("Erro interno no servidor.", response.message());
    }
}
