package com.test.assembly_voting_service.infrastructure.persistence.mapper;

import com.test.assembly_voting_service.domain.model.vote.Vote;
import com.test.assembly_voting_service.infrastructure.persistence.entity.VoteEntity;

public class VoteMapper {

    public static VoteEntity toEntity(Vote domain) {
        return VoteEntity.builder()
                .id(domain.id())
                .agendaId(domain.agendaId())
                .memberId(domain.memberId())
                .option(domain.option())
                .createdAt(domain.createdAt())
                .build();
    }

    public static Vote toDomain(VoteEntity entity) {
        return new Vote(entity.getId(), entity.getAgendaId(), entity.getMemberId(), entity.getOption(), entity.getCreatedAt());
    }
}
