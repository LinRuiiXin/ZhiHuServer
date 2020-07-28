package com.sz.ZhiHu.controller;

import com.sz.ZhiHu.dto.SimpleDto;
import com.sz.ZhiHu.po.Article;
import com.sz.ZhiHu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/Article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @PostMapping("/Video")
    public SimpleDto uploadVideo(MultipartHttpServletRequest request, @RequestParam Long userId,@RequestParam String title) throws IOException {
        MultipartFile video = request.getFile("video");
        if(video != null){
            if(video.getSize() < 20971520){
                String fileName = newFileName(video.getOriginalFilename(),userId);
                File file = new File("/Video/" + fileName);
                video.transferTo(file);
                Article article = new Article(userId,title,null,fileName,3);
                articleService.addArticle(article);
                return new SimpleDto(true,null,null);
            }else
                return new SimpleDto(false,"超过指定大小",null);
        }else
            return new SimpleDto(false,"文件为空",null);
    }

    private String newFileName(String originalFilename, Long userId) {
        long l = System.currentTimeMillis();
        String[] split = originalFilename.split("\\.");
        String suffix = split[(split.length)-1];
        String fileName = l + userId + "." + suffix;
        return fileName;
    }
}
