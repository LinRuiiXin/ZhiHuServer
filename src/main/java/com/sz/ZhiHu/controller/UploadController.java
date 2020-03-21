package com.sz.ZhiHu.controller;

import com.sz.ZhiHu.dto.SimpleDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;

@RestController
@RequestMapping("/upload")
public class UploadController {
    //上传头像，存储在 D:/upload/ZhiHu/用户id/portrait.文件后缀
    @PostMapping("/portrait/{id}")
    public SimpleDto uploadPortrait(@PathVariable("id")Long id, MultipartHttpServletRequest request){
        try {
            MultipartFile portrait = request.getFile("portrait");
            if(portrait != null){
                doFile("/User/"+id);
                String fileName = portrait.getOriginalFilename();
                String[] split = fileName.split("\\.");
                File file = new File("User/"+id+"/portrait."+split[1]);
                portrait.transferTo(file);
                return new SimpleDto(true,null,null);
            }else{
                return new SimpleDto(false,"文件为空",null);
            }
        }catch (Exception e){
            return null;
        }
    }
    //判断 D:/upload/ZhiHu 下的某个文件夹是否存在
    public void doFile(String path){
        File file = new File("d:/upload/ZhiHu"+path);
        if(!file.exists()){
            file.mkdir();
        }
    }
}
