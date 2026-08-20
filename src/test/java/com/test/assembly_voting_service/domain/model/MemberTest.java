package com.test.assembly_voting_service.domain.model;

import com.test.assembly_voting_service.domain.model.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Nested
    @DisplayName("Criação de Member")
    class Creation {

        @Test
        @DisplayName("deve criar membro com todos os campos válidos")
        void shouldCreateWithValidFields() {
            var id = UUID.randomUUID();
            var cpf = "12345678901";

            var member = new Member(id, cpf);

            assertEquals(id, member.id());
            assertEquals(cpf, member.cpf());
        }

        @Test
        @DisplayName("deve lançar exceção quando id for nulo")
        void shouldThrowWhenIdIsNull() {
            var ex = assertThrows(NullPointerException.class,
                    () -> new Member(null, "12345678901"));
            assertEquals("id must not be null", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando cpf for nulo")
        void shouldThrowWhenCpfIsNull() {
            var ex = assertThrows(NullPointerException.class,
                    () -> new Member(UUID.randomUUID(), null));
            assertEquals("cpf must not be null", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando cpf for vazio")
        void shouldThrowWhenCpfIsEmpty() {
            var ex = assertThrows(IllegalArgumentException.class,
                    () -> new Member(UUID.randomUUID(), ""));
            assertEquals("cpf must not be blank", ex.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando cpf contiver apenas espaços")
        void shouldThrowWhenCpfIsBlank() {
            var ex = assertThrows(IllegalArgumentException.class,
                    () -> new Member(UUID.randomUUID(), "   "));
            assertEquals("cpf must not be blank", ex.getMessage());
        }
    }
}
