package com.panghu.blog.controller;

import com.panghu.blog.domain.dto.LinkInsertDTO;
import com.panghu.blog.domain.dto.LinkUpdateDTO;
import com.panghu.blog.domain.entity.Link;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.vo.LinkListVO;
import com.panghu.blog.domain.vo.PageVO;
import com.panghu.blog.enums.AppHttpCodeEnum;
import com.panghu.blog.service.LinkService;
import com.panghu.blog.util.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.panghu.blog.enums.AppHttpCodeEnum.SYSTEM_ERROR;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/6 10:54
 * @description
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Resource
    LinkService linkService;

    @GetMapping("/list")
    ResponseResult listLink(String name, String status, Integer pageNum , Integer pageSize){
        PageVO pageVO  = linkService.listPageLink(name,status,pageNum,pageSize);
        return ResponseResult.okResult(pageVO);
    }

    @PostMapping
    ResponseResult insertLink(@RequestBody LinkInsertDTO linkInsertDTO){
        Link link = BeanCopyUtils.copyBeanSingle(linkInsertDTO, Link.class);
        return linkService.save(link) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @GetMapping("/{id}")
    ResponseResult getLinkById(@PathVariable Long id){
        Link link = linkService.getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBeanSingle(link, LinkListVO.class));
    }

    @PutMapping
    ResponseResult updateLink(@RequestBody LinkUpdateDTO linkUpdateDTO){
        Link link = BeanCopyUtils.copyBeanSingle(linkUpdateDTO, Link.class);
        return linkService.updateById(link) ? ResponseResult.okResult(): ResponseResult.errorResult(SYSTEM_ERROR);
    }
    @DeleteMapping("/{ids}")
    ResponseResult deleteLinkByIds(@PathVariable Long[] ids){
        return linkService.deleteLinkByIds(ids) >= 1 ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);

    }
}
