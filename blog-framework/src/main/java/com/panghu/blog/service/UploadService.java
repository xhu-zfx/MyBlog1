package com.panghu.blog.service;

import com.panghu.blog.domain.entity.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/18 15:58
 * @description
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
