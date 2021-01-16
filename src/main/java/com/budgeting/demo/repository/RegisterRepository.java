package com.budgeting.demo.repository;

import com.budgeting.demo.model.Register;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class RegisterRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save (Register register) {
        entityManager.persist(register);
    }

    @Transactional
    public void checkAll() {
        Query q = this.entityManager.createQuery("select r from Register r");
        q.getResultList().forEach(r -> {
            System.out.println(((Register)r).getName());
        });
    }

}
