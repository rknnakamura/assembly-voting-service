package com.test.assembly_voting_service.infrastructure.web.mapper;

import com.test.assembly_voting_service.application.usecase.command.CastVoteCommand;
import com.test.assembly_voting_service.domain.model.vote.Vote;
import com.test.assembly_voting_service.infrastructure.web.dto.request.CastVoteRequest;
import com.test.assembly_voting_service.infrastructure.web.dto.response.VoteResponse;

import java.util.UUID;

public final class VoteWebMapper {

    private VoteWebMapper() {
    }

    public static CastVoteCommand toCommand(UUID agendaId, CastVoteRequest request) {
        return new CastVoteCommand(agendaId, request.memberId(), request.option());
    }

    public static VoteResponse toResponse(Vote vote) {
        return new VoteResponse(vote.id(), vote.agendaId(), vote.memberId(), vote.option(), vote.createdAt());
    }
}
