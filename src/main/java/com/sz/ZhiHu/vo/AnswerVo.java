package com.sz.ZhiHu.vo;

import com.sz.ZhiHu.po.Answer;
import com.sz.ZhiHu.po.User;

public class AnswerVo {
    private User user;
    private Answer answer;
    public AnswerVo(){}

    public AnswerVo(User user, Answer answer) {
        this.user = user;
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "AnswerVo{" +
                "user=" + user +
                ", answer=" + answer +
                '}';
    }
}
