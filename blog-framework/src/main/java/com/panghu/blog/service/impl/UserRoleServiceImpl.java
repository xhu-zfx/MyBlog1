package com.panghu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panghu.blog.domain.entity.UserRole;
import com.panghu.blog.service.UserRoleService;
import com.panghu.blog.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author xhu-zfx
* @description 针对表【blog_sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2022-10-05 16:26:28
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

    @Override
    public List<Long> listRoleById(Long id) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        List<Long> roleIdList = list(queryWrapper)
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        return roleIdList;
    }

    @Transactional
    @Override
    public boolean updateUserRole(Long userId, List<Long> roleIds) {
        // 先把原数据全部删除
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,userId);
        getBaseMapper().delete(queryWrapper);
        // 如果修改后无role，直接返回
        if (roleIds.size() == 0) return true;
        // 插入新关系到user_role表
        List<UserRole> userRoleList = roleIds
                .stream()
                .map(roleId -> new UserRole(userId, roleId))
                .collect(Collectors.toList());
        boolean saveBatch = saveBatch(userRoleList);
        return saveBatch;
    }
}




