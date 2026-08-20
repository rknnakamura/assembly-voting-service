package com.test.assembly_voting_service.infrastructure.integration.userinfo;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserInfoClientConfiguration {

    @Bean
    public UserInfoClient userInfoClient(@Value("${integration.user-info.url}") String url) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(new UserInfoErrorDecoder())
                .target(UserInfoClient.class, url);
    }
}
