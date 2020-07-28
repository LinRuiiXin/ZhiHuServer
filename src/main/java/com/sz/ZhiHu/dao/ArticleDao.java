package com.sz.ZhiHu.dao;

import com.sz.ZhiHu.po.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao {
    List<Article> getRandomArticle(int sum);
    void addArticle(Article article);
}
