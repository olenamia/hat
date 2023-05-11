package com.mialyk.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyValidator {

    @Value("${hat.modification-api-key}")
    private String modificationApiKey;

    public void validate(String apiKey) {
        if (apiKey.equals(modificationApiKey)) {
            throw new AccessDeniedException("Invalid API key");
        }
    }
}
