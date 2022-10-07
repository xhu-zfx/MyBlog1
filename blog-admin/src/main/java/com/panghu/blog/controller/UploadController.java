package com.panghu.blog.controller;

import com.panghu.blog.constant.Consts;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/29 16:01
 * @description
 */
@RestController
@RequestMapping
public class UploadController {

    @Resource
    UploadService uploadService;

    @PostMapping("/upload")
    ResponseResult uploadImg(@RequestParam("img") MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
