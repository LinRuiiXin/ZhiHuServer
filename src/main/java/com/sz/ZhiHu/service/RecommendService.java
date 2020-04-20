package com.sz.ZhiHu.service;

import com.sz.ZhiHu.vo.RecommendViewBean;

import java.util.List;

public interface RecommendService {
    List<RecommendViewBean> getIndexRecommend(Long userId);
}
