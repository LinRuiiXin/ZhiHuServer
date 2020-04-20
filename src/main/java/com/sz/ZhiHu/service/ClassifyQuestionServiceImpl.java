package com.sz.ZhiHu.service;

import com.sz.ZhiHu.dao.ClassifyQuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassifyQuestionServiceImpl implements ClassifyQuestionService {
    @Autowired
    ClassifyQuestionDao classifyQuestionDao;
    @Override
    public void setQuestionType(Long questionId, Long classifyId) {
        classifyQuestionDao.insertClassifyQuestion(questionId,classifyId);
    }

    @Override
    public List<Long> getQuestionType(Long questionId) {
        return classifyQuestionDao.getQuestionType(questionId);
    }

    @Override
    public List<Long> getRandomQuestion(Long typeId, Integer sum) {
        return classifyQuestionDao.getRandomQuestionId(typeId,sum);
    }
}
