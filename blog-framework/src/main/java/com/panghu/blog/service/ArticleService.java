package com.panghu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panghu.blog.domain.dto.ArticleInsertDTO;
import com.panghu.blog.domain.dto.ArticleListDTO;
import com.panghu.blog.domain.dto.ArticleUpdateDTO;
import com.panghu.blog.domain.entity.Article;
import com.panghu.blog.domain.entity.ResponseResult;

/**
* @author xhu-zfx
* @description 针对表【blog_article(文章表)】的数据库操作Service
* @createDate 2022-09-06 16:20:51
*/

public interface ArticleService extends IService<Article> {

    ResponseResult listHotArticle();

    ResponseResult listArticleByCategory(Long categoryId, Integer pageSize, Integer pageNum);

    ResponseResult getArtivleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult insertArticle(ArticleInsertDTO articleInsertDTO);

    ResponseResult listArticle(Integer pageNum, Integer pageSize, ArticleListDTO articleListDTO);

    ResponseResult getArticleById(Long id);

    ResponseResult updateArticle(ArticleUpdateDTO articleUpdateDTO);

    ResponseResult deleteArticle(Long[] ids);
}
