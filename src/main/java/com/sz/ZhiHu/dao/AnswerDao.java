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
    Answer queryNextAnswer(@Param("questionId") Long questionId,@Param("supportSum") Long supportSum);
    Answer queryPreviousAnswer(@Param("questionId") Long questionId,@Param("supportSum") Long supportSum);
    List<Answer> queryNextTreeAnswer(@Param("questionId") Long questionId,@Param("id") Long id,@Param("supportSum") Long supportSum);
    List<Long> getAnswerOrder(Long questionId);
}
