package com.iquote.restapi.service;

import com.iquote.restapi.config.CoinAPIConfig;
import com.iquote.restapi.pojo.Quote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Component
@Data
public class RefreshQuote {

    private static final Logger log = LoggerFactory.getLogger(RefreshQuote.class);

    private final CoinAPIConfig coinAPIConfig;

    private RestTemplate restTemplate;
    private HttpHeaders header;
    private HttpEntity<String> requestEntity;
    private ResponseEntity<Quote> responseEntity;

    // private int count = 0;

    @Autowired
    public RefreshQuote(CoinAPIConfig cConfig) {
        this.coinAPIConfig = cConfig;
        header = new HttpHeaders();
        header.add("X-CoinAPI-Key", coinAPIConfig.getKey());
        requestEntity = new HttpEntity<String>(null, header);
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(cron = "5 * * * * ?")
    public void update() {
        // count = count + 1;
  
        try {
            responseEntity = restTemplate.exchange(coinAPIConfig.getUriQuotesCurrent(),
                    HttpMethod.GET, requestEntity, Quote.class);
            log.info(responseEntity.getBody().toString());
        } catch (Exception e) {
            log.error("Fail to get the price!!", e);
        }
    }
}
