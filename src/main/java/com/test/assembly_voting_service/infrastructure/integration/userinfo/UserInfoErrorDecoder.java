package com.test.assembly_voting_service.infrastructure.integration.userinfo;

import com.test.assembly_voting_service.domain.exception.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class UserInfoErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new ResourceNotFoundException("Member CPF is invalid or not found in external service");
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
