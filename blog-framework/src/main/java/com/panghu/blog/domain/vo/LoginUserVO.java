package com.panghu.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/11 12:04
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserVO {

    private String token;
    private UserInfoVO userInfo;
}
