package com.basdat.controller;

import com.basdat.model.request.NewsRequestEverythingParameters;
import com.basdat.model.response.NewsResponseEverythingBody;
import com.basdat.service.NewsRestService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NewsApiController {

    private final NewsRestService newsRestService;

    @RequestMapping(value = "/")
    public String welcome() {
        return "<h1>Welcome</h1>";
    }

    @GetMapping(value = "/news")
    public NewsResponseEverythingBody request(NewsRequestEverythingParameters newsRequestEverythingParameters) {
        return newsRestService.getNewsApiEverythingRequest(newsRequestEverythingParameters);
    }

    @GetMapping(value = "/test")
    public NewsRequestEverythingParameters request1(NewsRequestEverythingParameters newsRequestEverythingParameters){
        return newsRequestEverythingParameters;
    }
}
