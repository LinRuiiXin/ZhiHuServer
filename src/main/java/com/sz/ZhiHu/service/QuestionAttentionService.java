package com.sz.ZhiHu.service;

public interface QuestionAttentionService {
    void addNewAttention(Long userId,Long questionId);
    Boolean hasAttention(Long userId,Long questionId);
    void removeAttention(Long userId,Long questionId);
}
