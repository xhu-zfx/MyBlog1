package com.panghu.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/24 17:51
 * @description
 */
@AllArgsConstructor
@Data
public class AdminUserInfoVO {
    // 权限集合
    List<String> permissions;
    // 角色集合
    List<String> roles;
    // 用户信息
    UserInfoVO user;
}
