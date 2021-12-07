package com.iquote.batch.model;

import lombok.Data;

/**
 * 测试实体类
 */
@Data
public class Person {
    private String lastName;
    private String firstName;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
