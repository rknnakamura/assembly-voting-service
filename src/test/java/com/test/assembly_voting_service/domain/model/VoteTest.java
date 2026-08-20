package com.test.assembly_voting_service.domain.model;

import com.test.assembly_voting_service.domain.exception.DomainValidationException;
import com.test.assembly_voting_service.domain.model.vote.Vote;
import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoteTest {

    @Nested
    @DisplayName("Criação de Vote")
    class Creation {

        @Test
        @DisplayName("deve criar voto com todos os campos válidos")
        void shouldCreateWithValidFields() {
            var id = UUID.randomUUID();
            var agendaId = UUID.randomUUID();
            var memberId = UUID.randomUUID();
            var option = VoteOption.YES;
            var createdAt = OffsetDateTime.now();

            var vote = new Vote(id, agendaId, memberId, option, createdAt);

            assertEquals(id, vote.id());
            assertEquals(agendaId, vote.agendaId());
            assertEquals(memberId, vote.memberId());
            assertEquals(option, vote.option());
            assertEquals(createdAt, vote.createdAt());
        }

        @Test
        @DisplayName("deve lançar exceção quando id for nulo")
        void shouldThrowWhenIdIsNull() {
            var ex = assertThrows(DomainValidationException.class,
                    () -> new Vote(null, UUID.randomUUID(), UUID.randomUUID(), VoteOption.YES, OffsetDateTime.now()));
            assertEquals("id must not be null", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando agendaId for nulo")
        void shouldThrowWhenAgendaIdIsNull() {
            var ex = assertThrows(DomainValidationException.class,
                    () -> new Vote(UUID.randomUUID(), null, UUID.randomUUID(), VoteOption.YES, OffsetDateTime.now()));
            assertEquals("agendaId must not be null", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando memberId for nulo")
        void shouldThrowWhenMemberIdIsNull() {
            var ex = assertThrows(DomainValidationException.class,
                    () -> new Vote(UUID.randomUUID(), UUID.randomUUID(), null, VoteOption.YES, OffsetDateTime.now()));
            assertEquals("memberId must not be null", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando option for nulo")
        void shouldThrowWhenOptionIsNull() {
            var ex = assertThrows(DomainValidationException.class,
                    () -> new Vote(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), null, OffsetDateTime.now()));
            assertEquals("option must not be null", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando createdAt for nulo")
        void shouldThrowWhenCreatedAtIsNull() {
            var ex = assertThrows(DomainValidationException.class,
                    () -> new Vote(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), VoteOption.YES, null));
            assertEquals("createdAt must not be null", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("VoteOption")
    class VoteOptionTest {

        @Test
        @DisplayName("deve aceitar YES")
        void shouldAcceptYes() {
            var vote = new Vote(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), VoteOption.YES, OffsetDateTime.now());
            assertEquals(VoteOption.YES, vote.option());
        }

        @Test
        @DisplayName("deve aceitar NO")
        void shouldAcceptNo() {
            var vote = new Vote(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), VoteOption.NO, OffsetDateTime.now());
            assertEquals(VoteOption.NO, vote.option());
        }

        @Test
        @DisplayName("enum deve conter exatamente 2 valores")
        void shouldContainExactlyTwoValues() {
            assertEquals(2, VoteOption.values().length);
        }
    }
}
