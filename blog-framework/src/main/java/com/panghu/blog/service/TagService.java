package com.panghu.blog.service;

import com.panghu.blog.domain.dto.TagDTO;
import com.panghu.blog.domain.dto.TagUpdateDTO;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ZQ
* @description 针对表【blog_tag(标签)】的数据库操作Service
* @createDate 2022-09-24 15:41:48
*/
public interface TagService extends IService<Tag> {

    ResponseResult listPageTag(TagDTO tagDTO, Integer pageNum, Integer pageSize);

    ResponseResult insertTag(TagDTO tagDTO);

    ResponseResult deleteTag(Long[] ids);
    
    ResponseResult detailTag(Long id);

    ResponseResult updateTag(TagUpdateDTO tagUpdateDTO);

    ResponseResult listAllTag();
}
