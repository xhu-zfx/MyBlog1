package com.panghu.blog.service;

import com.panghu.blog.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.panghu.blog.domain.entity.ResponseResult;

/**
* @author xhu-zfx
* @description 针对表【blog_comment(评论表)】的数据库操作Service
* @createDate 2022-09-08 16:19:35
*/
public interface CommentService extends IService<Comment> {

    ResponseResult listAllComment(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult saveComment(Comment comment);
}
