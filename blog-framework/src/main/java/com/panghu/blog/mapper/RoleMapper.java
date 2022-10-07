package com.panghu.blog.mapper;

import com.panghu.blog.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author ZQ
* @description 针对表【blog_sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2022-09-24 17:53:44
* @Entity com.panghu.blog.domain.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<String> listRolesByUserId(Long id);
}




