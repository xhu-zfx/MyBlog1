package com.panghu.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/5 18:06
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;


    /**
     * 账号状态（0正常 1停用）
     */
    private String status;


    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;

    /**
     * 用户管理角色集合
     */
    List<Long> roleIds;
}
