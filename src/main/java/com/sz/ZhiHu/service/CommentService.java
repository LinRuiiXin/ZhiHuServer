package com.sz.ZhiHu.service;

import com.sz.ZhiHu.vo.AnswerCommentLevelOneVo;

import java.util.List;

public interface CommentService {
    List<AnswerCommentLevelOneVo> getAnswerCommentLevelOne(Long answerId, Long userId,int limit);
}
