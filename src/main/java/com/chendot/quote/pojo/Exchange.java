package com.chendot.quote.pojo;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Exchange {

    private @Id Long id;
    private String exchangeId, assetBase, assetQuote;
    private double price_precision;
    private int size_precision;

    public Exchange(String exchangId, String assetBase, String assetQuote) {
        this.exchangeId = exchangId;
        this.assetBase = assetBase;
        this.assetQuote = assetQuote;
    }
}
