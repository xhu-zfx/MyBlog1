package com.panghu.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/29 16:12
 * @description 接收新建博文数据的DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInsertDTO {

    /**
     * 标题
     */
    private String title;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 是否置顶
     */
    private String isTop;

    /**
     * 是否允许评论 1是，0否
     */
    private String isComment;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 所关联标签id数组
     */
    private List<Long> tags;

    /**
     * 所属分类id
     */
    private Long categoryId;
    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 状态（0已发布，1草稿）
     */
    private String status;
}
