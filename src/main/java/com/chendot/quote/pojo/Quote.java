package com.chendot.quote.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @JsonIgnoreProperties类注解，json序列化时将java bean中的一些属性忽略掉，序列化和反序列化都受影响。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 将这个注解写在类上之后，就会忽略类中不存在的字段
@Component
public class Quote {
    private String type;
    private Value value;

    public Quote() {

    }
}
