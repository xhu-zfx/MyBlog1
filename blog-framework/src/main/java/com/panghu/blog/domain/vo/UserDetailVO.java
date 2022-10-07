package com.panghu.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/5 17:12
 * @description 修改用户的用户信息回显VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailVO {

    List<Long> roleIds;

    List<RoleListVO> roles;

    UserListVO user;
}
