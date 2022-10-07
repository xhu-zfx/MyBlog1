package com.panghu.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panghu.blog.domain.entity.Link;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.vo.LinkListVO;
import com.panghu.blog.domain.vo.PageVO;
import com.panghu.blog.service.LinkService;
import com.panghu.blog.mapper.LinkMapper;
import com.panghu.blog.util.BeanCopyUtils;
import com.panghu.blog.constant.Consts;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
* @author xhu-zfx
* @description 针对表【blog_link(友链)】的数据库操作Service实现
* @createDate 2022-09-08 16:19:35
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Override
    public ResponseResult listAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, Consts.LINK_STATUS_NORMAL);
        List<LinkListVO> linkListVOS = BeanCopyUtils.copyBeanList(list(queryWrapper), LinkListVO.class);
        return ResponseResult.okResult(linkListVOS);
    }

    @Override
    public PageVO listPageLink(String name, String status, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(!StrUtil.isBlankIfStr(status),Link::getStatus,status)
                .like(!StrUtil.isBlankIfStr(name),Link::getStatus,name);
        Page<Link> page=new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<LinkListVO> linkListVOS = BeanCopyUtils.copyBeanList(page.getRecords(), LinkListVO.class);
        return new PageVO(linkListVOS,page.getTotal());
    }

    @Override
    public int deleteLinkByIds(Long[] ids) {
        int deleteBatch = getBaseMapper().deleteBatchIds(Arrays.asList(ids));
        return deleteBatch;
    }
}




