package com.test.assembly_voting_service.domain.model;

import com.test.assembly_voting_service.domain.model.agenda.Agenda;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AgendaTest {

    @Nested
    @DisplayName("Criação de Agenda")
    class Creation {

        @Test
        @DisplayName("deve criar agenda com todos os campos válidos")
        void shouldCreateWithValidFields() {
            var id = UUID.randomUUID();
            var title = "Pauta de teste";
            var createdAt = OffsetDateTime.now();

            var agenda = new Agenda(id, title, createdAt);

            assertEquals(id, agenda.id());
            assertEquals(title, agenda.title());
            assertEquals(createdAt, agenda.createdAt());
        }

        @Test
        @DisplayName("deve lançar exceção quando id for nulo")
        void shouldThrowWhenIdIsNull() {
            var ex = assertThrows(NullPointerException.class,
                    () -> new Agenda(null, "Título", OffsetDateTime.now()));
            assertEquals("id must not be null", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando title for nulo")
        void shouldThrowWhenTitleIsNull() {
            var ex = assertThrows(NullPointerException.class,
                    () -> new Agenda(UUID.randomUUID(), null, OffsetDateTime.now()));
            assertEquals("title must not be null", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando title for vazio")
        void shouldThrowWhenTitleIsEmpty() {
            var ex = assertThrows(IllegalArgumentException.class,
                    () -> new Agenda(UUID.randomUUID(), "", OffsetDateTime.now()));
            assertEquals("title must not be blank", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando title contiver apenas espaços")
        void shouldThrowWhenTitleIsBlank() {
            var ex = assertThrows(IllegalArgumentException.class,
                    () -> new Agenda(UUID.randomUUID(), "   ", OffsetDateTime.now()));
            assertEquals("title must not be blank", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando createdAt for nulo")
        void shouldThrowWhenCreatedAtIsNull() {
            var ex = assertThrows(NullPointerException.class,
                    () -> new Agenda(UUID.randomUUID(), "Título", null));
            assertEquals("createdAt must not be null", ex.getMessage());
        }
    }
}
