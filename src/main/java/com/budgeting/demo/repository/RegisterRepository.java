package com.budgeting.demo.repository;

import com.budgeting.demo.model.Register;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
public class RegisterRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void persist(Register register) {
        entityManager.persist(register);
    }

    @Transactional
    public void merge (Register register) {
        entityManager.merge(register);
        entityManager.flush();
    }

    @Transactional
    public Register read(String name) throws NoResultException {
        TypedQuery<Register> query = this.entityManager.createQuery("SELECT r FROM Register r WHERE r.name = ?1", Register.class);
        query.setParameter(1, name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public List<Register> getAll() {
        return this.entityManager.createQuery("select r from Register r", Register.class).getResultList();
    }

}
