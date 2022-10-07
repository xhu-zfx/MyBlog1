package com.panghu.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/9 15:17
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVO {
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 所属分类名
     */
    private String categoryName;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 访问量
     */
    private Long viewCount;

    /**
     *
     */
    private Date createTime;

}
