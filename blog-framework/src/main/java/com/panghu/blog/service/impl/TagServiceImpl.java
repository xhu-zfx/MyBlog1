package com.panghu.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panghu.blog.constant.Consts;
import com.panghu.blog.domain.dto.TagDTO;
import com.panghu.blog.domain.dto.TagUpdateDTO;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.Tag;
import com.panghu.blog.domain.vo.PageVO;
import com.panghu.blog.domain.vo.TagVO;
import com.panghu.blog.enums.AppHttpCodeEnum;
import com.panghu.blog.service.TagService;
import com.panghu.blog.mapper.TagMapper;
import com.panghu.blog.util.BeanCopyUtils;
import com.panghu.blog.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
* @author ZQ
* @description 针对表【blog_tag(标签)】的数据库操作Service实现
* @createDate 2022-09-24 15:41:48
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public ResponseResult listPageTag(TagDTO tagDTO, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        // 条件查询：根据name、remark模糊查询，
        queryWrapper
                .like(!StrUtil.isBlankIfStr(tagDTO.getName()),Tag::getName, tagDTO.getName())
                .like(!StrUtil.isBlankIfStr(tagDTO.getRemark()),Tag::getRemark, tagDTO.getRemark())
                .orderByDesc(Tag::getCreateTime);
        Page<Tag> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<TagVO> tagVOList = BeanCopyUtils.copyBeanList(page.getRecords(), TagVO.class);
        return ResponseResult.okResult(new PageVO(tagVOList,page.getTotal()));
    }

    @Override
    public ResponseResult insertTag(TagDTO tagDTO) {
        // 获取tagListDTO中的 name 和 remark 字段，手动封装 创建时间、更新时间、创建人、更新人 字段
        Tag tag = BeanCopyUtils.copyBeanSingle(tagDTO, Tag.class);
        boolean updateSuccess = saveOrUpdate(tag);
        if (!updateSuccess) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_TAG_INSERT);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long[] ids) {
        int updateSuccess = getBaseMapper().deleteBatchIds(Arrays.asList(ids));
        if (updateSuccess<=0) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_TAG_DELETE);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult detailTag(Long id) {
        Tag tag = getById(id);
        TagVO tagVO = BeanCopyUtils.copyBeanSingle(tag, TagVO.class);
        return ResponseResult.okResult(tagVO);
    }

    @Override
    public ResponseResult updateTag(TagUpdateDTO tagUpdateDTO) {
        // 将 name 和 remark 赋值给tag，并手动封装 更新时间、更新人 字段
        Tag tag = BeanCopyUtils.copyBeanSingle(tagUpdateDTO, Tag.class);
        boolean updateSuccess = updateById(tag);
        if (!updateSuccess) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_TAG_UPDATE);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        List<TagVO> tagVOList = BeanCopyUtils.copyBeanList(list(), TagVO.class);
        return ResponseResult.okResult(tagVOList);
    }


}




