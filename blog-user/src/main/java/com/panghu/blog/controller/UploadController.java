package com.panghu.blog.controller;

import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/18 15:53
 * @description
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Resource
    UploadService uploadService;

    @PostMapping
    ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
