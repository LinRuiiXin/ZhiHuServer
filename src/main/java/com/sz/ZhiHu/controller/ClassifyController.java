package com.sz.ZhiHu.controller;

import com.sz.ZhiHu.dto.SimpleDto;
import com.sz.ZhiHu.po.Classify;
import com.sz.ZhiHu.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Classify")
public class ClassifyController {
    @Autowired
    ClassifyService classifyService;
    @GetMapping("/{keyword}/{start}")
    public SimpleDto getClassifyByKeyword(@PathVariable("keyword")String keyword,@PathVariable("start")int start){
        List<Classify> classifyAboutKeyword = classifyService.getClassifyAboutKeyword(keyword, start);
        return new SimpleDto(true,null,classifyAboutKeyword);
    }

}
