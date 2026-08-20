package com.test.assembly_voting_service.infrastructure.persistence.adapter;

import com.test.assembly_voting_service.application.port.out.MemberRepository;
import com.test.assembly_voting_service.domain.model.member.Member;
import com.test.assembly_voting_service.infrastructure.persistence.repository.SpringDataMemberRepository;
import com.test.assembly_voting_service.infrastructure.persistence.mapper.MemberMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class MemberRepositoryAdapter implements MemberRepository {

    private final SpringDataMemberRepository repository;

    public MemberRepositoryAdapter(SpringDataMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Member> findById(UUID memberId) {
        return repository.findById(memberId)
                .map(MemberMapper::toDomain);
    }
}
