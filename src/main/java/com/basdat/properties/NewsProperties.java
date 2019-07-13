package com.basdat.properties;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NewsProperties {
    private final String newsApiGatewayUrl;
}
