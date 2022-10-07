package com.panghu.blog.controller;

import cn.hutool.core.util.StrUtil;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.User;
import com.panghu.blog.enums.AppHttpCodeEnum;
import com.panghu.blog.exception.SystemException;
import com.panghu.blog.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/11 10:44
 * @description
 */
@RestController
public class UserLoginController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        // 后端校验
        if (StrUtil.isBlankIfStr(user.getUserName()))
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        return userService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return userService.logout();
    }
}
