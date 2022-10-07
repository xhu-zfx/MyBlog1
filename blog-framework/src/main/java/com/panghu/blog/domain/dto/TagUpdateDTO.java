package com.panghu.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/28 18:36
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TagUpdateDTO {
    private Long id;

    private String name;

    private String remark;
}
