package com.basdat.service;

import com.basdat.model.request.NewsRequestEverythingParameters;
import com.basdat.model.response.NewsResponseEverythingBody;
import com.basdat.properties.NewsProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NewsRestService {

    @Value("${news.api.access.token}")
    private String accessToken;

    @Value("${news.save.json.path}")
    private String jsonSavePath;

    private final NewsProperties newsProperties;
    private final RestTemplate restTemplate;

    public NewsRestService(final NewsProperties newsProperties,
        @Qualifier("newsRestTemplate") final RestTemplate restTemplate) {
        this.newsProperties = newsProperties;
        this.restTemplate = restTemplate;
    }

    public NewsResponseEverythingBody getNewsApiEverythingRequest(
        final NewsRequestEverythingParameters newsRequestBody) {

        log.info("Start executing getNewsApiEverythingRequest with newsRequestBody={}", newsRequestBody);

        String url = newsProperties.getNewsApiGatewayUrl();
        url = url.concat("/everything");

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Accept", "application/json");
        final HttpEntity<?> entity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        MultiValueMap parameters = new LinkedMultiValueMap();
        final Map<String, String> tempMaps =
            objectMapper.convertValue(newsRequestBody, new TypeReference<Map<String, String>>() {});
        Map<String, String> maps = tempMaps
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey,
                entry -> {
                    try {
                        if (entry.getKey().equals("q")) {
                            return URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
                        } else {
                            return entry.getValue();
                        }
                    } catch (Exception exc) {
                        return null;
                    }
                }));
        parameters.setAll(tempMaps);

        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
            .queryParams(parameters);

        log.info("URL builder url={}", builder.toUriString());

        try {
            final HttpEntity<NewsResponseEverythingBody> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                NewsResponseEverythingBody.class);
            log.info("Done executing getNewsApiEverythingRequest");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonSavePath), response.getBody());
            log.info("Save JSON file to " + jsonSavePath);
            return response.getBody();
        } catch (final HttpStatusCodeException exc) {
            log.error("Failed executing postRequest to add caption with requestBody={}, statusCode={}, and response={}",
                newsRequestBody.toString(), exc.getStatusCode(), exc.getResponseBodyAsString(), exc);
            return null;
        } catch (Exception e) {
            log.error("error={}", e.getMessage());
            return null;
        }
    }
}
