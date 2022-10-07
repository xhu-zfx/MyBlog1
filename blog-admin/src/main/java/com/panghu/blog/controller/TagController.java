package com.panghu.blog.controller;

import com.panghu.blog.domain.dto.TagDTO;
import com.panghu.blog.domain.dto.TagUpdateDTO;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/24 15:45
 * @description
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Resource
    TagService tagService;

    // 查询Tag
    @GetMapping("/listTag")
    ResponseResult listTag(Integer pageNum , Integer pageSize , TagDTO tagDTO){
        return tagService.listPageTag(tagDTO,pageNum,pageSize);
    }

    @GetMapping("/listAllTag")
    ResponseResult listAllTag(){
        return tagService.listAllTag();
    }

    // 新增Tag
    @PostMapping("/insertTag")
    ResponseResult insertTag(@RequestBody TagDTO tagDTO){
        return tagService.insertTag(tagDTO);
    }

    // 删除Tag
    @DeleteMapping("/deleteTag/{ids}")
    ResponseResult deleteTag(@PathVariable Long[] ids){
        return tagService.deleteTag(ids);
    }

    // 查询Tag详情
    @GetMapping("/detailTag/{id}")
    ResponseResult detailTag(@PathVariable("id") Long id){
        return tagService.detailTag(id);
    }

    // 修改Tag
    @PutMapping("/updateTag")
    ResponseResult updateTag(@RequestBody TagUpdateDTO tagUpdateDTO){
        return tagService.updateTag(tagUpdateDTO);
    }






}
