package com.sz.ZhiHu.service;

import com.sz.ZhiHu.po.Answer;
import com.sz.ZhiHu.po.Article;
import com.sz.ZhiHu.po.User;
import com.sz.ZhiHu.util.RandomUtils;
import com.sz.ZhiHu.vo.RecommendViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    CollectService collectService;
    @Autowired
    ClassifyQuestionService classifyQuestionService;
    @Autowired
    TypeRecordService typeRecordService;
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    AnswerService answerService;
    @Autowired
    ArticleService articleService;
    /*
        查出用户最近8条浏览的问题类型。再根据类型随机出1-3个问题
    */
    @Override
    public List<RecommendViewBean> getIndexRecommend(Long userId) {
        int otherRecommend = 2;
        List<RecommendViewBean> recommendViewBeans = new ArrayList<>();
        if(userId != -1){
            List<Long> userRecordType = typeRecordService.getUserRecordType(userId);
            List<Long> questionId = new ArrayList<>();
            userRecordType.forEach(id->{
                questionId.addAll(classifyQuestionService.getRandomQuestion(id,1));
            });
            questionId.stream().distinct().forEach(id->{
                RecommendViewBean randomAnswer = getRandomAnswerViewBeanByQuestion(id);
                if(randomAnswer != null){
                    recommendViewBeans.add(randomAnswer);
                }
            });
            if(recommendViewBeans.size() < 5){
                otherRecommend += 5-recommendViewBeans.size();
            }
        }else{
            otherRecommend = 5;
        }
        //获取指定个数的Answer并封装成RecommendViewBean
        recommendViewBeans.addAll(getRandomAnswerViewBean(otherRecommend));
        //获取随机一片文章并封装成RecommendViewBean
        recommendViewBeans.addAll(getRandomArticleViewBean());
        return recommendViewBeans;
    }


    public RecommendViewBean getRandomAnswerViewBeanByQuestion(Long id){

        Answer answer = answerService.queryRandomAnswerByQuestionId(id);
        if(answer != null){
            RecommendViewBean viewBean = wrapAnswer(answer);
            return viewBean;
        }
        return null;
    }

    public List<RecommendViewBean> getRandomAnswerViewBean(int sum){
        List<RecommendViewBean> recommendViewBeans = new ArrayList<>();
        List<Answer> randomAnswer = answerService.getRandomAnswer(sum);
        randomAnswer.forEach(answer -> {
            recommendViewBeans.add(wrapAnswer(answer));
        });
        return recommendViewBeans;
    }


    private List<RecommendViewBean> getRandomArticleViewBean() {
        List<RecommendViewBean> recommendViewBeans = new ArrayList<>();
        List<Article> randomArticle = articleService.getRandomArticle(RandomUtils.getRangeNumber(2));
        randomArticle.forEach(article->{
            RecommendViewBean viewBean = new RecommendViewBean();
            viewBean.setTitle(article.getTitle());
            viewBean.setCommentSum(article.getCommentSum());
            viewBean.setSupportSum(article.getSupportSum());
            viewBean.setContentType(2);
            User user = userService.getUserById(article.getAuthorId());
            viewBean.setUserId(user.getId());
            viewBean.setUsername(user.getUserName());
            viewBean.setPortraitFileName(user.getPortraitFileName());
            viewBean.setIntroduction(user.getProfile());
            viewBean.setContentId(article.getId());
            recommendViewBeans.add(viewBean);
        });
        return recommendViewBeans;
    }


    private RecommendViewBean wrapAnswer(Answer answer) {
        RecommendViewBean viewBean = new RecommendViewBean();
        viewBean.setQuestionId(answer.getQuestionId());
        viewBean.setContentType(1);
        viewBean.setContent(answer.getContent());
        viewBean.setThumbnail(answer.getThumbnail());
        viewBean.setTitle(questionService.getQuestionTitle(answer.getQuestionId()));
        viewBean.setSupportSum(answer.getSupportSum());
        viewBean.setContentId(answer.getId());
        viewBean.setType(answer.getContentType());
        viewBean.setCommentSum(answer.getCommentSum());
        User user = userService.getUserById(answer.getUserId());
        viewBean.setUserId(user.getId());
        viewBean.setUsername(user.getUserName());
        viewBean.setPortraitFileName(user.getPortraitFileName());
        viewBean.setIntroduction(user.getProfile());
        return viewBean;
    }
}
