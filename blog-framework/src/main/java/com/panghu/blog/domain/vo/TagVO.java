package com.panghu.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/28 16:17
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVO {
    private Long id;

    private String name;

    private String remark;
}
