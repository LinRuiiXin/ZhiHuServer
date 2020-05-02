package com.sz.ZhiHu.service;

import com.sz.ZhiHu.dto.SimpleDto;
import com.sz.ZhiHu.po.Answer;
import com.sz.ZhiHu.vo.AnswerVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AnswerService {
    SimpleDto insertAnswer(MultipartFile file, Answer answer);
    Answer queryRandomAnswerByQuestionId(Long questionId);
    List<Answer> getRandomAnswer(Integer sum);
    AnswerVo getAnswerById(Long userId,Long id) throws ExecutionException, InterruptedException;
    AnswerVo getNextAnswer(Long userId,Long questionId, Long supportSum) throws ExecutionException, InterruptedException;
    AnswerVo getPreviousAnswer(Long userId,Long questionId,Long id) throws ExecutionException, InterruptedException;
    List<AnswerVo> getNextTreeAnswer(Long userId,Long questionId,Long id) throws ExecutionException, InterruptedException;
    boolean userHasAttention(Long userId,Long answererId);
    boolean userHasSupport(Long userId,Long answerId);
}
