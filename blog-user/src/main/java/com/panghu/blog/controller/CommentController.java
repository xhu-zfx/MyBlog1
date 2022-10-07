package com.panghu.blog.controller;

import com.panghu.blog.annotation.SystemLog;
import com.panghu.blog.constant.Consts;
import com.panghu.blog.domain.entity.Comment;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/14 18:12
 * @description
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.listAllComment(Consts.COMMENT_TYPE_ARTICLE,articleId,pageNum,pageSize);
    }

    @PostMapping("/saveComment")
    public ResponseResult saveComment(@RequestBody Comment comment){
        return commentService.saveComment(comment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.listAllComment(Consts.COMMENT_TYPE_LINK,null,pageNum,pageSize);
    }
}
