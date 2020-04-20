package com.sz.ZhiHu.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectDao {
    List<Long> getUserCollectQuestionId(Long userId);
}
