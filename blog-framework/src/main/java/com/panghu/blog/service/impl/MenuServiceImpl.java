package com.panghu.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panghu.blog.constant.Consts;
import com.panghu.blog.domain.entity.Menu;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.vo.MenuListVO;
import com.panghu.blog.domain.vo.MenuTreeVO;
import com.panghu.blog.domain.vo.MenuVO;
import com.panghu.blog.enums.AppHttpCodeEnum;
import com.panghu.blog.service.MenuService;
import com.panghu.blog.mapper.MenuMapper;
import com.panghu.blog.util.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author xhu-zfx
* @email <756867768@qq.com>
* @description 针对表【blog_sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2022-09-24 17:53:44
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    // 查询权限
    @Override
    public List<String> listPermissionsById(Long id) {
        if (id.equals(1L)){
            // 如果为超级管理员，则返回所有状态为正常的权限
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(Menu::getStatus, Consts.MENU_STATUS_NORMAL);
            queryWrapper
                    .eq(Menu::getMenuType, Consts.MENU_TYPE_MENU)
                    .or()
                    .eq(Menu::getMenuType, Consts.MENU_TYPE_BUTTON);
            List<String> permissonList = list(queryWrapper)
                    .stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return permissonList;
        }
        // 非超级管理员需要根据id查询role，再根据role查询对应的menu的perms集合
        return getBaseMapper().listPermissonByUserId(id);
    }

    // 查询动态路由菜单树
    @Override
    public List<MenuVO> listMenuTreeByUserId(Long id) {
        List<MenuVO> menuVOList = null;
        // 如果是管理员，返回所有菜单
        if (id.equals(1L)){
            menuVOList = getBaseMapper().listAllMenuVO();
        } else {
            menuVOList = getBaseMapper().listMenuVOByUserId(id);
        }
        return treeMenuVOList(menuVOList);
    }

    @Override
    public ResponseResult listMenuTree(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(!StrUtil.isBlankIfStr(status),Menu::getStatus,status)
                .like(!StrUtil.isBlankIfStr(menuName),Menu::getMenuName,menuName)
                .orderByAsc(Menu::getParentId)
                .orderByAsc(Menu::getOrderNum);

        List<Menu> menuList = list(queryWrapper);
        List<MenuListVO> menuListVOS = BeanCopyUtils.copyBeanList(menuList, MenuListVO.class);
        return ResponseResult.okResult(menuListVOS);
    }

    @Override
    public ResponseResult insertMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        Menu menu = getById(id);
        MenuListVO menuListVO = BeanCopyUtils.copyBeanSingle(menu, MenuListVO.class);
        return ResponseResult.okResult(menuListVO);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if (menu.getParentId().equals(menu.getId())) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_MENU_UPDATE_PARENT);
        boolean updateSuccess = updateById(menu);
        if (!updateSuccess) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_MENU_UPDATE);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long id) {
        // 查询有没有菜单的父菜单为它，如果有，不能删除
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,id);
        List<Menu> menuList = list(queryWrapper);
        if (menuList.size() > 0) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_MENU_DELETE);
        getBaseMapper().deleteById(id);
        return ResponseResult.okResult();
    }

    // 查询所有菜单树
    @Override
    public List<Menu> listAllMenuTree() {
        // 查询所有数据
        // 获得一级数据：parentId==0
        // 获得二级数据：menuType in 'C,M' 并且 parentId
        // 获得三级数据：menuType = F 并且 parentId

        // 查询所有数据
        List<Menu> menuList = list();
        List<Menu> parentMenuList = builderMenuTree(menuList, 0L);
        return parentMenuList;

//        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper
//                .eq(Menu::getParentId, 0L)
//                .orderByAsc(Menu::getOrderNum);
//        List<Menu> menuList = list(queryWrapper);
//        List<MenuTreeVO> menuTreeVOList = menuList
//                .stream()
//                .map(menu -> new MenuTreeVO(menu.getId(), menu.getMenuName(), menu.getParentId()))
//                .collect(Collectors.toList());
//        return treeAllMenu(menuTreeVOList);
    }


    // 根据menuVO对象的id，查询所有parent_id为它的对象，将这些对象存入父对象的children中，构建parent-children树
    // 注意 ! 父子关系最多仅存在二级，不涉及递归
    private List<MenuVO> treeMenuVOList(List<MenuVO> menuVOList) {
        List<MenuVO> menuVOTree = menuVOList
                .stream()
                .map(menuVO -> menuVO.setChildren(getBaseMapper().listMenuVOChildren(menuVO.getId())))
                .collect(Collectors.toList());
        return menuVOTree;
    }

    private List<MenuTreeVO> treeAllMenu(List<MenuTreeVO> menuTreeVOList) {
        List<MenuTreeVO> list = menuTreeVOList
                .stream()
                .map(menuTreeVO -> menuTreeVO.setChildren(getBaseMapper().listMenuTreeVOChildren(menuTreeVO.getId())))
                .collect(Collectors.toList());
        return list;
    }


    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}




