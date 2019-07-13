package com.basdat.config;

import com.basdat.config.rest.NewsRestTemplateBuilder;
import com.basdat.properties.NewsProperties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
public class NewsApiGatewayConfiguration {

    @Bean
    public NewsProperties newsProperties(@Value("${news.api.gateway.url}") final String newsApiGatewayUrl) {
        return NewsProperties.builder()
            .newsApiGatewayUrl(newsApiGatewayUrl)
            .build();
    }

    @Bean
    @Qualifier("newsRestTemplate")
    public RestTemplate newsRestTemplate() {
        return new NewsRestTemplateBuilder().build();
    }
}
