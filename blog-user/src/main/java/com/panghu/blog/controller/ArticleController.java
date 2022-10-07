package com.panghu.blog.controller;

import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/7 16:36
 * @description
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章控制器",description = "文章接口")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @ApiOperation(value = "查询热门文章接口")
    public ResponseResult hotArtivleList(){
        return articleService.listHotArticle();
    }

    @GetMapping("/articleList")
    @ApiOperation(value = "分类查询文章接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类id", required = false),
            @ApiImplicitParam(name = "pageNum", value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
    public ResponseResult articleList(Long categoryId,Integer pageNum,Integer pageSize){
        return articleService.listArticleByCategory(categoryId,pageSize,pageNum);
    }

    @GetMapping("/{id}")
    public ResponseResult getArtivleDetail(@PathVariable("id") Long id){
        return articleService.getArtivleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
