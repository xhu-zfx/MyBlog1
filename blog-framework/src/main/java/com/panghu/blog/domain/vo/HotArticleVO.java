package com.panghu.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/8 12:23
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVO {
    private Long id;
    private String title;
    private Long viewCount;
}
