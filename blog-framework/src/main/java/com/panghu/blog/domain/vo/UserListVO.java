package com.panghu.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/4 18:56
 * @description
 */
@Data
@AllArgsConstructor
public class UserListVO {
    /**
     * 头像
     */
    private String avatar;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 邮箱
     */
    private String email;

    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phonenumber;


    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;
    /**
     * 账号状态（0正常 1停用）
     */
    private String status;

    /**
     * 用户名
     */
    private String userName;
}
