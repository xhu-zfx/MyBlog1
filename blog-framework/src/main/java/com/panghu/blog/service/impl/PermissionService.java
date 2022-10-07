package com.panghu.blog.service.impl;

import com.panghu.blog.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/1 18:54
 * @description
 */
@Service("ps")
public class PermissionService {
    /**
     * 判断当前用户是否具有该权限
     * @param permission 要判断的权限
     * @return true：有该权限
     */
    public boolean hasPermission(String permission){
        // 如果是超级管理员，返回true
        if (SecurityUtils.isAdmin()) return true;
        // 获取当前用户的权限列表并校验
        List<String> permissonList = SecurityUtils.getLoginUser().getPermissons();
        return permissonList.contains(permission);

    }
}
