package com.iquote.restapi.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Exchange {

    private @Id Long id;
    private String exchangeId, name, assetBase, assetQuote;
    private double price_precision;
    private int size_precision;

    public Exchange() {}

    public Exchange(String exchangId, String assetBase, String assetQuote) {
        this.exchangeId = exchangId;
        this.assetBase = assetBase;
        this.assetQuote = assetQuote;
    }
}
