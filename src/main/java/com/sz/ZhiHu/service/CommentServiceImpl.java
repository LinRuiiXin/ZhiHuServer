package com.sz.ZhiHu.service;

import com.sz.ZhiHu.dao.CommentDao;
import com.sz.ZhiHu.dao.CommentSupportDao;
import com.sz.ZhiHu.po.AnswerCommentLevelOne;
import com.sz.ZhiHu.vo.AnswerCommentLevelOneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao;
    private UserService userService;
    private CommentSupportDao commentSupportDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, UserService userService, CommentSupportDao commentSupportDao) {
        this.commentDao = commentDao;
        this.userService = userService;
        this.commentSupportDao = commentSupportDao;
    }

    @Override
    public List<AnswerCommentLevelOneVo> getAnswerCommentLevelOne(Long answerId, Long userId, int limit) {
        List<AnswerCommentLevelOne> answerCommentLevelOne = commentDao.getAnswerCommentLevelOne(answerId, limit);
        List<AnswerCommentLevelOneVo> res = new ArrayList<>(answerCommentLevelOne.size());
            if (answerCommentLevelOne.size() != 0) {
            for (AnswerCommentLevelOne commentLevelOne : answerCommentLevelOne) {
                AnswerCommentLevelOneVo vo = new AnswerCommentLevelOneVo();
                vo.setCommentLevelOne(commentLevelOne);
                vo.setUser(userService.getUserById(commentLevelOne.getUserId()));
                vo.setSupport(commentLevelOne.getSupportSum() == 0 ? false : userHadSupportCommentLevelOne(commentLevelOne.getId(), userId));
                res.add(vo);
            }
        }
        return res;
    }

    /*
     * 获取用户是否为一级评论点赞
     * */
    private boolean userHadSupportCommentLevelOne(Long commentId, Long userId) {
        return commentSupportDao.hadSupportAnswerCommentLvOne(commentId, userId) != null;
    }
}
