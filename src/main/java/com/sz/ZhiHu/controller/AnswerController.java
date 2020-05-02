package com.sz.ZhiHu.controller;

import com.sz.ZhiHu.dto.SimpleDto;
import com.sz.ZhiHu.po.Answer;
import com.sz.ZhiHu.service.AnswerService;
import com.sz.ZhiHu.vo.AnswerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/Answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;
    @PostMapping
    public SimpleDto addAnswer(MultipartHttpServletRequest request, @RequestParam Long questionId,@RequestParam Long userId,@RequestParam Integer contentType,@RequestParam String content,@RequestParam String thumbnail){
        MultipartFile answerFile = request.getFile("answer");
        if(answerFile != null){
            SimpleDto simpleDto = answerService.insertAnswer(answerFile,new Answer(questionId,userId,content,contentType,contentType == 1 ? null : thumbnail));
            return simpleDto;
        }else{
            return new SimpleDto(false,"回答不能为空,",null);
        }
    }
    @GetMapping("/{userId}/{id}")
    public SimpleDto getAnswerById(@PathVariable Long userId,@PathVariable Long id) throws ExecutionException, InterruptedException {
        AnswerVo answerById = answerService.getAnswerById(userId,id);
        return answerById.getAnswer() == null ? new SimpleDto(false,"此回答已被删除",null) : new SimpleDto(true,null,answerById);
    }
    @GetMapping("/Next/{userId}/{questionId}/{id}")
    public SimpleDto getNextAnswer(@PathVariable Long userId,@PathVariable Long questionId,@PathVariable Long id) throws ExecutionException, InterruptedException {
        AnswerVo nextAnswer = answerService.getNextAnswer(userId,questionId, id);
        return nextAnswer == null ? new SimpleDto(false,"没有更多内容了",null) : new SimpleDto(true,null,nextAnswer);
    }
    @GetMapping("/Previous/{userId}/{questionId}/{id}")
    public SimpleDto getPreviousAnswer(@PathVariable Long userId,@PathVariable Long questionId,@PathVariable Long id) throws ExecutionException, InterruptedException {
        AnswerVo nextAnswer = answerService.getPreviousAnswer(userId,questionId, id);
        return nextAnswer == null ? new SimpleDto(false,"没有更多内容了",null) : new SimpleDto(true,null,nextAnswer);
    }
    @GetMapping("/Page/{userId}/{questionId}/{id}")
    public SimpleDto getNextTreeAnswer(@PathVariable Long userId,@PathVariable Long questionId, @PathVariable Long id) throws ExecutionException, InterruptedException {
        List<AnswerVo> nextTreeAnswer = answerService.getNextTreeAnswer(userId,questionId,id);
        return new SimpleDto(true,null,nextTreeAnswer);
    }
}
