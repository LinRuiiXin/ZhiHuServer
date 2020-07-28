package com.sz.ZhiHu.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentSupportDao {
    Long hadSupportAnswerCommentLvOne(Long commentId, Long userId);
}
