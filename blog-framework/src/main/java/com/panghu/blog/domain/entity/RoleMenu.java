package com.panghu.blog.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 角色和菜单关联表
 * @TableName blog_sys_role_menu
 */
@TableName(value ="blog_sys_role_menu")
@Data
@AllArgsConstructor
public class RoleMenu implements Serializable {
    /**
     * 角色ID
     */
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long roleId;

    /**
     * 菜单ID
     */
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long menuId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}