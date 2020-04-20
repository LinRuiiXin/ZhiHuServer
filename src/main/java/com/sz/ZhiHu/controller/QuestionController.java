package com.sz.ZhiHu.controller;

import com.sz.ZhiHu.dto.SimpleDto;
import com.sz.ZhiHu.po.Question;
import com.sz.ZhiHu.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/Question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/Random/{userId}")
    public SimpleDto getRandomQuestion(@PathVariable("userId")Long userId){
        return new SimpleDto(true,null,questionService.getRandomQuestion(userId));
    }
    //添加问题，可以携带问题描述文件，如果有，记为有问题描述，否则反之。回答的类型按 id1-id2-id3 的形式
    @PostMapping
    public SimpleDto addNewQuestion(MultipartHttpServletRequest request, Long userId, String questionName, String typeStr){
        Question question = new Question(questionName, userId);
        MultipartFile describe = request.getFile("describe");
        if (describe == null) {
            question.setHasDescribe(0);
            questionService.addQuestion(question, typeStr);
            return new SimpleDto(true,null,null);
        } else {
            try {
                question.setHasDescribe(1);
                Question newQuestion = questionService.addQuestion(question,typeStr);
                File file = new File("/Question/"+newQuestion.getId()+".txt");
                describe.transferTo(file);
                return new SimpleDto(true,null,null);
            } catch (IOException e) {
                e.printStackTrace();
                return new SimpleDto(false,"上传失败",null);
            }
        }
    }
    @RequestMapping("/Browse/{questionId}/{userId}")
    public void recordUserBrowse(@PathVariable Long questionId,@PathVariable Long userId){
        questionService.recordUserBrowse(questionId,userId);
    }
}
