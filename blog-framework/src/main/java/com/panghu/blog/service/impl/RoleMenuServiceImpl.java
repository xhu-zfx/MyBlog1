package com.panghu.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panghu.blog.domain.entity.RoleMenu;
import com.panghu.blog.service.RoleMenuService;
import com.panghu.blog.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author ZQ
* @description 针对表【blog_sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2022-10-04 10:47:45
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




