package com.budgeting.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Register {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    private Double amount;

    public Register() {
    }

    public Register(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void add(Double value) throws Exception {
        if (value < 0) {
            throw new Exception("Value incorrect");
        }
        this.amount += value;
    }

    public void subtract(Double value) throws Exception {
        if (value < 0 || this.amount < value) {
            throw new Exception("Value incorrect");
        }
        this.amount -= value;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
