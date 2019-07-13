package com.basdat.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/*
    References : https://newsapi.org/docs/endpoints/everything
 */
@Data
@Builder
@AllArgsConstructor
public class NewsRequestEverythingParameters {

    private String q;
    private String sources;
    private String domains;
    private String excludeDomains;
    private String from;
    private String to;
    private String language;
    private String sortBy;

    private Integer pageSize;
    private Integer page;
}
