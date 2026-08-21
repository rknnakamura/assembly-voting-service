package com.test.assembly_voting_service.infrastructure.web.exception;

import com.test.assembly_voting_service.domain.exception.BusinessException;
import com.test.assembly_voting_service.domain.exception.ResourceNotFoundException;
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
    void shouldHandleResourceNotFoundException() {
        var ex = new ResourceNotFoundException("Agenda not found");
        var response = handler.handleResourceNotFoundException(ex);

        assertNotNull(response);
        assertNotNull(response.timestamp());
        assertEquals("Agenda not found", response.message());
    }

    @Test
    void shouldHandleBusinessException() {
        var ex = new BusinessException("Voting session is closed");
        var response = handler.handleBusinessException(ex);

        assertNotNull(response);
        assertNotNull(response.timestamp());
        assertEquals("Voting session is closed", response.message());
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
