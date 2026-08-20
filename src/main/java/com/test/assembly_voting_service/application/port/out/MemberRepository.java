package com.test.assembly_voting_service.application.port.out;

import com.test.assembly_voting_service.domain.model.member.Member;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    Optional<Member> findById(UUID memberId);
}
