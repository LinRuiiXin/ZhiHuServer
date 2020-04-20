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

@RestController
@RequestMapping("/Answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;
    @PostMapping
    public SimpleDto addAnswer(MultipartHttpServletRequest request, @RequestParam("questionId")Long questionId,@RequestParam("userId")Long userId,@RequestParam("contentType")Integer contentType,@RequestParam String content){
        MultipartFile answerFile = request.getFile("answer");
        if(answerFile != null){
            SimpleDto simpleDto = null;
            Answer answer = answerService.insertAnswer(new Answer(questionId, userId,content, contentType));
            File file = new File("/Answer/"+answer.getId()+".txt");
            try {
                answerFile.transferTo(file);
                simpleDto = new SimpleDto(true,null,null);
            } catch (IOException e) {
                e.printStackTrace();
                simpleDto = new SimpleDto(false,"IO异常",null);
            }
            return  simpleDto;
        }else{
            return new SimpleDto(false,"回答不能为空,",null);
        }
    }
    @GetMapping("/{id}")
    public SimpleDto getAnswerById(@PathVariable Long id){
        AnswerVo answerById = answerService.getAnswerById(id);
        return answerById.getAnswer() == null ? new SimpleDto(false,"此回答已被删除",null) : new SimpleDto(true,null,answerById);
    }
    @GetMapping("/Next/{questionId}/{id}")
    public SimpleDto getNextAnswer(@PathVariable Long questionId,@PathVariable Long id){
        AnswerVo nextAnswer = answerService.getNextAnswer(questionId, id);
        return nextAnswer == null ? new SimpleDto(false,"没有更多内容了",null) : new SimpleDto(true,null,nextAnswer);
    }
    @GetMapping("/Previous/{questionId}/{id}")
    public SimpleDto getPreviousAnswer(@PathVariable Long questionId,@PathVariable Long id){
        AnswerVo nextAnswer = answerService.getPreviousAnswer(questionId, id);
        return nextAnswer == null ? new SimpleDto(false,"没有更多内容了",null) : new SimpleDto(true,null,nextAnswer);
    }
    @GetMapping("/Page/{questionId}/{id}")
    public SimpleDto getNextTreeAnswer(@PathVariable Long questionId, @PathVariable Long id){
        List<AnswerVo> nextTreeAnswer = answerService.getNextTreeAnswer(questionId,id);
        return new SimpleDto(true,null,nextTreeAnswer);
    }
}
