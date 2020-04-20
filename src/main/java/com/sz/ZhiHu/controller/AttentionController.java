package com.sz.ZhiHu.controller;

import com.sz.ZhiHu.dto.SimpleDto;
import com.sz.ZhiHu.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Attention")
public class AttentionController {
    @Autowired
    QuestionService questionService;
    @PostMapping("/Question")
    public SimpleDto addQuestionSub(@RequestParam("userId") String userId,@RequestParam("questionId")String questionId){
        questionService.subscribeQuestion(Long.valueOf(userId),Long.valueOf(questionId));
        return new SimpleDto(true,null,null);
    }
    @DeleteMapping("/Question")
    public SimpleDto removeQuestionSub(@RequestParam("userId")String userId,@RequestParam("questionId")String questionId){
        questionService.disSubscribeQuestion(Long.valueOf(userId),Long.valueOf(questionId));
        return new SimpleDto(true,null,null);
    }
}
