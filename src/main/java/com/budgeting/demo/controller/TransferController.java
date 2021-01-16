package com.budgeting.demo.controller;

import com.budgeting.demo.model.Register;
import com.budgeting.demo.repository.RegisterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {
    RegisterRepository repository;

    public TransferController(RegisterRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/checkController")
    public ResponseEntity checkController(@RequestBody String message) {
        //System.out.println(message);
        repository.save(new Register(message));
        repository.checkAll();
        return ResponseEntity.ok().build();
    }

}
