package com.panghu.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panghu.blog.constant.RedisConsts;
import com.panghu.blog.domain.dto.ArticleInsertDTO;
import com.panghu.blog.domain.dto.ArticleListDTO;
import com.panghu.blog.domain.dto.ArticleUpdateDTO;
import com.panghu.blog.domain.entity.ArticleTag;
import com.panghu.blog.domain.entity.Category;
import com.panghu.blog.domain.vo.*;
import com.panghu.blog.enums.AppHttpCodeEnum;
import com.panghu.blog.mapper.ArticleMapper;
import com.panghu.blog.domain.entity.Article;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.service.ArticleService;
import com.panghu.blog.service.ArticleTagService;
import com.panghu.blog.service.CategoryService;
import com.panghu.blog.constant.Consts;
import com.panghu.blog.util.BeanCopyUtils;
import com.panghu.blog.util.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.panghu.blog.enums.AppHttpCodeEnum.SYSTEM_ERROR;
import static com.panghu.blog.util.BeanCopyUtils.copyBeanSingle;
import static com.panghu.blog.util.BeanCopyUtils.copyBeanList;
import static com.panghu.blog.constant.Consts.ERROR_CATEGORY_ID_NOT_EXIST;

/**
* @author xhu-zfx
* @description 针对表【blog_article(文章表)】的数据库操作Service实现
* @createDate 2022-09-06 16:20:51
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Resource
    private CategoryService categoryService;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private RedisCache redisCache;

    // 查询前十的热门文章
    @Override
    public ResponseResult listHotArticle() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 查询条件：已发布、按照浏览量进行排序、最多查询10条
        queryWrapper.eq(Article::getStatus, Consts.BLOG_STATUS_RELEASE).orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);
        List<Article> articleList = page.getRecords();
        articleList.forEach(article -> {
            // 从redis中读取实时viewcount
            article.setViewCount(getViewCountFromRedis(article.getId()));
        });
        List<HotArticleVO> articleVos = copyBeanList(articleList, HotArticleVO.class);
        return ResponseResult.okResult(articleVos);
    }
    // 查询文章列表
    @Override
    public ResponseResult listArticleByCategory(Long categoryId, Integer pageSize, Integer pageNum) {
        // 查询条件：categoryId是否传入,传入即根据查询、查询正式发布的文章、置顶的文章优先显示
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                // categoryId是否传入,传入即根据查询
                .eq(ObjectUtil.isNotNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId)
                // 查询正式发布的文章
                .eq(Article::getStatus, Consts.BLOG_STATUS_RELEASE)
                // 根据isTop降序
                .orderByDesc(Article::getIsTop)
                // 根据CreateTime排序
                .orderByDesc(Article::getCreateTime);
        // 分页查询
        if (ObjectUtil.isNull(pageSize)||ObjectUtil.isNull(pageNum)) return ResponseResult.errorResult(SYSTEM_ERROR, Consts.ERROR_PAGE_PARAM);
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
//        articles.stream()
//                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
//                .collect(Collectors.toList());
        articles.forEach(article -> {
            article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
            // 从redis中读取实时viewcount
            article.setViewCount(getViewCountFromRedis(article.getId()));
        });
        List<ArticleListVO> articleVOList = copyBeanList(articles, ArticleListVO.class);
        return ResponseResult.okResult(new PageVO(articleVOList,page.getTotal()));
    }

    // 查询文章详情
    @Override
    public ResponseResult getArtivleDetail(Long id) {
        // 根据id查询
        Article article = getById(id);
        // 从redis中读取实时viewcount
        article.setViewCount(getViewCountFromRedis(id));
        // 转换成vo
        ArticleDetailVO articleDetailVo = copyBeanSingle(article, ArticleDetailVO.class);
        // 根据分类id查询分类名
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if (BeanUtil.isEmpty(category)) return ResponseResult.errorResult(SYSTEM_ERROR,ERROR_CATEGORY_ID_NOT_EXIST);
        articleDetailVo.setCategoryName(category.getName());
        return ResponseResult.okResult(articleDetailVo);
    }

    // 实时更新viewcount到redis
    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(RedisConsts.BLOG_USER_PREFIX +RedisConsts.ARTICLE_VIEW_COUNT_KEY,id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult insertArticle(ArticleInsertDTO articleInsertDTO) {
        Article article = copyBeanSingle(articleInsertDTO, Article.class);
        save(article);
        List<ArticleTag> articleTagList = articleInsertDTO.getTags()
                .stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTagList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listArticle(Integer pageNum, Integer pageSize, ArticleListDTO articleListDTO) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 传入即根据 Title、Summary模糊查询
        queryWrapper
                .like(!StrUtil.isBlankIfStr(articleListDTO.getTitle()),Article::getTitle,articleListDTO.getTitle())
                .like(!StrUtil.isBlankIfStr(articleListDTO.getSummary()),Article::getSummary,articleListDTO.getSummary())
                .orderByDesc(Article::getCreateTime);
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Article> articleList = copyBeanList(page.getRecords(), Article.class);
        return ResponseResult.okResult(new PageVO(articleList,page.getTotal()));
    }

    @Override
    public ResponseResult getArticleById(Long id) {
        Article article = getById(id);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> articleTagList = articleTagService.list(queryWrapper);
        List<Long> tagList = articleTagList
                .stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        ArticleUpdateVO articleUpdateVO = copyBeanSingle(article, ArticleUpdateVO.class);
        articleUpdateVO.setTags(tagList);
        return ResponseResult.okResult(articleUpdateVO);
    }

    @Override
    @Transactional
    public ResponseResult updateArticle(ArticleUpdateDTO articleUpdateDTO) {
        // 更新数据到Article表
        Article article = copyBeanSingle(articleUpdateDTO, Article.class);
        boolean update = updateById(article);
        // 更新数据到ArticleTag表
        // 1. ArticleTag表中有相关数据的话，将ArticleTag表中articleId为该id的数据全部删除
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,articleUpdateDTO.getId());
        int delete = articleTagService.getBaseMapper().delete(queryWrapper);
        // 2. 插入字段
        List<ArticleTag> articleTagList = articleUpdateDTO.getTags()
                .stream()
                .map(tagId -> new ArticleTag(articleUpdateDTO.getId(), tagId))
                .collect(Collectors.toList());
        boolean saveBatch = false;
        if (articleTagList.size() > 0) {
            saveBatch = articleTagService.saveBatch(articleTagList);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long[] ids) {
        int deleteRows = getBaseMapper().deleteBatchIds(Arrays.asList(ids));
        if (deleteRows <= 0) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_ARTICLE_DELETE);
        return ResponseResult.okResult();
    }

    // 从redis中读取实时viewcount
    Long getViewCountFromRedis(Long id){
        Integer articleViewCount = redisCache.getCacheMapValue(RedisConsts.BLOG_USER_PREFIX + RedisConsts.ARTICLE_VIEW_COUNT_KEY, id.toString());
        return articleViewCount.longValue();
    }
}




