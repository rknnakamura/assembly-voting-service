package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.application.usecase.ListAgendasUseCase;
import com.test.assembly_voting_service.domain.model.agenda.Agenda;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppControllerTest {

    @Mock
    private ListAgendasUseCase listAgendasUseCase;

    @InjectMocks
    private AppController controller;

    @BeforeEach
    void setUp() {
        // Simula o contexto da requisição web para que o ServletUriComponentsBuilder funcione.
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(8080);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    @DisplayName("Deve retornar o formulário de cadastro de pauta com URL absoluta e body preenchido")
    void shouldReturnAgendaForm() {
        var response = controller.getAgendaForm();

        assertNotNull(response);
        assertEquals("FORMULARIO", response.type());
        assertEquals("Cadastrar pauta", response.title());
        
        assertEquals(1, response.items().size());
        assertEquals("INPUT_TEXTO", response.items().getFirst().type());
        assertEquals("title", response.items().getFirst().id());

        assertNotNull(response.actionButton());
        assertEquals("Cadastrar", response.actionButton().text());
        assertEquals("http://localhost:8080/api/v1/agendas", response.actionButton().url());
        assertTrue(response.actionButton().body().containsKey("title"));
    }

    @Test
    @DisplayName("Deve retornar a seleção de pautas para gerenciamento com URL absoluta")
    void shouldReturnManagementAgendaSelection() {
        var agenda = Agenda.create("Pauta Teste Gerenciamento");
        when(listAgendasUseCase.execute()).thenReturn(List.of(agenda));

        var response = controller.getManagementAgendaSelection();

        assertNotNull(response);
        assertEquals("SELECAO", response.type());
        assertEquals("Pautas", response.title());
        
        assertEquals(1, response.items().size());
        var item = response.items().getFirst();
        assertEquals("Pauta Teste Gerenciamento", item.text());
        assertEquals("http://localhost:8080/app/form/agendas/" + agenda.id() + "/voting-session", item.url());
        
        verify(listAgendasUseCase).execute();
    }

    @Test
    @DisplayName("Deve retornar o formulário de abrir sessão com URL absoluta e duration pré-preenchido")
    void shouldReturnOpenSessionForm() {
        var agendaId = UUID.randomUUID();

        var response = controller.getOpenSessionForm(agendaId);

        assertNotNull(response);
        assertEquals("FORMULARIO", response.type());
        assertEquals("Abrir sessão", response.title());
        
        assertEquals(1, response.items().size());
        assertEquals("INPUT_NUMERO", response.items().getFirst().type());
        assertEquals("durationInMinutes", response.items().getFirst().id());

        assertNotNull(response.actionButton());
        assertEquals("Abrir sessão", response.actionButton().text());
        assertEquals("http://localhost:8080/api/v1/agendas/" + agendaId + "/voting-session", response.actionButton().url());
        assertEquals(1, response.actionButton().body().get("durationInMinutes"));
    }

    @Test
    @DisplayName("Deve retornar a seleção de pautas para votação com URL absoluta")
    void shouldReturnVotingAgendaSelection() {
        var agenda = Agenda.create("Pauta Teste Votação");
        when(listAgendasUseCase.execute()).thenReturn(List.of(agenda));

        var response = controller.getVotingAgendaSelection();

        assertNotNull(response);
        assertEquals("SELECAO", response.type());
        assertEquals("Escolha uma pauta para votar", response.title());
        
        assertEquals(1, response.items().size());
        var item = response.items().getFirst();
        assertEquals("Pauta Teste Votação", item.text());
        assertEquals("http://localhost:8080/app/select/agendas/" + agenda.id() + "/voting", item.url());
        
        verify(listAgendasUseCase).execute();
    }

    @Test
    @DisplayName("Deve retornar os itens de seleção de voto (SIM/NAO) preenchidos e apontando para REST API")
    void shouldReturnVoteSelectionOptions() {
        var agendaId = UUID.randomUUID();

        var response = controller.getVoteSelection(agendaId);

        assertNotNull(response);
        assertEquals("SELECAO", response.type());
        assertEquals("Votação", response.title());
        
        assertEquals(2, response.items().size());
        
        var itemSim = response.items().getFirst();
        assertEquals("SIM", itemSim.text());
        assertEquals("http://localhost:8080/api/v1/agendas/" + agendaId + "/votes", itemSim.url());
        assertEquals("YES", itemSim.body().get("option"));
        
        var itemNao = response.items().get(1);
        assertEquals("NÃO", itemNao.text());
        assertEquals("http://localhost:8080/api/v1/agendas/" + agendaId + "/votes", itemNao.url());
        assertEquals("NO", itemNao.body().get("option"));
    }
}
