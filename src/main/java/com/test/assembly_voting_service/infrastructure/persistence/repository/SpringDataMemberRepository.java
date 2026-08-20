package com.test.assembly_voting_service.infrastructure.persistence.repository;

import com.test.assembly_voting_service.infrastructure.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataMemberRepository extends JpaRepository<MemberEntity, UUID> {
}
