package com.panghu.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panghu.blog.domain.entity.ArticleTag;
import com.panghu.blog.service.ArticleTagService;
import com.panghu.blog.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author ZQ
* @description 针对表【blog_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2022-09-29 16:47:30
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




