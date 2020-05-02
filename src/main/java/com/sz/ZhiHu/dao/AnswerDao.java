package com.sz.ZhiHu.dao;

import com.sz.ZhiHu.po.Answer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerDao {
    void insertAnswer(Answer answer);
    Answer queryRandomAnswerByQuestionId(Long questionId);
    List<Answer> getRandomAnswer(Integer sum);
    Answer queryAnswerById(Long id);
    List<Long> getAnswerOrder(Long questionId);
    Integer userHasAttention(@Param("userId") Long userId,@Param("answererId") Long answererId);
    Integer userHasSupport(@Param("userId") Long userId,@Param("answerId") Long answerId);
}
