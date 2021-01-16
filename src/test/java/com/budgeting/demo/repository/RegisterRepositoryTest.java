package com.budgeting.demo.repository;

import com.budgeting.demo.model.Register;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(RegisterRepository.class)
@Sql({"/insertTest.sql"})
public class RegisterRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    RegisterRepository registerRepository;

    @Test
    public void givenValueInserted_valueAvailable() {
        registerRepository.save(new Register("aaa", 10.0));
        assertEquals(10.0, registerRepository.read("aaa").getAmount());
    }

    @Test
    public void givenInitialization_valuesInserted() {
        List<Register> all = registerRepository.getAll();
        assertEquals(4, all.size());
        assertEquals("Wallet", all.get(0).getName());
        assertEquals(1000.0, all.get(0).getAmount());
    }

}