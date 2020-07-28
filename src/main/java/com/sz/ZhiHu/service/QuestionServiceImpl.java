package com.sz.ZhiHu.service;

import com.sz.ZhiHu.dao.AnswerDao;
import com.sz.ZhiHu.dao.QuestionDao;
import com.sz.ZhiHu.po.Question;
import com.sz.ZhiHu.vo.RecommendQuestionViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QuestionServiceImpl implements QuestionService {
    /**
     *    key-questionId value-lock
     *    根据questionId决定哪个问题由哪个锁保护
     */
    private Map<Long,Object> locks;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    UserService userService;
    @Autowired
    AsyncService asyncService;
    @Autowired
    QuestionAttentionService questionAttentionService;
    @Autowired
    ClassifyQuestionService classifyQuestionService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AnswerDao answerDao;

    public QuestionServiceImpl(){
        locks = new ConcurrentHashMap<>();
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Question addQuestion(Question question, String typeStr) {
        questionDao.insertQuestion(question);
        asyncService.setQuestionType(question.getId(),typeStr);
        return question;
    }

    @Override
    public List<RecommendQuestionViewBean> getRandomQuestion(Long userId) {
        List<Question> randomQuestion = questionDao.getRandomQuestion();
        List<RecommendQuestionViewBean> beans = new ArrayList<>();
        randomQuestion.forEach(question -> {
            RecommendQuestionViewBean recommendQuestionViewBean = new RecommendQuestionViewBean();
            recommendQuestionViewBean.setSubscribe(questionAttentionService.hasAttention(userId,question.getId()));
            recommendQuestionViewBean.setQuestion(question);
            recommendQuestionViewBean.setUser(userService.getUserById(question.getQuestionerId()));
            beans.add(recommendQuestionViewBean);
        });
        return beans;
    }
    //竞态条件
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void subscribeQuestion(Long userId, Long questionId) {
        if(!questionAttentionService.hasAttention(userId,questionId)){
            questionDao.subscribeQuestion(questionId);
            questionAttentionService.addNewAttention(userId,questionId);
        }
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void incrementAnswer(Long questionId) {
        questionDao.incrementAnswer(questionId);
    }
    //竞态条件
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void disSubscribeQuestion(Long userId, Long questionId) {
        if(questionAttentionService.hasAttention(userId,questionId)){
            questionAttentionService.removeAttention(userId,questionId);
            questionDao.disSubscribeQuestion(questionId);
        }
    }

    @Override
    public String getQuestionTitle(Long questionId) {
        return questionDao.getQuestionNameById(questionId);
    }

    @Override
    public void recordUserBrowse(Long questionId,Long userId) {
        List<Long> questionType = classifyQuestionService.getQuestionType(questionId);
        asyncService.recordUserBrowse(questionType,userId);
    }
    /**
     *  共享变量 -> locks
     */
    @Override
    public List<Long> getAnswerOrder(Long questionId) {
        Object lock = locks.get(questionId);
        if(lock == null){
            Object o = new Object();
            lock = locks.putIfAbsent(questionId,o);
            if(lock == null)
                lock = o;
        }
        String key = "AnswerOrder:"+questionId;
        List<Long> answerOrder = null;
            if(!redisTemplate.hasKey(key)){
                synchronized (lock) {
                    if(!redisTemplate.hasKey(key)){
                        answerOrder = answerDao.getAnswerOrder(questionId);
                        redisTemplate.opsForValue().set(key, answerOrder);
                    }else
                        answerOrder = (List<Long>) redisTemplate.opsForValue().get(key);
                }
            }else{
                answerOrder = (List<Long>) redisTemplate.opsForValue().get(key);
            }
        return answerOrder;
    }

    @Override
    public void updateQuestionOrder(Long questionId) {
        Object lock = locks.get(questionId);
        if(lock == null){
            Object o = new Object();
            lock = locks.putIfAbsent(questionId, o);
            if(lock == null)
                lock = o;
        }
        String key = "AnswerOrder:"+questionId;
        List<Long> answerOder = answerDao.getAnswerOrder(questionId);
        if(!redisTemplate.hasKey(key)){
            synchronized (lock) {
                if(!redisTemplate.hasKey(key))
                    redisTemplate.opsForValue().set(key, answerOder);
                else
                    redisTemplate.boundValueOps(key).set(answerOder);
            }
        }else{
            redisTemplate.boundValueOps(key).set(answerOder);
        }
    }
}
