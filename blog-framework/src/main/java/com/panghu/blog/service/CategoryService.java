package com.panghu.blog.service;

import com.panghu.blog.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.vo.PageVO;

/**
* @author xhu-zfx
* @description 针对表【blog_category(分类表)】的数据库操作Service
* @createDate 2022-09-08 16:19:35
*/
public interface CategoryService extends IService<Category> {

    ResponseResult listAllCategoryHasArticle();

    ResponseResult listAllCategory();

    PageVO listPageCategory(String name, String status, Integer pageNum, Integer pageSize);


    int deleteCategoryByIds(Long[] ids);
}
