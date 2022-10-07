package com.panghu.blog.service;

import com.panghu.blog.domain.dto.RoleChangeStatusDTO;
import com.panghu.blog.domain.dto.RoleInsertDTO;
import com.panghu.blog.domain.dto.RoleUpdateDTO;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.panghu.blog.domain.vo.PageVO;
import com.panghu.blog.domain.vo.RoleListVO;

import java.util.List;

/**
* @author xhu-zfx
* @description 针对表【blog_sys_role(角色信息表)】的数据库操作Service
* @createDate 2022-09-24 17:53:44
*/
public interface RoleService extends IService<Role> {

    List<String> listRolesById(Long id);

    PageVO listPageRole(String status, String roleName, Integer pageNum, Integer pageSize);

    boolean changeStatus(RoleChangeStatusDTO roleChangeStatusDTO);

    boolean insertRole(RoleInsertDTO roleInsertDTO);

    RoleListVO getRoleById(Long id);

    boolean updateRole(RoleUpdateDTO roleUpdateDTO);

    int deleteRole(Long id);

    List<Role> listAllRole();
}
