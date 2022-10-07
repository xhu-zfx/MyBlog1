package com.panghu.blog.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/3 19:41
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuTreeVO {

    @JsonSerialize(using = ToStringSerializer.class)
    Long id;


    String menuName;

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    Long parentId;

    List<MenuTreeVO> children;

    public MenuTreeVO(Long id, String menuName, Long parentId) {
        this.id = id;
        this.menuName = menuName;
        this.parentId = parentId;
    }
}
