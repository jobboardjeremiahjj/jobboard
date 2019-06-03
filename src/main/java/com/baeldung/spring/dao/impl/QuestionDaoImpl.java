/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.spring.dao.impl;

import com.baeldung.spring.dao.QuestionDao;
import com.baeldung.spring.entity.Company;
import com.baeldung.spring.entity.Question;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author james
 */
@Repository
@Transactional
@Service
public class QuestionDaoImpl implements QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createQuestion(Question question) {
        try {
            entityManager.persist(question);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Question> findAll() {
        Query query = entityManager.createQuery("SELECT q FROM Question q");
        List<Question> querylist = query.getResultList();
        return querylist;
    }

    @Override
    public List<Question> createXam(int qsnsNo) {
        Query query = entityManager.createQuery("SELECT q FROM Question q ORDER BY RAND()");
        query.setMaxResults(qsnsNo);
        List<Question> querylist = query.getResultList();
        return querylist;
    }

}
