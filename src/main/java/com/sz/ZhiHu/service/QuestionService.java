package com.sz.ZhiHu.service;

import com.sz.ZhiHu.po.Question;
import com.sz.ZhiHu.vo.RecommendQuestionViewBean;

import java.util.List;

public interface QuestionService {
    Question addQuestion(Question question,String typeStr);
    List<RecommendQuestionViewBean> getRandomQuestion(Long userId);
    void subscribeQuestion(Long userId,Long questionId);
    void incrementAnswer(Long questionId);
    void disSubscribeQuestion(Long userId,Long questionId);
    String getQuestionTitle(Long questionId);
    void recordUserBrowse(Long questionId,Long userId);
    List<Long> getAnswerOrder(Long questionId);
    void updateQuestionOrder(Long questionId);
}
