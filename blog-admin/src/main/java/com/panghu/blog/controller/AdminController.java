package com.panghu.blog.controller;

import cn.hutool.core.util.StrUtil;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.User;
import com.panghu.blog.domain.vo.*;
import com.panghu.blog.enums.AppHttpCodeEnum;
import com.panghu.blog.exception.SystemException;
import com.panghu.blog.service.AdminService;
import com.panghu.blog.service.MenuService;
import com.panghu.blog.service.RoleService;
import com.panghu.blog.util.BeanCopyUtils;
import com.panghu.blog.util.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/11 10:44
 * @description
 */
@RestController
public class AdminController {

    @Resource
    private AdminService adminService;

    @Resource
    MenuService menuService;

    @Resource
    RoleService roleService;

    // 登录接口
    @PostMapping("/admin/login")
    public ResponseResult login(@RequestBody User user){
        // 后端校验
        if (StrUtil.isBlankIfStr(user.getUserName()))
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        return adminService.login(user);
    }

    // 退出登录接口
    @PostMapping("/admin/logout")
    public ResponseResult logout(){
        return adminService.logout();
    }

    // 查询权限接口
    @GetMapping("/getInfo")
    ResponseResult getInfo(){
        // 查询当前用户id
        User loginAdmin = SecurityUtils.getLoginUser().getUser();
        // 根据用户id查询权限信息
        List<String> permissions=menuService.listPermissionsById(loginAdmin.getId());
        // 根据用户id查询角色信息
        List<String> roles=roleService.listRolesById(loginAdmin.getId());
        // 根据用户id查询用户信息
        UserInfoVO userInfoVO= BeanCopyUtils.copyBeanSingle(loginAdmin, UserInfoVO.class);
        // 返回数据
        return ResponseResult.okResult(new AdminUserInfoVO(permissions,roles,userInfoVO));
    }

    // 动态路由接口
    @GetMapping("/getRouters")
    ResponseResult getRouters(){
        // 查询当前用户id
        User loginAdmin = SecurityUtils.getLoginUser().getUser();
        List<MenuVO> menuVOTree=menuService.listMenuTreeByUserId(loginAdmin.getId());
        return ResponseResult.okResult(new RoutersVO(menuVOTree));
    }


}
