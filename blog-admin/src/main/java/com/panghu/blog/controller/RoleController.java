package com.panghu.blog.controller;

import com.panghu.blog.domain.dto.RoleChangeStatusDTO;
import com.panghu.blog.domain.dto.RoleInsertDTO;
import com.panghu.blog.domain.dto.RoleUpdateDTO;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.Role;
import com.panghu.blog.domain.vo.PageVO;
import com.panghu.blog.domain.vo.RoleListVO;
import com.panghu.blog.service.RoleService;
import com.panghu.blog.util.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.panghu.blog.enums.AppHttpCodeEnum.SYSTEM_ERROR;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/3 17:28
 * @description
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Resource
    RoleService roleService;

    @GetMapping("/list")
    ResponseResult listRole(String status,String roleName,Integer pageNum,Integer pageSize){
        PageVO pageVO = roleService.listPageRole(status, roleName, pageNum, pageSize);
        return ResponseResult.okResult(pageVO);
    }

    @GetMapping("/listAllRole")
    ResponseResult listAllRole(){
        List<Role> roleList = roleService.listAllRole();
        List<RoleListVO> roleListVOS = BeanCopyUtils.copyBeanList(roleList, RoleListVO.class);
        return ResponseResult.okResult(roleListVOS);
    }

    @PutMapping("/changeStatus")
    ResponseResult changeStatus(@RequestBody RoleChangeStatusDTO roleChangeStatusDTO){
        return roleService.changeStatus(roleChangeStatusDTO) ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @PostMapping
    ResponseResult insertRole(@RequestBody RoleInsertDTO roleInsertDTO){
        return roleService.insertRole(roleInsertDTO) ?  ResponseResult.okResult(): ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @GetMapping("/{id}")
    ResponseResult getRoleById(@PathVariable Long id){
        return ResponseResult.okResult(roleService.getRoleById(id)) ;
    }

    @PutMapping
    ResponseResult updateRole(@RequestBody RoleUpdateDTO roleUpdateDTO){
         return roleService.updateRole(roleUpdateDTO) ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @DeleteMapping("/{id}")
    ResponseResult deleteRole(@PathVariable Long id){
        return roleService.deleteRole(id) == 1 ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);
    }
}
