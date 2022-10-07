package com.panghu.blog.mapper;

import com.panghu.blog.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panghu.blog.domain.vo.MenuTreeVO;
import com.panghu.blog.domain.vo.MenuVO;

import java.util.List;

/**
* @author ZQ
* @description 针对表【blog_sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2022-09-24 17:53:44
* @Entity com.panghu.blog.domain.entity.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> listPermissonByUserId(Long id);

    List<MenuVO> listAllMenuVO();

    List<MenuVO> listMenuVOByUserId(Long id);

    List<MenuVO> listMenuVOChildren(Long id);

    List<MenuTreeVO> listMenuTreeVOChildren(Long id);
}




