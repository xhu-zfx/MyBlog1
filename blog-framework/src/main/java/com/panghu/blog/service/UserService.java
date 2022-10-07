package com.panghu.blog.service;

import com.panghu.blog.domain.dto.UserInsertDTO;
import com.panghu.blog.domain.dto.UserUpdateDTO;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.panghu.blog.domain.vo.PageVO;

/**
* @author xhu-zfx
* @description 针对表【blog_sys_user(用户表)】的数据库操作Service
* @createDate 2022-09-11 10:33:53
*/
public interface UserService extends IService<User> {

    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    PageVO listPageUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);
    public boolean checkUserNameExist( String userName);
    public boolean checkUserEmailExist( String email);
    public boolean checkUserPhonenumberExist(String phonenumber);

    boolean insertUser(UserInsertDTO userInsertDTO);

    int deleteUser(Long[] id);

    boolean updateUser(UserUpdateDTO userUpdateDTO);
}
