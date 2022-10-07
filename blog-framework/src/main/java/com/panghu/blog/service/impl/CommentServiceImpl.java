package com.panghu.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panghu.blog.constant.Consts;
import com.panghu.blog.domain.entity.Comment;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.entity.User;
import com.panghu.blog.domain.vo.CommentVO;
import com.panghu.blog.domain.vo.PageVO;
import com.panghu.blog.service.CommentService;
import com.panghu.blog.mapper.CommentMapper;
import com.panghu.blog.service.UserService;
import com.panghu.blog.util.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.panghu.blog.enums.AppHttpCodeEnum.SYSTEM_ERROR;

/**
* @author xhu-zfx
* @description 针对表【blog_comment(评论表)】的数据库操作Service实现
* @createDate 2022-09-08 16:19:35
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Resource
    UserService userService;

    @Override
    public ResponseResult listAllComment(String commentType,Long articleId, Integer pageNum, Integer pageSize) {
        // 查询对应文章的根评论，即rootId为-1
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Comment::getType,commentType)
                .eq(Comment::getRootId, Consts.COMMENT_ROOT)
                .eq(commentType.equals(Consts.COMMENT_TYPE_ARTICLE),Comment::getArticleId,articleId)
                .orderByAsc(Comment::getCreateTime);
        // 分页查询
        if (ObjectUtil.isNull(pageSize)||ObjectUtil.isNull(pageNum)) return ResponseResult.errorResult(SYSTEM_ERROR, Consts.ERROR_PAGE_PARAM);
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        // 转化为CommentVo
        List<CommentVO> commentVOList = toCommentVoList(page.getRecords());
        // 查询子评论
        commentVOList.forEach(commentVO -> getChildren(commentVO));
        return ResponseResult.okResult(new PageVO(commentVOList,page.getTotal()));
    }

    @Override
    public ResponseResult saveComment(Comment comment) {
        // 前端传入参数不完整，需要手动封装
        // 所以考虑封装工具类，创建Mybatisplus自动填充控制器
        if (ObjectUtil.isNull(comment)) return ResponseResult.errorResult(SYSTEM_ERROR,Consts.ERROR_COMMENT_NULL);
        save(comment);
        return ResponseResult.okResult();
    }

    // 获取当前根评论的子评论
    private void getChildren(CommentVO commentVo){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Comment::getRootId,commentVo.getId())
                .orderByAsc(Comment::getCreateTime);
        List<Comment> list = list(queryWrapper);
        commentVo.setChildren(toCommentVoList(list));
    }

    // 将Comment拷贝为CommentVo
    // 并手动添加数据：根据toCommentUserId查询toCommentUserName、根据create_by查询username
    private List<CommentVO> toCommentVoList(List<Comment> list){
        List<CommentVO> commentVOList = BeanCopyUtils.copyBeanList(list, CommentVO.class);
        commentVOList.forEach(commentVO ->{
            User rootUser = userService.getById(commentVO.getToCommentId());
            if (!BeanUtil.isEmpty(rootUser)) commentVO.setToCommentUserName(rootUser.getUserName());
            commentVO.setUsername(userService.getById(commentVO.getCreateBy()).getUserName());
        });
        return commentVOList;
    }
}




