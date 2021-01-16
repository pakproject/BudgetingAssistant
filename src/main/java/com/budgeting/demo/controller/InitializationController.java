package com.budgeting.demo.controller;

import com.budgeting.demo.model.Register;
import com.budgeting.demo.repository.RegisterRepository;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class InitializationController {
    RegisterRepository repository;

    public InitializationController(RegisterRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initializeIfEmpty () {
        if (repository.getAll().size() == 0) {
            repository.persist(new Register("Wallet", 1000.0));
            repository.persist(new Register("Savings", 5000.0));
            repository.persist(new Register("Insurance policy", 0.0));
            repository.persist(new Register("Food expenses", 0.0));
        }
    }
}
