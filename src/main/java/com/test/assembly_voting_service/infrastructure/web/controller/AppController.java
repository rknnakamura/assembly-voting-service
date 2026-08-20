package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.application.usecase.ListAgendasUseCase;
import com.test.assembly_voting_service.infrastructure.web.dto.response.app.FormResponse;
import com.test.assembly_voting_service.infrastructure.web.dto.response.app.FormResponse.ActionButton;
import com.test.assembly_voting_service.infrastructure.web.dto.response.app.FormResponse.FormItem;
import com.test.assembly_voting_service.infrastructure.web.dto.response.app.SelectionResponse;
import com.test.assembly_voting_service.infrastructure.web.dto.response.app.SelectionResponse.SelectionItem;
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

    @PostMapping("/form/agendas/{agendaId}/voting-session")
    public FormResponse getOpenSessionForm(@PathVariable UUID agendaId) {
        Map<String, Object> bodyTemplate = new HashMap<>();
        bodyTemplate.put("durationInMinutes", 1);

        return new FormResponse(
                "FORMULARIO",
                "Abrir sessão",
                List.of(new FormItem("INPUT_NUMERO", "durationInMinutes", "Duração da sessão em minutos", 1)),
                new ActionButton("Abrir sessão", getBaseUrl() + "/api/v1/agendas/" + agendaId + "/voting-session", bodyTemplate)
        );
    }

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

    @PostMapping("/select/agendas/{agendaId}/voting")
    public SelectionResponse getVoteSelection(@PathVariable UUID agendaId) {
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
