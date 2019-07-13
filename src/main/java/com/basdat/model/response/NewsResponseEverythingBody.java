package com.basdat.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Builder
@Getter
@ToString
public class NewsResponseEverythingBody {
    private final String status;
    private final int totalResults;

    private final List<NewsArticle> articles;
}
