package com.sz.ZhiHu.service;

import com.sz.ZhiHu.dao.ClassifyDao;
import com.sz.ZhiHu.po.Classify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClassifyServiceImpl implements ClassifyService {
    @Autowired
    ClassifyDao classifyDao;
    @Override
    public List<Classify> getClassifyAboutKeyword(String keyword,int start) {
        return classifyDao.queryClassifyAboutKeyword(keyword,start,start+5);
    }
}
