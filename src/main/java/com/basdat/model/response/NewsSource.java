package com.basdat.model.response;

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
public class NewsSource {
    private final String id;
    private final String name;
}
