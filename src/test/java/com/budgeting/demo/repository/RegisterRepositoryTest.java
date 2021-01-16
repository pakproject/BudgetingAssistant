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

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({RegisterRepository.class, TestEntityManager.class})
@Sql({"/insertTest.sql"})
public class RegisterRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    RegisterRepository registerRepository;

    @Test
    public void givenInitialization_valuesInserted() {
        List<Register> all = registerRepository.getAll();
        assertEquals(4, all.size());
        assertEquals("Wallet", all.get(0).getName());
        assertEquals(1000.0, all.get(0).getAmount());
    }

    @Test
    public void givenMerge_mergeInvoked() throws Exception {
        Register register = registerRepository.read("Wallet");
        register.add(100.0);
        registerRepository.merge(register);
        assertEquals(1100.0, registerRepository.read("Wallet").getAmount());
    }

    @Test
    public void givenMergeTwo_mergeTwoInvoked() throws Exception {
        Register r1 = registerRepository.read("Wallet");
        Register r2 = registerRepository.read("Savings");
        r1.add(100.0);
        r2.subtract(100.0);
        registerRepository.merge(r1, r2);
        assertEquals(1100.0, registerRepository.read("Wallet").getAmount());
        assertEquals(4900.0, registerRepository.read("Savings").getAmount());
    }
}