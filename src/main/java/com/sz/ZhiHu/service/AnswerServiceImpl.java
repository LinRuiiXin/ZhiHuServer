package com.sz.ZhiHu.service;

import com.sz.ZhiHu.dao.AnswerDao;
import com.sz.ZhiHu.po.Answer;
import com.sz.ZhiHu.po.User;
import com.sz.ZhiHu.vo.AnswerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService{
    @Autowired
    AnswerDao answerDao;
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Value("${spring.servlet.multipart.location}")
    private String resourcesLocation;
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Answer insertAnswer(Answer answer) {
        answerDao.insertAnswer(answer);
        questionService.incrementAnswer(answer.getQuestionId());
        questionService.updateQuestionOrder(answer.getQuestionId());
        return answer;
    }

    @Override
    public Answer queryRandomAnswerByQuestionId(Long questionId) {
        return answerDao.queryRandomAnswerByQuestionId(questionId);
    }

    @Override
    public List<Answer> getRandomAnswer(Integer sum) {
        return answerDao.getRandomAnswer(sum);
    }

    @Override
    public AnswerVo getAnswerById(Long id) {
        Answer answer = answerDao.queryAnswerById(id);
        return answer != null ? wrapAnswer(answer) : null;
    }

    @Override
    public AnswerVo getNextAnswer(Long questionId, Long id) {
        List<Long> candidateId = getCandidateId(questionId, id, 1);
        return candidateId.size() == 0 ? null : getAnswerById(candidateId.get(0));
    }

    @Override
    public AnswerVo getPreviousAnswer(Long questionId, Long id) {
        Long previousId = getPreviousId(questionId, id);
        if(previousId != null){
            AnswerVo answerById = getAnswerById(previousId);
            return answerById;
        }
        return null;
    }

    @Override
    public List<AnswerVo> getNextTreeAnswer(Long questionId,Long id) {
        List<Long> candidateId = getCandidateId(questionId, id, 2);
        candidateId.add(0,id);
        System.out.println(candidateId);
        List<AnswerVo> answerBatch = getAnswerBatch(candidateId);
        return answerBatch;
    }


    private AnswerVo wrapAnswer(Answer answer){
        answer.setContent(getAnswerContent(answer.getId()));
        User user = userService.getUserById(answer.getUserId());
        return new AnswerVo(user,answer);
    }

    private String getAnswerContent(Long id) {
        FileReader fileReader = null;
        StringBuffer stringBuffer;
        try {
            fileReader = new FileReader(resourcesLocation+"/Answer/"+id+".txt");
            char [] buffer = new char[1024];
            int len = 0;
            stringBuffer= new StringBuffer();
            while ((len = fileReader.read(buffer))!=-1){
                stringBuffer.append(buffer,0,len);
            }
            return stringBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
    private List<Long> getCandidateId(Long questionId,Long id,Integer len){
        List<Long> answerOrder = questionService.getAnswerOrder(questionId);
        List<Long> ids = new ArrayList<>();
        int index = -1;
        for(int i = 0;i<answerOrder.size();i++){
            Long idInOrder = answerOrder.get(i);
            if(idInOrder.equals(id)){
                index = i;
                break;
            }
        }
        if(index != -1){
            for(int i = 1;i<=len;i++){
                int indexNow = index+i;
                if(indexNow>=answerOrder.size())
                    break;
                ids.add(answerOrder.get(indexNow));
            }
        }
        return ids;
    }
    private Long getPreviousId(Long questionId,Long id){
        List<Long> answerOrder = questionService.getAnswerOrder(questionId);
        int index = -1;
        for(int i = 0;i<answerOrder.size();i++){
            Long idInOrder = answerOrder.get(i);
            if(idInOrder.equals(id)){
                index = i;
                break;
            }
        }
        if(index != -1){
            int indexNow = index - 1;
            if(indexNow >= 0){
                return answerOrder.get(indexNow);
            }
            return null;
        }
        return null;
    }
    private List<AnswerVo> getAnswerBatch(List<Long> ids){
        List<AnswerVo> res = new ArrayList<>();
        ids.forEach(id->res.add(getAnswerById(id)));
        return res;
    }
}
