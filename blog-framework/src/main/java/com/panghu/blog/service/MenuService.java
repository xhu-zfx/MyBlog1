package com.panghu.blog.service;

import com.panghu.blog.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.vo.MenuTreeVO;
import com.panghu.blog.domain.vo.MenuVO;

import java.util.List;

/**
* @author ZQ
* @description 针对表【blog_sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2022-09-24 17:53:44
*/
public interface MenuService extends IService<Menu> {

    List<String> listPermissionsById(Long id);

    List<MenuVO> listMenuTreeByUserId(Long id);

    ResponseResult listMenuTree(String status, String menuName);

    ResponseResult insertMenu(Menu menu);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long id);

    List<Menu> listAllMenuTree();
}
