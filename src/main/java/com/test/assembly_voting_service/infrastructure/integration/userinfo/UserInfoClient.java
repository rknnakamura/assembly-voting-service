package com.test.assembly_voting_service.infrastructure.integration.userinfo;

import feign.Param;
import feign.RequestLine;

public interface UserInfoClient {

    @RequestLine("GET /users/{cpf}")
    UserInfoResponse checkEligibility(@Param("cpf") String cpf);
}
