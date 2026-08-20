package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.application.usecase.CastVoteUseCase;
import com.test.assembly_voting_service.application.usecase.GetVotingResultUseCase;
import com.test.assembly_voting_service.application.usecase.command.CastVoteCommand;
import com.test.assembly_voting_service.application.usecase.command.GetVotingResultQuery;
import com.test.assembly_voting_service.infrastructure.web.dto.request.CastVoteRequest;
import com.test.assembly_voting_service.infrastructure.web.dto.response.VoteResponse;
import com.test.assembly_voting_service.infrastructure.web.dto.response.VotingResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/agendas/{agendaId}")
public class VoteController {

    private final CastVoteUseCase castVoteUseCase;
    private final GetVotingResultUseCase getVotingResultUseCase;

    public VoteController(
            CastVoteUseCase castVoteUseCase,
            GetVotingResultUseCase getVotingResultUseCase) {
        this.castVoteUseCase = castVoteUseCase;
        this.getVotingResultUseCase = getVotingResultUseCase;
    }

    @PostMapping("/votes")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteResponse castVote(
            @PathVariable UUID agendaId,
            @Valid @RequestBody CastVoteRequest request) {
        var vote = castVoteUseCase.execute(new CastVoteCommand(agendaId, request.memberId(), request.option()));
        return new VoteResponse(vote.id(), vote.agendaId(), vote.memberId(), vote.option(), vote.createdAt());
    }

    @GetMapping("/result")
    public VotingResultResponse getResult(@PathVariable UUID agendaId) {
        // TODO: retornar dados consolidados quando GetVotingResultUseCase for implementado por completo
        getVotingResultUseCase.execute(new GetVotingResultQuery(agendaId));
        return new VotingResultResponse(agendaId, 0, 0, 0);
    }
}
