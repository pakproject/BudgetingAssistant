package com.budgeting.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Currency;

@Entity
public class Register {
    @Id
    @GeneratedValue
    private Long id;

    String name;

    public Register() {
    }

    public Register(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
