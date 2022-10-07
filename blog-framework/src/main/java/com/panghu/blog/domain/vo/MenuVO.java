package com.panghu.blog.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/26 11:10
 * @description
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class MenuVO {

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long id;

    private String menuName;

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long parentId;

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Integer orderNum;

    private String path;

    private String component;

    private String menuType;

    private String visible;

    private String status;

    private String perms;

    private String icon;

    private Date createTime;

    private List<MenuVO> children;

    public MenuVO(Long id, String menuName, Long parentId, Integer orderNum, String path, String component, String menuType, String visible, String status, String perms, String icon, Date createTime) {
        this.id = id;
        this.menuName = menuName;
        this.parentId = parentId;
        this.orderNum = orderNum;
        this.path = path;
        this.component = component;
        this.menuType = menuType;
        this.visible = visible;
        this.status = status;
        this.perms = perms;
        this.icon = icon;
        this.createTime = createTime;
    }
}
