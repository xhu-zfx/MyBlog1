package com.panghu.blog.service;

import com.panghu.blog.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.vo.PageVO;

/**
* @author xhu-zfx
* @description 针对表【blog_link(友链)】的数据库操作Service
* @createDate 2022-09-08 16:19:35
*/
public interface LinkService extends IService<Link> {

    ResponseResult listAllLink();

    PageVO listPageLink(String name, String status, Integer pageNum, Integer pageSize);

    int deleteLinkByIds(Long[] ids);
}
