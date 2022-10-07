package com.panghu.blog.controller;

import cn.hutool.core.util.StrUtil;
import com.panghu.blog.domain.dto.UserInsertDTO;
import com.panghu.blog.domain.dto.UserUpdateDTO;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.User;
import com.panghu.blog.domain.vo.PageVO;
import com.panghu.blog.domain.vo.RoleListVO;
import com.panghu.blog.domain.vo.UserDetailVO;
import com.panghu.blog.domain.vo.UserListVO;
import com.panghu.blog.enums.AppHttpCodeEnum;
import com.panghu.blog.exception.SystemException;
import com.panghu.blog.service.RoleService;
import com.panghu.blog.service.UserRoleService;
import com.panghu.blog.service.UserService;
import com.panghu.blog.util.BeanCopyUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.panghu.blog.enums.AppHttpCodeEnum.SYSTEM_ERROR;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/4 18:36
 * @description
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    UserRoleService userRoleService;

    @Resource
    RoleService roleService;

    @GetMapping("/list")
    ResponseResult listUser(Integer pageNum,Integer pageSize, String userName,String phonenumber,String status){
        PageVO pageVO=userService.listPageUser(pageNum,pageSize,userName,phonenumber,status);
        return ResponseResult.okResult(pageVO);
    }

    @PostMapping
    ResponseResult insertUser(@RequestBody UserInsertDTO userInsertDTO){
        if (StrUtil.isBlankIfStr(userInsertDTO.getUserName())) throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        // 数据校验:查询用户名，邮箱，手机号是否存在
        if (userService.checkUserNameExist(userInsertDTO.getUserName()))    throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        if (userService.checkUserEmailExist(userInsertDTO.getEmail()))    throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        if (userService.checkUserPhonenumberExist(userInsertDTO.getPhonenumber()))    throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);

        return userService.insertUser(userInsertDTO) ?  ResponseResult.okResult(): ResponseResult.errorResult(SYSTEM_ERROR);

    }

    @DeleteMapping("/{id}")
    ResponseResult deleteUser(@PathVariable Long[] id){
        return userService.deleteUser(id) >= 1 ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);

    }

    @GetMapping("/{id}")
    ResponseResult getUserDetailById(@PathVariable Long id){
        UserDetailVO userDetailVO=new UserDetailVO();
        // 查询 roleIds用户所关联的角色id集合
        userDetailVO.setRoleIds(userRoleService.listRoleById(id));
        // 查询 user用户信息
        userDetailVO.setUser(BeanCopyUtils.copyBeanSingle(userService.getById(id), UserListVO.class));
        // 查询 roles所有角色的id集合
        userDetailVO.setRoles(BeanCopyUtils.copyBeanList(roleService.listAllRole(), RoleListVO.class));
        return ResponseResult.okResult(userDetailVO);
    }

    @PutMapping
    ResponseResult updateUser(@RequestBody UserUpdateDTO userUpdateDTO){
        boolean updateUser = userService.updateUser(userUpdateDTO);
        return updateUser  ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);

    }
}
