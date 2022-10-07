package com.panghu.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panghu.blog.domain.entity.Article;
import com.panghu.blog.domain.entity.Category;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.vo.CategoryListVO;
import com.panghu.blog.domain.vo.PageVO;
import com.panghu.blog.mapper.ArticleMapper;
import com.panghu.blog.service.CategoryService;
import com.panghu.blog.mapper.CategoryMapper;
import com.panghu.blog.util.BeanCopyUtils;
import com.panghu.blog.constant.Consts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.panghu.blog.constant.Consts.*;

/**
* @author xhu-zfx
* @description 针对表【blog_category(分类表)】的数据库操作Service实现
* @createDate 2022-09-08 16:19:35
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    // 循环依赖
//    @Resource
//    private ArticleService articleService;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public ResponseResult listAllCategoryHasArticle() {
        // 查询文章表,状态为已发布的文章
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper.eq(Article::getStatus, Consts.BLOG_STATUS_RELEASE);
        List<Article> articleList = articleMapper.selectList(articleQueryWrapper);
//        List<Article> articleList = articleService.list(articleQueryWrapper);
        // 获取文章分类id,去重
        Set<Long> categoryIds = articleList
                .stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        // 根据categoryIds查询分类表,并且只查询状态正常的
        List<Category> categoryList = listByIds(categoryIds)
                .stream()
                .filter(category -> CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 返回封装vo
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(categoryList, CategoryListVO.class));
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, CATEGORY_STATUS_NORMAL);
        List<CategoryListVO> categoryListVOList = BeanCopyUtils.copyBeanList(list(queryWrapper), CategoryListVO.class);
        return ResponseResult.okResult(categoryListVOList);
    }

    @Override
    public PageVO listPageCategory(String name, String status, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(!StrUtil.isBlankIfStr(status),Category::getStatus, status)
                .like(!StrUtil.isBlankIfStr(name),Category::getName, name);
        Page<Category> page=new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<CategoryListVO> categoryListVOS = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryListVO.class);
        return new PageVO(categoryListVOS,page.getTotal());
    }

    @Override
    public int deleteCategoryByIds(Long[] ids) {
        int deleteBatchIds = getBaseMapper().deleteBatchIds(Arrays.asList(ids));
        return deleteBatchIds;
    }

}




