package com.test.assembly_voting_service.infrastructure.persistence.adapter;

import com.test.assembly_voting_service.application.port.out.AgendaRepository;
import com.test.assembly_voting_service.domain.model.agenda.Agenda;
import com.test.assembly_voting_service.infrastructure.persistence.entity.AgendaEntity;
import com.test.assembly_voting_service.infrastructure.persistence.repository.SpringDataAgendaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AgendaRepositoryAdapter implements AgendaRepository {

    private final SpringDataAgendaRepository repository;

    public AgendaRepositoryAdapter(SpringDataAgendaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Agenda save(Agenda agenda) {
        var entity = AgendaEntity.builder()
                .id(agenda.id())
                .title(agenda.title())
                .createdAt(agenda.createdAt())
                .build();
        var saved = repository.save(entity);
        return new Agenda(saved.getId(), saved.getTitle(), saved.getCreatedAt());
    }

    @Override
    public List<Agenda> findAll() {
        return repository.findAll().stream()
                .map(entity -> new Agenda(entity.getId(), entity.getTitle(), entity.getCreatedAt()))
                .toList();
    }

    @Override
    public Optional<Agenda> findById(UUID id) {
        return repository.findById(id)
                .map(entity -> new Agenda(entity.getId(), entity.getTitle(), entity.getCreatedAt()));
    }
}
