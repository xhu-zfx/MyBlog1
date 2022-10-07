package com.panghu.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/2 14:18
 * @description 接收查询所有博文的DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDTO {
    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;
}

