package com.panghu.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panghu.blog.constant.Consts;
import com.panghu.blog.domain.dto.RoleChangeStatusDTO;
import com.panghu.blog.domain.dto.RoleInsertDTO;
import com.panghu.blog.domain.dto.RoleUpdateDTO;
import com.panghu.blog.domain.entity.ArticleTag;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.Role;
import com.panghu.blog.domain.entity.RoleMenu;
import com.panghu.blog.domain.vo.PageVO;
import com.panghu.blog.domain.vo.RoleListVO;
import com.panghu.blog.service.RoleMenuService;
import com.panghu.blog.service.RoleService;
import com.panghu.blog.mapper.RoleMapper;
import com.panghu.blog.util.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author xhu-zfx
* @description 针对表【blog_sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2022-09-24 17:53:44
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Resource
    RoleMenuService roleMenuService;

    @Override
    public List<String> listRolesById(Long id) {
        return getBaseMapper().listRolesByUserId(id);
    }

    @Override
    public PageVO listPageRole(String status, String roleName, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(!StrUtil.isBlankIfStr(status), Role::getStatus, status)
                .like(!StrUtil.isBlankIfStr(roleName), Role::getRoleName, roleName)
                .orderByAsc(Role::getRoleSort);
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<RoleListVO> roleVOList = BeanCopyUtils.copyBeanList(page.getRecords(), RoleListVO.class);
        return new PageVO(roleVOList,page.getTotal());
    }

    @Override
    public boolean changeStatus(RoleChangeStatusDTO roleChangeStatusDTO) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(Role::getId,roleChangeStatusDTO.getRoleId())
                .set(!StrUtil.isBlankIfStr(roleChangeStatusDTO.getStatus()), Role::getStatus, roleChangeStatusDTO.getStatus());
        return update(updateWrapper);
    }

    @Override
    @Transactional
    public boolean insertRole(RoleInsertDTO roleInsertDTO) {
        // 插入基本数据到role表
        Role role = BeanCopyUtils.copyBeanSingle(roleInsertDTO, Role.class);
        boolean save = save(role);
        // 插入菜单权限集合到role_menu表
        List<RoleMenu> roleMenuList = roleInsertDTO.getMenuIds()
                .stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        boolean saveBatch = roleMenuService.saveBatch(roleMenuList);
        return saveBatch && save;
    }

    @Override
    public RoleListVO getRoleById(Long id) {
        Role role = getById(id);
        RoleListVO roleVO = BeanCopyUtils.copyBeanSingle(role, RoleListVO.class);
        return roleVO;
    }

    @Override
    @Transactional
    public boolean updateRole(RoleUpdateDTO roleUpdateDTO) {
        // 更新基本信息到role表
        Role role = BeanCopyUtils.copyBeanSingle(roleUpdateDTO, Role.class);
        updateById(role);
        // 1. ArticleTag表中有相关数据的话，将ArticleTag表中articleId为该id的数据全部删除
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,roleUpdateDTO.getId());
        roleMenuService.getBaseMapper().delete(queryWrapper);
        // menuIds到role_menu表
        List<RoleMenu> roleMenuList = roleUpdateDTO.getMenuIds()
                .stream()
                .map(menuId -> new RoleMenu(roleUpdateDTO.getId(), menuId))
                .collect(Collectors.toList());
        if (roleMenuList.size() > 0) return roleMenuService.saveBatch(roleMenuList);
        return false;
    }

    @Override
    public int deleteRole(Long id) {
        return getBaseMapper().deleteById(id);
    }

    @Override
    public List<Role> listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus,Consts.ROLE_STATUS_NORMAL);
        return list(queryWrapper);
    }
}




