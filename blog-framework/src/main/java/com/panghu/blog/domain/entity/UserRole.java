package com.panghu.blog.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户和角色关联表
 * @TableName blog_sys_user_role
 */
@TableName(value ="blog_sys_user_role")
@Data
@AllArgsConstructor
public class UserRole implements Serializable {
    /**
     * 用户ID
     */

    private Long userId;

    /**
     * 角色ID
     */

    private Long roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}