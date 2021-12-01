package com.iquote.restapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "coinapi")
@Data
public class CoinAPIConfig {

    private String key;
    private String uriMetadataExchanges;
    private String uriQuotesCurrent;

}
