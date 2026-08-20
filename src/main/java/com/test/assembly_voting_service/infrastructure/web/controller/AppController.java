package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.application.usecase.ListAgendasUseCase;
import com.test.assembly_voting_service.infrastructure.web.dto.response.app.FormResponse;
import com.test.assembly_voting_service.infrastructure.web.dto.response.app.FormResponse.ActionButton;
import com.test.assembly_voting_service.infrastructure.web.dto.response.app.FormResponse.FormItem;
import com.test.assembly_voting_service.infrastructure.web.dto.response.app.SelectionResponse;
import com.test.assembly_voting_service.infrastructure.web.dto.response.app.SelectionResponse.SelectionItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Tag(name = "Mobile App (BFF)", description = "Endpoints para Server-Driven UI do aplicativo móvel")
@RestController
@RequestMapping("/app")
public class AppController {

    private final ListAgendasUseCase listAgendasUseCase;

    public AppController(ListAgendasUseCase listAgendasUseCase) {
        this.listAgendasUseCase = listAgendasUseCase;
    }

    private String getBaseUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }

    @Operation(summary = "Formulário de nova pauta", description = "Retorna o layout para o formulário de criação de pauta")
    @GetMapping("/form/agendas")
    public FormResponse getAgendaForm() {
        Map<String, Object> bodyTemplate = new HashMap<>();
        bodyTemplate.put("title", "");

        return new FormResponse(
                "FORMULARIO",
                "Cadastrar pauta",
                List.of(new FormItem("INPUT_TEXTO", "title", "Título da pauta", "")),
                new ActionButton("Cadastrar", getBaseUrl() + "/api/v1/agendas", bodyTemplate)
        );
    }

    @Operation(summary = "Seleção de pautas (Gerenciamento)", description = "Retorna o layout de seleção de pautas para abertura de sessão")
    @GetMapping("/select/agendas/management")
    public SelectionResponse getManagementAgendaSelection() {
        var items = listAgendasUseCase.execute().stream()
                .map(agenda -> new SelectionItem(
                        agenda.title(),
                        getBaseUrl() + "/app/form/agendas/" + agenda.id() + "/voting-session",
                        Map.of()))
                .toList();

        return new SelectionResponse("SELECAO", "Pautas", items);
    }

    @Operation(summary = "Formulário de abertura de sessão", description = "Retorna o layout para definir a duração e abrir uma sessão de votação")
    @PostMapping("/form/agendas/{agendaId}/voting-session")
    public FormResponse getOpenSessionForm(
            @Parameter(description = "ID da pauta") @PathVariable UUID agendaId) {
        Map<String, Object> bodyTemplate = new HashMap<>();
        bodyTemplate.put("durationInMinutes", 1);

        return new FormResponse(
                "FORMULARIO",
                "Abrir sessão",
                List.of(new FormItem("INPUT_NUMERO", "durationInMinutes", "Duração da sessão em minutos", 1)),
                new ActionButton("Abrir sessão", getBaseUrl() + "/api/v1/agendas/" + agendaId + "/voting-session", bodyTemplate)
        );
    }

    @Operation(summary = "Seleção de pautas (Votação)", description = "Retorna o layout de seleção de pautas disponíveis para o associado votar")
    @GetMapping("/select/agendas/voting")
    public SelectionResponse getVotingAgendaSelection() {
        var items = listAgendasUseCase.execute().stream()
                .map(agenda -> new SelectionItem(
                        agenda.title(),
                        getBaseUrl() + "/app/select/agendas/" + agenda.id() + "/voting",
                        Map.of()))
                .toList();

        return new SelectionResponse("SELECAO", "Escolha uma pauta para votar", items);
    }

    @Operation(summary = "Seleção de voto (Opções)", description = "Retorna o layout com os botões de opções de voto (Sim/Não) para uma pauta")
    @PostMapping("/select/agendas/{agendaId}/voting")
    public SelectionResponse getVoteSelection(
            @Parameter(description = "ID da pauta") @PathVariable UUID agendaId) {
        var items = List.of(
                new SelectionItem("SIM",
                        getBaseUrl() + "/api/v1/agendas/" + agendaId + "/votes",
                        Map.of("memberId", "8dc2dbe4-b5d5-4543-9be2-d7d336b99dc1", "option", "YES")),
                new SelectionItem("NÃO",
                        getBaseUrl() + "/api/v1/agendas/" + agendaId + "/votes",
                        Map.of("memberId", "8dc2dbe4-b5d5-4543-9be2-d7d336b99dc1", "option", "NO"))
        );

        return new SelectionResponse("SELECAO", "Votação", items);
    }
}
