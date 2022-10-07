package com.panghu.blog.exception;

import com.panghu.blog.enums.AppHttpCodeEnum;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/13 19:01
 * @description
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
