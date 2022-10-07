package com.panghu.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panghu.blog.constant.Consts;
import com.panghu.blog.constant.RedisConsts;
import com.panghu.blog.domain.entity.LoginUser;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.User;
import com.panghu.blog.domain.vo.LoginUserVO;
import com.panghu.blog.domain.vo.UserInfoVO;
import com.panghu.blog.mapper.UserMapper;
import com.panghu.blog.service.AdminService;
import com.panghu.blog.util.BeanCopyUtils;
import com.panghu.blog.util.JwtUtil;
import com.panghu.blog.util.RedisCache;
import com.panghu.blog.util.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/24 16:33
 * @description
 */
@Service
public class AdminServiceImpl extends ServiceImpl<UserMapper, User>
        implements AdminService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        // authenticationManager的authenticate最终会调用UserDetailsService的loadUserByUsername方法
        // 我们创建UserDetailsServiceImpl对该方法进行实现即可
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (BeanUtil.isEmpty(authenticate)) throw new RuntimeException(Consts.ERROR_LOGIN_WRONG);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        redisCache.setCacheObject(RedisConsts.BLOG_ADMIN_PREFIX +RedisConsts.LOGIN_USER_KEY+userId,loginUser, RedisConsts.LOGIN_USER_TIME, TimeUnit.DAYS);
        // 返回token
        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        // 获取请求头并解密，已封装到SecurityUtils.getLoginUser方法中
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 获取userId
        String userId = loginUser.getUser().getId().toString();
        // 删除redis中的信息
        redisCache.deleteObject(RedisConsts.BLOG_ADMIN_PREFIX + RedisConsts.LOGIN_USER_KEY + userId);
        return ResponseResult.okResult();
    }


}
