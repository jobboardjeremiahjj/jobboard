/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.spring.controller;

import com.baeldung.spring.dao.QuestionDao;
import com.baeldung.spring.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author james
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    
    @Autowired
    QuestionDao questionDao;
    
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String showQuestions(Model model) {
        model.addAttribute("questions", questionDao.findAll());
        System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwww" + questionDao.findAll().size());
        return "interviewquestions";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addQuestion(@RequestParam("question") String question, @RequestParam("optionA") String optionA,
			@RequestParam("optionB") String optionB,@RequestParam("optionC") String optionC ,@RequestParam("answer") String answer, Model model) {
        model.addAttribute("questions", questionDao.findAll());
        
        Question qsn = new Question();
        qsn.setQuestion(question);
        qsn.setOptionA(optionA);
        qsn.setOptionB(optionB);
        qsn.setOptionC(optionC);
        qsn.setAnswer(answer);
        
        questionDao.createQuestion(qsn);
        return "redirect:/question/get";
    }
    

}
