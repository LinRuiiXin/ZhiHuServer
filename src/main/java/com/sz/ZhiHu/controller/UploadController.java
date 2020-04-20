package com.sz.ZhiHu.controller;

import com.sz.ZhiHu.dto.SimpleDto;
import com.sz.ZhiHu.po.Question;
import com.sz.ZhiHu.service.QuestionService;
import com.sz.ZhiHu.service.UserService;
import com.sz.ZhiHu.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;

@RestController
@RequestMapping("/Upload")
public class UploadController {
    @Autowired
    UserService userService;
    //上传头像，存储在 D:/upload/ZhiHu/用户id/portrait.文件后缀
    @PostMapping("/Portrait/{id}")
    public SimpleDto uploadPortrait(@PathVariable("id")Long id, MultipartHttpServletRequest request){
        try {
            MultipartFile portrait = request.getFile("portrait");
            if(portrait != null){
                FileUtils.doFile("/User/"+id);
                String fileName = FileUtils.convertFileName(portrait,id);
                File file = new File("User/"+id+"/"+fileName);
                portrait.transferTo(file);
                userService.setPortraitFileNameById(id,fileName);
                return new SimpleDto(true,fileName,null);
            }else{
                return new SimpleDto(false,"文件为空",null);
            }
        }catch (Exception e){
            return new SimpleDto(false,"上传失败",null);
        }
    }
    /*
    * 上传问题，回答，文章的图片文件，并以当前时间戳+用户id作为文件名存储在 /upload/ZhiHu/Image/文件名+后缀
    * */
    @PostMapping("/Image/{id}")
    public SimpleDto uploadImage(@PathVariable("id")Long id,MultipartHttpServletRequest request){
        try {
            MultipartFile image = request.getFile("image");
            if(image != null){
                FileUtils.doFile("/Image");
                String fileName = FileUtils.convertFileName(image,id);
                image.transferTo(new File("/Image/"+fileName));
                return new SimpleDto(true,fileName,null);
            }else{
                return new SimpleDto(false,"文件为空",null);
            }
        }catch (Exception e){
            return new SimpleDto(false,"上传失败",null);
        }
    }
    /*
    * 上传问题，回答，文章的视频文件，并以当前时间戳+用户id作为文件名存储在 /upload/ZhiHu/Video/文件名+后缀
    * */
    @PostMapping("/Video/{id}")
    public SimpleDto uploadVideo(@PathVariable("id")Long id,MultipartHttpServletRequest request){
        try {
            MultipartFile video = request.getFile("video");
            if(video != null){
                FileUtils.doFile("/Video");
                String fileName = FileUtils.convertFileName(video,id);
                video.transferTo(new File("/Video/"+fileName));
                return new SimpleDto(true,fileName,null);
            }else{
                return new SimpleDto(false,"文件为空",null);
            }
        }catch (Exception e){
            return new SimpleDto(false,"上传失败",null);
        }
    }
}
