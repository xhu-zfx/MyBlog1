package com.panghu.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.panghu.blog.domain.entity.User;
import com.panghu.blog.domain.entity.LoginUser;
import com.panghu.blog.mapper.MenuMapper;
import com.panghu.blog.mapper.UserMapper;
import com.panghu.blog.constant.Consts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/11 11:19
 * @description
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Resource
    MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        // 是否查询成功 , 查询不到即抛出异常
        if (BeanUtil.isEmpty(user)) throw new RuntimeException(Consts.ERROR_LOGIN_USER_NOT_EXIST);
        // 如果是后台用户才需要封装权限信息
        if (user.getType().equals(Consts.LOGIN_USER_TYPE_ADMIN)){
            List<String> permissonList = menuMapper.listPermissonByUserId(user.getId());
            return new LoginUser(user,permissonList);
        }
        return new LoginUser(user);
    }
}
