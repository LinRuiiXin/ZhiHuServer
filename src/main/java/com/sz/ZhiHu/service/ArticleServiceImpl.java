package com.sz.ZhiHu.service;

import com.sz.ZhiHu.dao.ArticleDao;
import com.sz.ZhiHu.po.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;
    @Override
    public List<Article> getRandomArticle(int sum) {
        return articleDao.getRandomArticle(sum);
    }

    @Override
    public void addArticle(Article article) {
        articleDao.addArticle(article);
    }
}
