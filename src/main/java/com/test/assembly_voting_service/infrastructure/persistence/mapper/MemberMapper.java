package com.test.assembly_voting_service.infrastructure.persistence.mapper;

import com.test.assembly_voting_service.domain.model.member.Member;
import com.test.assembly_voting_service.infrastructure.persistence.entity.MemberEntity;

public class MemberMapper {

    public static Member toDomain(MemberEntity entity) {
        return new Member(entity.getId(), entity.getCpf());
    }
}
