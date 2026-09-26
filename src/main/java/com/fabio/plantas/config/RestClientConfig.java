package com.fabio.plantas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean
    public RestClient openMeteoRestClient(RestClient.Builder builder) {
        return builder.baseUrl("https://api.open-meteo.com/v1").build();
    }
}
