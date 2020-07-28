package com.sz.ZhiHu.service;

import com.sz.ZhiHu.po.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getRandomArticle(int sum);
    void addArticle(Article article);
}
