package com.sz.ZhiHu.controller;

import com.sz.ZhiHu.dto.SimpleDto;
import com.sz.ZhiHu.service.RecommendService;
import com.sz.ZhiHu.vo.RecommendViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Recommend")
public class RecommendController {
    RecommendService recommendService;
    @Autowired
    public void setRecommendService(RecommendService recommendService){
        this.recommendService = recommendService;
    }
    @GetMapping("/Index/{userId}")
    public SimpleDto getIndexRecommend(@PathVariable Long userId){
        List<RecommendViewBean> indexRecommend = recommendService.getIndexRecommend(userId);
        return new SimpleDto(true,null,indexRecommend);
    }

}
