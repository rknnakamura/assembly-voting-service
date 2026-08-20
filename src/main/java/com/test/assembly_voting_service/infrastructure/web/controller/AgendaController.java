package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.application.usecase.CreateAgendaUseCase;
import com.test.assembly_voting_service.application.usecase.ListAgendasUseCase;
import com.test.assembly_voting_service.application.usecase.command.CreateAgendaCommand;
import com.test.assembly_voting_service.infrastructure.web.dto.request.CreateAgendaRequest;
import com.test.assembly_voting_service.infrastructure.web.dto.response.AgendaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/agendas")
public class AgendaController {

    private final CreateAgendaUseCase createAgendaUseCase;
    private final ListAgendasUseCase listAgendasUseCase;

    public AgendaController(
            CreateAgendaUseCase createAgendaUseCase,
            ListAgendasUseCase listAgendasUseCase) {
        this.createAgendaUseCase = createAgendaUseCase;
        this.listAgendasUseCase = listAgendasUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgendaResponse create(@Valid @RequestBody CreateAgendaRequest request) {
        var agenda = createAgendaUseCase.execute(new CreateAgendaCommand(request.title()));
        return new AgendaResponse(agenda.id(), agenda.title(), agenda.createdAt());
    }

    @GetMapping
    public List<AgendaResponse> listAll() {
        return listAgendasUseCase.execute().stream()
                .map(agenda -> new AgendaResponse(agenda.id(), agenda.title(), agenda.createdAt()))
                .toList();
    }
}
