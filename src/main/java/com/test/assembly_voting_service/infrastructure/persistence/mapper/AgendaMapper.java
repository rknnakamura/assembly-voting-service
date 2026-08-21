package com.test.assembly_voting_service.infrastructure.persistence.mapper;

import com.test.assembly_voting_service.domain.model.agenda.Agenda;
import com.test.assembly_voting_service.infrastructure.persistence.entity.AgendaEntity;

public class AgendaMapper {

    public static AgendaEntity toEntity(Agenda domain) {
        return AgendaEntity.builder()
                .id(domain.id())
                .title(domain.title())
                .createdAt(domain.createdAt())
                .build();
    }

    public static Agenda toDomain(AgendaEntity entity) {
        return new Agenda(entity.getId(), entity.getTitle(), entity.getCreatedAt());
    }
}
