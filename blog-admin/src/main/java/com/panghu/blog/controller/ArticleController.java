package com.panghu.blog.controller;

import com.panghu.blog.domain.dto.ArticleInsertDTO;
import com.panghu.blog.domain.dto.ArticleListDTO;
import com.panghu.blog.domain.dto.ArticleUpdateDTO;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/29 16:09
 * @description
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @PostMapping
    ResponseResult insertArticle(@RequestBody ArticleInsertDTO articleInsertDTO){
        return articleService.insertArticle(articleInsertDTO);
    }

    @GetMapping("/list")
    ResponseResult listArticle(Integer pageNum , Integer pageSize ,ArticleListDTO articleListDTO){
        return articleService.listArticle(pageNum,pageSize,articleListDTO);
    }

    @GetMapping("/{id}")
    ResponseResult getArticleById(@PathVariable("id") Long id){
        return articleService.getArticleById(id);
    }

    @PutMapping
    ResponseResult updateArticle(@RequestBody ArticleUpdateDTO articleUpdateDTO){
        return articleService.updateArticle(articleUpdateDTO);
    }

    // 删除Tag
    @DeleteMapping("/{ids}")
    ResponseResult deleteTag(@PathVariable Long[] ids){
        return articleService.deleteArticle(ids);
    }

}
