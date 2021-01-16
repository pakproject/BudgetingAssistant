package com.budgeting.demo.controller;

import com.budgeting.demo.model.RechargeParam;
import com.budgeting.demo.model.Register;
import com.budgeting.demo.model.TransferParam;
import com.budgeting.demo.repository.RegisterRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransferController {
    RegisterRepository repository;
    private Gson gson;

    public TransferController(RegisterRepository repository) {
        this.repository = repository;
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @PostMapping("/checkAll")
    public ResponseEntity getAll() {
        List<Register> allRegisers = repository.getAll();
        allRegisers.forEach(r -> {
            System.out.printf("Name: %s, amount: %f%n", r.getName(), r.getAmount());
        });
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recharge")
    public ResponseEntity recharge(@RequestBody String body) {
        RechargeParam rechargeParam;
        try {
            rechargeParam = gson.fromJson(body, RechargeParam.class);
        } catch (JsonSyntaxException e) {
            return ResponseEntity.badRequest().build();
        }
        Register register = repository.read(rechargeParam.getName());
        if (register == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            register.add(rechargeParam.getAmount());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        repository.merge(register);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity transfer (@RequestBody String body) {
        TransferParam transferParam;
        try {
            transferParam = gson.fromJson(body, TransferParam.class);
        } catch (JsonSyntaxException e) {
            return ResponseEntity.badRequest().build();
        }
        Register source = repository.read(transferParam.getSource());
        Register target = repository.read(transferParam.getTarget());
        if (source == null || target == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            source.subtract(transferParam.getAmount());
            target.add(transferParam.getAmount());
            repository.merge(source, target);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
