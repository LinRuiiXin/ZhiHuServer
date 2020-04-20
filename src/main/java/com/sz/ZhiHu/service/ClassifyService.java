package com.sz.ZhiHu.service;

import com.sz.ZhiHu.po.Classify;

import java.util.List;

public interface ClassifyService {
    List<Classify> getClassifyAboutKeyword(String keyword,int start);
}
