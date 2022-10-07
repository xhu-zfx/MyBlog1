package com.panghu.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/2 17:15
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuListVO {

    /**
     * 组件路径
     */
    private String component;

    /**
     * 菜单图标
     */
    private String icon;

    private Long id;

    /**
     * 是否为外链（0是 1否）
     */
    private Integer isFrame;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    private String visible;

}
