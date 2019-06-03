/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.spring.dao;

import com.baeldung.spring.entity.Question;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author james
 */

public interface QuestionDao {
    
   
    void createQuestion(Question question);
    
    List<Question> findAll();
    
    List<Question> createXam(int qsnsNo);
}
