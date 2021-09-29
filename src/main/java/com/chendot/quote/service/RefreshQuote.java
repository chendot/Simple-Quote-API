package com.chendot.quote.service;

import com.chendot.quote.pojo.Quote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Component
@Data
public class RefreshQuote {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Quote quote;

    private int count = 0;
    
    @Scheduled(cron = "0/3 * * * * ?")
    public void update() {
        count = count + 1;
		quote = restTemplate.getForObject("https://quoters.apps.pcfone.io/api/random", Quote.class);
    }
}
