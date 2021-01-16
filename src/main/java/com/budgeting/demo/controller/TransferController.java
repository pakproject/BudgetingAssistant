package com.budgeting.demo.controller;

import com.budgeting.demo.model.RechargeParam;
import com.budgeting.demo.model.Register;
import com.budgeting.demo.repository.RegisterRepository;
import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

@RestController
public class TransferController {
    RegisterRepository repository;
    private Gson gson;

    public TransferController(RegisterRepository repository) {
        this.repository = repository;
        gson = new GsonBuilder().create();
        initializeIfEmpty();
    }

    private void initializeIfEmpty () {
        if (repository.getAll().size() == 0) {
            repository.persist(new Register("Wallet", 1000.0));
            repository.persist(new Register("Savings", 5000.0));
            repository.persist(new Register("Insurance policy", 0.0));
            repository.persist(new Register("Food expenses", 0.0));
        }
    }

    @PostMapping("/getAll")
    public ResponseEntity getAll() {
        repository.getAll().forEach(r -> {
            System.out.printf("Name: %s, amount: %f%n", r.getName(), r.getAmount());
        });
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recharge")
    public ResponseEntity recharge(@RequestBody String body) {
        RechargeParam rechargeParam = gson.fromJson(body, RechargeParam.class);
        Register register = repository.read(rechargeParam.getName());
        if (register == null || rechargeParam.getAmount() < 0) {
            return ResponseEntity.badRequest().build();
        }
        register.setAmount(register.getAmount() + rechargeParam.getAmount());
        repository.merge(register);
        return ResponseEntity.ok().build();
    }

}
