package com.sz.ZhiHu.service;

import com.sz.ZhiHu.po.Answer;
import com.sz.ZhiHu.vo.AnswerVo;

import java.util.List;

public interface AnswerService {
    Answer insertAnswer(Answer answer);
    Answer queryRandomAnswerByQuestionId(Long questionId);
    List<Answer> getRandomAnswer(Integer sum);
    AnswerVo getAnswerById(Long id);
    AnswerVo getNextAnswer(Long questionId, Long supportSum);
    AnswerVo getPreviousAnswer(Long questionId,Long id);
    List<AnswerVo> getNextTreeAnswer(Long questionId,Long id);
}
