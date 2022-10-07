package com.panghu.blog.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/4 11:46
 * @description
 */
@Data
@AllArgsConstructor
public class RoleMenuTreeVO {

    List<MenuTreeVO> menus;


    List<Long> checkedKeys;
}
