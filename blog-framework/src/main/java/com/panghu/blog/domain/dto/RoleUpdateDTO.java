package com.panghu.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/4 17:01
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleUpdateDTO {
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 菜单权限集合
     */
    private List<Long> menuIds;

    /**
     * 备注
     */
    private String remark;

}
