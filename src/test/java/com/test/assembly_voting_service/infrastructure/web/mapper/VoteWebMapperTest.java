package com.test.assembly_voting_service.infrastructure.web.mapper;

import com.test.assembly_voting_service.domain.model.vote.Vote;
import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import com.test.assembly_voting_service.infrastructure.web.dto.request.CastVoteRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VoteWebMapperTest {

    @Test
    void shouldMapToCommand() {
        var agendaId = UUID.randomUUID();
        var memberId = UUID.randomUUID();
        var request = new CastVoteRequest(memberId, VoteOption.YES);

        var command = VoteWebMapper.toCommand(agendaId, request);

        assertNotNull(command);
        assertEquals(agendaId, command.agendaId());
        assertEquals(memberId, command.memberId());
        assertEquals(VoteOption.YES, command.option());
    }

    @Test
    void shouldMapToResponse() {
        var agendaId = UUID.randomUUID();
        var memberId = UUID.randomUUID();
        var vote = Vote.create(agendaId, memberId, VoteOption.NO);

        var response = VoteWebMapper.toResponse(vote);

        assertNotNull(response);
        assertEquals(vote.id(), response.id());
        assertEquals(agendaId, response.agendaId());
        assertEquals(memberId, response.memberId());
        assertEquals(VoteOption.NO, response.option());
        assertEquals(vote.createdAt(), response.createdAt());
    }
}
