package com.test.assembly_voting_service.infrastructure.persistence.mapper;

import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import com.test.assembly_voting_service.infrastructure.persistence.entity.VotingSessionEntity;

public class VotingSessionMapper {

    public static VotingSessionEntity toEntity(VotingSession domain) {
        return VotingSessionEntity.builder()
                .id(domain.id())
                .agendaId(domain.agendaId())
                .startedAt(domain.startedAt())
                .endedAt(domain.endedAt())
                .build();
    }

    public static VotingSession toDomain(VotingSessionEntity entity) {
        return new VotingSession(entity.getId(), entity.getAgendaId(), entity.getStartedAt(), entity.getEndedAt());
    }
}
