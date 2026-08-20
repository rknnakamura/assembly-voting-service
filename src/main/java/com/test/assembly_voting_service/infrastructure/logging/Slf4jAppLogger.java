package com.test.assembly_voting_service.infrastructure.logging;

import com.test.assembly_voting_service.application.port.out.AppLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Slf4jAppLogger implements AppLogger {
    private final Logger log = LoggerFactory.getLogger(Slf4jAppLogger.class);

    @Override
    public void info(String message, Object... args) {
        log.info(message, args);
    }
}
