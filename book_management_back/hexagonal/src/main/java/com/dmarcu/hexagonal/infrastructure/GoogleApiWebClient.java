package com.dmarcu.hexagonal.infrastructure;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GoogleApiWebClient {

    private static final String GOOGLE_BOOOKS_BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    @Getter
    private WebClient webClient;

    public GoogleApiWebClient(){
        this.webClient = WebClient.builder().baseUrl(GOOGLE_BOOOKS_BASE_URL).build();
    }
}
