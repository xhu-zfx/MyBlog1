package com.panghu.blog.service.impl;

import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.service.UploadService;
import com.panghu.blog.util.PathUtils;
import com.panghu.blog.util.UploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/18 15:58
 * @description 文件上传实现类
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Resource
    UploadUtils uploadUtils;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        String originalFilename = img.getOriginalFilename();
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadUtils.UploadToQiniuOSS(img,filePath);
        return ResponseResult.okResult(url);
    }

}
