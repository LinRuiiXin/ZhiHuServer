package com.sz.ZhiHu.dao;

import com.sz.ZhiHu.po.AnswerCommentLevelOne;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {
    List<AnswerCommentLevelOne> getAnswerCommentLevelOne(Long answerId,int start);
}
