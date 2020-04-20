package com.sz.ZhiHu.service;

import com.sz.ZhiHu.dao.QuestionAttentionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionAttentionServiceImpl implements QuestionAttentionService {
    @Autowired
    QuestionAttentionDao questionAttentionDao;
    @Override
    public void addNewAttention(Long userId, Long questionId) {
        questionAttentionDao.addNewAttention(userId,questionId);
    }

    @Override
    public Boolean hasAttention(Long userId, Long questionId) {
        return questionAttentionDao.getIdByQuestionIdUserId(questionId,userId)==null?false:true;
    }

    @Override
    public void removeAttention(Long userId, Long questionId) {
        questionAttentionDao.removeAttention(userId,questionId);
    }
}
