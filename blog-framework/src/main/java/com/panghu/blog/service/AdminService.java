package com.panghu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.User;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/24 16:32
 * @description
 */
public interface AdminService extends IService<User> {
    ResponseResult login(User user);

    ResponseResult logout();
}
