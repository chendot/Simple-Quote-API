package com.chendot.quote.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Greeting {
    private long id;
    private String content;

    private String message;

    public Greeting() {

    }

    public Greeting(String message) {
        this.message = message;
    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
}