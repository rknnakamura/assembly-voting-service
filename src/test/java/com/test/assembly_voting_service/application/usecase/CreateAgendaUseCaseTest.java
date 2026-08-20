package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.domain.exception.DomainValidationException;
import com.test.assembly_voting_service.application.port.out.AgendaRepository;
import com.test.assembly_voting_service.application.usecase.command.CreateAgendaCommand;
import com.test.assembly_voting_service.domain.model.agenda.Agenda;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAgendaUseCaseTest {

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private CreateAgendaUseCase createAgendaUseCase;

    @Test
    @DisplayName("Deve criar e salvar uma agenda com sucesso")
    void shouldCreateAndSaveAgenda() {
        var command = new CreateAgendaCommand("Reforma do Estatuto");
        
        when(agendaRepository.save(any(Agenda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = createAgendaUseCase.execute(command);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(command.title(), result.title());
        assertNotNull(result.createdAt());
        
        verify(agendaRepository, times(1)).save(any(Agenda.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar agenda com título vazio")
    void shouldThrowExceptionWhenTitleIsBlank() {
        var command = new CreateAgendaCommand("   ");

        var ex = assertThrows(DomainValidationException.class, () -> createAgendaUseCase.execute(command));
        assertEquals("title must not be blank", ex.getMessage());
        
        verify(agendaRepository, never()).save(any());
    }
}
