package com.sz.ZhiHu.service;

import com.sz.ZhiHu.dao.CollectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService{
    CollectDao collectDao;

    @Autowired
    public void setCollectDao(CollectDao collectDao) {
        this.collectDao = collectDao;
    }

    @Override
    public List<Long> getUserCollectQuestionId(Long userId) {
        return collectDao.getUserCollectQuestionId(userId);
    }
}
