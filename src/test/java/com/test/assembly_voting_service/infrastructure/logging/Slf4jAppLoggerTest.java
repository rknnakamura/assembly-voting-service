package com.test.assembly_voting_service.infrastructure.logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

class Slf4jAppLoggerTest {

    private Slf4jAppLogger appLogger;
    private Logger mockLogger;

    @BeforeEach
    void setUp() {
        appLogger = new Slf4jAppLogger();
        mockLogger = mock(Logger.class);
        
        ReflectionTestUtils.setField(appLogger, "log", mockLogger);
    }

    @Test
    @DisplayName("Deve chamar o método info do slf4j corretamente")
    void shouldLogInfo() {
        var message = "Log message: {}";
        var arg = "value";

        appLogger.info(message, arg);

        verify(mockLogger, times(1)).info(message, new Object[]{arg});
    }
}
