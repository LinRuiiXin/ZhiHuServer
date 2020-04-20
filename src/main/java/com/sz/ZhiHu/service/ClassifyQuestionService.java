package com.sz.ZhiHu.service;

import java.util.List;

public interface ClassifyQuestionService {
    void setQuestionType(Long questionId,Long classifyId);
    List<Long> getQuestionType(Long questionId);
    List<Long> getRandomQuestion(Long typeId,Integer sum);
}
