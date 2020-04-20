package com.sz.ZhiHu.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileUtils {

    //根据当前系统时间戳+用户id生成唯一文件名
    public static String convertFileName(MultipartFile multipartFile, Long id){
        String [] fileNameArr = multipartFile.getOriginalFilename().split("\\.");
        String fileName = System.currentTimeMillis()+String.valueOf(id)+"."+fileNameArr[(fileNameArr.length)-1];
        return fileName;
    }
    //判断 D:/upload/ZhiHu 下的某个文件夹是否存在
    public static void doFile(String path){
        File file = new File("d:/upload/ZhiHu"+path);
        if(!file.exists()){
            file.mkdir();
        }
    }
}
