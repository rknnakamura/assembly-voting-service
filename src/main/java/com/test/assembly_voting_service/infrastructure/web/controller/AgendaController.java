package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.application.usecase.CastVoteUseCase;
import com.test.assembly_voting_service.application.usecase.CreateAgendaUseCase;
import com.test.assembly_voting_service.application.usecase.GetVotingResultUseCase;
import com.test.assembly_voting_service.application.usecase.ListAgendasUseCase;
import com.test.assembly_voting_service.application.usecase.OpenVotingSessionUseCase;
import com.test.assembly_voting_service.application.usecase.command.CastVoteCommand;
import com.test.assembly_voting_service.application.usecase.command.CreateAgendaCommand;
import com.test.assembly_voting_service.application.usecase.command.GetVotingResultQuery;
import com.test.assembly_voting_service.application.usecase.command.OpenVotingSessionCommand;
import com.test.assembly_voting_service.infrastructure.web.dto.request.CastVoteRequest;
import com.test.assembly_voting_service.infrastructure.web.dto.request.CreateAgendaRequest;
import com.test.assembly_voting_service.infrastructure.web.dto.request.OpenVotingSessionRequest;
import com.test.assembly_voting_service.infrastructure.web.dto.response.AgendaResponse;
import com.test.assembly_voting_service.infrastructure.web.dto.response.VoteResponse;
import com.test.assembly_voting_service.infrastructure.web.dto.response.VotingResultResponse;
import com.test.assembly_voting_service.infrastructure.web.dto.response.VotingSessionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agendas")
public class AgendaController {

    private final CreateAgendaUseCase createAgendaUseCase;
    private final ListAgendasUseCase listAgendasUseCase;
    private final OpenVotingSessionUseCase openVotingSessionUseCase;
    private final CastVoteUseCase castVoteUseCase;
    private final GetVotingResultUseCase getVotingResultUseCase;

    public AgendaController(
            CreateAgendaUseCase createAgendaUseCase,
            ListAgendasUseCase listAgendasUseCase,
            OpenVotingSessionUseCase openVotingSessionUseCase,
            CastVoteUseCase castVoteUseCase,
            GetVotingResultUseCase getVotingResultUseCase) {
        this.createAgendaUseCase = createAgendaUseCase;
        this.listAgendasUseCase = listAgendasUseCase;
        this.openVotingSessionUseCase = openVotingSessionUseCase;
        this.castVoteUseCase = castVoteUseCase;
        this.getVotingResultUseCase = getVotingResultUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgendaResponse create(@RequestBody CreateAgendaRequest request) {
        var agenda = createAgendaUseCase.execute(new CreateAgendaCommand(request.title()));
        return new AgendaResponse(agenda.id(), agenda.title(), agenda.createdAt());
    }

    @GetMapping
    public List<AgendaResponse> listAll() {
        return listAgendasUseCase.execute().stream()
                .map(agenda -> new AgendaResponse(agenda.id(), agenda.title(), agenda.createdAt()))
                .toList();
    }

    @PostMapping("/{agendaId}/voting-session")
    @ResponseStatus(HttpStatus.CREATED)
    public VotingSessionResponse openVotingSession(
            @PathVariable UUID agendaId,
            @RequestBody OpenVotingSessionRequest request) {
        var session = openVotingSessionUseCase.execute(new OpenVotingSessionCommand(agendaId, request.durationInMinutes()));
        return new VotingSessionResponse(session.id(), session.agendaId(), session.startedAt(), session.endedAt());
    }

    @PostMapping("/{agendaId}/votes")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteResponse castVote(
            @PathVariable UUID agendaId,
            @RequestBody CastVoteRequest request) {
        var vote = castVoteUseCase.execute(new CastVoteCommand(agendaId, request.memberId(), request.option()));
        return new VoteResponse(vote.id(), vote.agendaId(), vote.memberId(), vote.option(), vote.createdAt());
    }

    @GetMapping("/{agendaId}/result")
    public VotingResultResponse getResult(@PathVariable UUID agendaId) {
        // TODO: retornar dados consolidados quando GetVotingResultUseCase for implementado
        getVotingResultUseCase.execute(new GetVotingResultQuery(agendaId));
        return new VotingResultResponse(agendaId, 0, 0, 0);
    }
}
