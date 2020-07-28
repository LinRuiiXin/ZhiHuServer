package com.sz.ZhiHu.controller;

import com.sz.ZhiHu.dto.SimpleDto;
import com.sz.ZhiHu.service.CommentService;
import com.sz.ZhiHu.vo.AnswerCommentLevelOneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Comment")
public class CommentController {
    CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/LevelOne/{answerId}/{userId}/{limit}")
    public SimpleDto getQuestionAnswer(@PathVariable Long answerId, @PathVariable Long userId,@PathVariable int limit){
        List<AnswerCommentLevelOneVo> answerCommentLevelOne = commentService.getAnswerCommentLevelOne(answerId, userId, limit);
        return new SimpleDto(true,null,answerCommentLevelOne);
    }
}
