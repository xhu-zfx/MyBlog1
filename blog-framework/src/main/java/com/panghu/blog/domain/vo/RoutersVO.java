package com.panghu.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/26 11:07
 * @description
 */
@Data
@AllArgsConstructor
public class RoutersVO {
    List<MenuVO> menus;
}
