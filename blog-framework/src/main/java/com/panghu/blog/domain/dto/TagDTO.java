package com.panghu.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/28 15:50
 * @description 分页查询标签的(Data Transfer Object)数据传输对象
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    private String name;
    private String remark;
}
