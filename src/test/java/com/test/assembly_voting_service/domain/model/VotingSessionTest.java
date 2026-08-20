package com.test.assembly_voting_service.domain.model;

import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VotingSessionTest {

    @Nested
    @DisplayName("Criação de VotingSession")
    class Creation {

        @Test
        @DisplayName("deve criar sessão com todos os campos válidos")
        void shouldCreateWithValidFields() {
            var id = UUID.randomUUID();
            var agendaId = UUID.randomUUID();
            var startedAt = OffsetDateTime.now();
            var endedAt = startedAt.plusMinutes(1);

            var session = new VotingSession(id, agendaId, startedAt, endedAt);

            assertEquals(id, session.id());
            assertEquals(agendaId, session.agendaId());
            assertEquals(startedAt, session.startedAt());
            assertEquals(endedAt, session.endedAt());
        }

        @Test
        @DisplayName("deve lançar exceção quando id for nulo")
        void shouldThrowWhenIdIsNull() {
            var now = OffsetDateTime.now();
            var ex = assertThrows(NullPointerException.class,
                    () -> new VotingSession(null, UUID.randomUUID(), now, now.plusMinutes(1)));
            assertEquals("id must not be null", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando agendaId for nulo")
        void shouldThrowWhenAgendaIdIsNull() {
            var now = OffsetDateTime.now();
            var ex = assertThrows(NullPointerException.class,
                    () -> new VotingSession(UUID.randomUUID(), null, now, now.plusMinutes(1)));
            assertEquals("agendaId must not be null", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando startedAt for nulo")
        void shouldThrowWhenStartedAtIsNull() {
            var ex = assertThrows(NullPointerException.class,
                    () -> new VotingSession(UUID.randomUUID(), UUID.randomUUID(), null, OffsetDateTime.now()));
            assertEquals("startedAt must not be null", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando endedAt for nulo")
        void shouldThrowWhenEndedAtIsNull() {
            var ex = assertThrows(NullPointerException.class,
                    () -> new VotingSession(UUID.randomUUID(), UUID.randomUUID(), OffsetDateTime.now(), null));
            assertEquals("endedAt must not be null", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando endedAt for igual a startedAt")
        void shouldThrowWhenEndedAtEqualsStartedAt() {
            var now = OffsetDateTime.now();
            var ex = assertThrows(IllegalArgumentException.class,
                    () -> new VotingSession(UUID.randomUUID(), UUID.randomUUID(), now, now));
            assertEquals("endedAt must be after startedAt", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando endedAt for anterior a startedAt")
        void shouldThrowWhenEndedAtIsBeforeStartedAt() {
            var now = OffsetDateTime.now();
            var ex = assertThrows(IllegalArgumentException.class,
                    () -> new VotingSession(UUID.randomUUID(), UUID.randomUUID(), now, now.minusMinutes(1)));
            assertEquals("endedAt must be after startedAt", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("isOpen()")
    class IsOpen {

        @Test
        @DisplayName("deve retornar true quando sessão estiver aberta")
        void shouldReturnTrueWhenSessionIsOpen() {
            var now = OffsetDateTime.now();
            var session = new VotingSession(
                    UUID.randomUUID(), UUID.randomUUID(),
                    now.minusMinutes(5), now.plusMinutes(5));

            assertTrue(session.isOpen());
        }

        @Test
        @DisplayName("deve retornar false quando sessão já tiver encerrado")
        void shouldReturnFalseWhenSessionHasEnded() {
            var now = OffsetDateTime.now();
            var session = new VotingSession(
                    UUID.randomUUID(), UUID.randomUUID(),
                    now.minusMinutes(10), now.minusMinutes(5));

            assertFalse(session.isOpen());
        }

        @Test
        @DisplayName("deve retornar false quando sessão ainda não tiver iniciado")
        void shouldReturnFalseWhenSessionHasNotStarted() {
            var now = OffsetDateTime.now();
            var session = new VotingSession(
                    UUID.randomUUID(), UUID.randomUUID(),
                    now.plusMinutes(5), now.plusMinutes(10));

            assertFalse(session.isOpen());
        }
    }
}
