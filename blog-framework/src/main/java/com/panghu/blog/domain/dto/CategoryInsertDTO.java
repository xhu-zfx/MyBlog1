package com.panghu.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/6 10:18
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInsertDTO {
    String name;
    String description;
    String status;
}
