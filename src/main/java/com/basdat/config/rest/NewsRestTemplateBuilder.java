package com.basdat.config.rest;

import org.springframework.web.client.RestTemplate;

public class NewsRestTemplateBuilder {

    public RestTemplate build() {
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
