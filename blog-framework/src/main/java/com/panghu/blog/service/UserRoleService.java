package com.panghu.blog.service;

import com.panghu.blog.domain.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ZQ
* @description 针对表【blog_sys_user_role(用户和角色关联表)】的数据库操作Service
* @createDate 2022-10-05 16:26:28
*/
public interface UserRoleService extends IService<UserRole> {

    List<Long> listRoleById(Long id);

    boolean updateUserRole(Long userId, List<Long> roleIds);
}
