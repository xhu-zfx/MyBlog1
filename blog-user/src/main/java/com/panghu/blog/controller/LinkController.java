package com.panghu.blog.controller;

import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.service.LinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/11 9:56
 * @description
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.listAllLink();
    }
}
