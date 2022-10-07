package com.panghu.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panghu.blog.constant.Consts;
import com.panghu.blog.constant.RedisConsts;
import com.panghu.blog.domain.dto.UserInsertDTO;
import com.panghu.blog.domain.dto.UserUpdateDTO;
import com.panghu.blog.domain.entity.LoginUser;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.User;
import com.panghu.blog.domain.entity.UserRole;
import com.panghu.blog.domain.vo.LoginUserVO;
import com.panghu.blog.domain.vo.PageVO;
import com.panghu.blog.domain.vo.UserInfoVO;
import com.panghu.blog.domain.vo.UserListVO;
import com.panghu.blog.enums.AppHttpCodeEnum;
import com.panghu.blog.exception.SystemException;
import com.panghu.blog.service.UserRoleService;
import com.panghu.blog.service.UserService;
import com.panghu.blog.mapper.UserMapper;
import com.panghu.blog.util.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
* @author xhu-zfx
* @description 针对表【blog_sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-09-11 10:33:53
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    RedisCache redisCache;

    @Resource
    UserRoleService userRoleService;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        // authenticationManager的authenticate最终会调用UserDetailsService的loadUserByUsername方法
        // 我们创建UserDetailsServiceImpl对该方法进行实现即可
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (BeanUtil.isEmpty(authenticate)) throw new RuntimeException(Consts.ERROR_LOGIN_WRONG);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        redisCache.setCacheObject(RedisConsts.BLOG_USER_PREFIX +RedisConsts.LOGIN_USER_KEY+userId,loginUser, RedisConsts.LOGIN_USER_TIME, TimeUnit.DAYS);
        // 将user转换为userInfoVo
        UserInfoVO userInfoVo = BeanCopyUtils.copyBeanSingle(loginUser.getUser(), UserInfoVO.class);
        // 返回LoginUserVo对象
        return ResponseResult.okResult(new LoginUserVO(jwt,userInfoVo));
    }

    @Override
    public ResponseResult logout() {
        // 获取请求头并解密，已封装到SecurityUtils.getLoginUser方法中
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 获取userId
        String userId = loginUser.getUser().getId().toString();
        // 删除redis中的信息
        redisCache.deleteObject(RedisConsts.BLOG_USER_PREFIX +RedisConsts.LOGIN_USER_KEY+userId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 获取userId
        String userId = loginUser.getUser().getId().toString();
        User user = getById(userId);
        UserInfoVO userInfoVO = BeanCopyUtils.copyBeanSingle(user, UserInfoVO.class);
        // 封装UserInfoVO
        return ResponseResult.okResult(userInfoVO);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(User::getId,user.getId())
                .set(User::getAvatar,user.getAvatar())
                .set(User::getEmail,user.getEmail())
                .set(User::getNickName,user.getNickName())
                .set(User::getSex,user.getSex());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User user) {
        // 数据校验:格式判断
        if (StrUtil.isBlankIfStr(user.getUserName()))  throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        if (!checkUserPasswordFormat(user)) throw new SystemException(AppHttpCodeEnum.PASSWORD_FORMAT_ERROR);
        if (!checkUserEmailFormat(user)) throw new SystemException(AppHttpCodeEnum.EMAIL_FORMAT_ERROR);
        // 数据校验:查询用户名，邮箱，手机号是否存在
        if (checkUserNameExist(user.getUserName()))    throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        if (checkUserEmailExist(user.getEmail()))    throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        if (checkUserPhonenumberExist(user.getPhonenumber()))    throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 存入
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public PageVO listPageUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(!StrUtil.isBlankIfStr(phonenumber),User::getPhonenumber,phonenumber)
                .like(!StrUtil.isBlankIfStr(userName),User::getUserName,userName)
                .eq(!StrUtil.isBlankIfStr(status),User::getStatus,status);
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<UserListVO> userListVOS = BeanCopyUtils.copyBeanList(page.getRecords(), UserListVO.class);
        return new PageVO(userListVOS,page.getTotal());
    }


    @Deprecated
    private boolean checkUserFormat( User user){
        return     StrUtil.isBlankIfStr(user.getEmail()) || !ReUtil.isMatch(Consts.REGEX_EMAIL,user.getEmail())
                || StrUtil.isBlankIfStr(user.getUserName()) || StrUtil.isBlankIfStr(user.getNickName())
                || StrUtil.isBlankIfStr(user.getPassword()) || !ReUtil.isMatch(Consts.REGEX_PASSWORD,user.getUserName());
    }

    private boolean checkUserPasswordFormat(User user){
        return ReUtil.isMatch(Consts.REGEX_PASSWORD,user.getPassword());
    }
    private boolean checkUserEmailFormat( User user){
        return ReUtil.isMatch(Consts.REGEX_EMAIL,user.getEmail());

    }

    @Override
    public boolean checkUserNameExist( String userName){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        User user1 = getOne(queryWrapper);
        return !BeanUtil.isEmpty(user1);
    }
    @Override
    public boolean checkUserEmailExist( String email){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        User user1 = getOne(queryWrapper);
        return !BeanUtil.isEmpty(user1);
    }
    @Override
    public boolean checkUserPhonenumberExist(String phonenumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phonenumber);
        User user1 = getOne(queryWrapper);
        return !BeanUtil.isEmpty(user1);
    }

    @Transactional
    @Override
    public boolean insertUser(UserInsertDTO userInsertDTO) {
        // 密码加密
        User user = BeanCopyUtils.copyBeanSingle(userInsertDTO, User.class);
        user.setPassword(passwordEncoder.encode(userInsertDTO.getPassword()));
        // 存入User表
        boolean save = save(user);
        // 存入user_role表
        List<UserRole> userRoleList = userInsertDTO.getRoleIds()
                .stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        boolean saveBatch = userRoleService.saveBatch(userRoleList);
        return save && saveBatch;
    }

    @Transactional
    @Override
    public int deleteUser(Long[] id) {
        int delete = getBaseMapper().deleteBatchIds(Arrays.asList(id));
        return delete;
    }

    @Transactional
    @Override
    public boolean updateUser(UserUpdateDTO userUpdateDTO) {
        // 更新基本信息到user表
        boolean updateUser = updateById(BeanCopyUtils.copyBeanSingle(userUpdateDTO, User.class));
        // 更新用户角色关系到user_role表
        boolean updateUserRole = userRoleService.updateUserRole(userUpdateDTO.getId(),userUpdateDTO.getRoleIds());
        return updateUser && updateUserRole;
    }


}




