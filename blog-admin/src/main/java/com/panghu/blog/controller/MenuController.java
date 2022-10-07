package com.panghu.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.panghu.blog.domain.dto.RoleUpdateDTO;
import com.panghu.blog.domain.entity.Menu;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.RoleMenu;
import com.panghu.blog.domain.vo.MenuTreeVO;
import com.panghu.blog.domain.vo.RoleMenuTreeVO;
import com.panghu.blog.service.MenuService;
import com.panghu.blog.service.RoleMenuService;
import com.panghu.blog.util.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/2 17:19
 * @description
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Resource
    MenuService menuService;
    
    @Resource
    RoleMenuService roleMenuService;

    @GetMapping("/list")
    ResponseResult list(String status,String menuName){
        return menuService.listMenuTree(status,menuName);
    }

    @PostMapping
    ResponseResult insertMenu(@RequestBody Menu menu){
        return menuService.insertMenu(menu);
    }

    @GetMapping("/{id}")
    ResponseResult getMenuById(@PathVariable Long id){
        return menuService.getMenuById(id);
    }

    @PutMapping
    ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{id}")
    ResponseResult deleteMenu(@PathVariable Long id){
        return menuService.deleteMenu(id);
    }

    @GetMapping("/treeselect")
    ResponseResult treeselect(){
        List<MenuTreeVO> menuTreeVOS = BeanCopyUtils.copyBeanList(menuService.listAllMenuTree(), MenuTreeVO.class);
        return ResponseResult.okResult(menuTreeVOS);
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    ResponseResult roleMenuTreeselect(@PathVariable Long id){
        // 查询所有菜单树
        List<MenuTreeVO> menuTreeVOS = BeanCopyUtils.copyBeanList(menuService.listAllMenuTree(), MenuTreeVO.class);
        // 查询角色所关联的菜单权限id列表
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        List<Long> listMenuByRoleId = roleMenuService.list(queryWrapper)
                .stream()
                .map(roleMenu -> roleMenu.getMenuId())
                .collect(Collectors.toList());
        return ResponseResult.okResult(new RoleMenuTreeVO(menuTreeVOS,listMenuByRoleId));
    }


}
