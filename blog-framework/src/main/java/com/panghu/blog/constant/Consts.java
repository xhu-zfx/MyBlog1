package com.panghu.blog.constant;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/7 17:19
 * @description 常量类
 */
public class Consts {
    // 博客状态：1代表草稿、0代表正式发布
    public static final Integer BLOG_STATUS_DRAFT=1;    //草稿
    public static final Integer BLOG_STATUS_RELEASE=0;    //发布
    // 分类状态：1代表禁用、0代表正常
    public static final String CATEGORY_STATUS_NORMAL="0";
//    public static final String CATEGORY_STATUS_BAN="1";
    public static final String LINK_STATUS_NORMAL="0";
//    public static final String LINK_STATUS_BAN="1";
    // 是否为根评论
    public static final Long COMMENT_ROOT=-1L;
    // 评论类型：友链评论
    public static final String COMMENT_TYPE_LINK="1";
    // 评论类型：文章评论
    public static final String COMMENT_TYPE_ARTICLE="0";
    // 菜单类型：M目录 C菜单 F按钮
    public static final String MENU_TYPE_CATALOG="M";
    public static final String MENU_TYPE_MENU="C";
    public static final String MENU_TYPE_BUTTON="F";
    // 菜单状态：0代表正常，1代表停用
    public static final String MENU_STATUS_NORMAL="0";
    // 菜单状态：0代表显示，1代表隐藏
    public static final String MENU_VISIBLE_NORMAL="0";
    public static final String ROLE_STATUS_NORMAL="0";
    public static final String ROLE_ADMIN="admin";
    public static final String EXPORT_CATEGORY="分类.xlsx";
    public static final String EXPORT_CATEGORY_SHEET="分类导出";
    public static final String EXPORT_CATEGORY_NAME="分类名称";
    public static final String EXPORT_CATEGORY_DESCRIPTION="分类描述";
    public static final String EXPORT_CATEGORY_STATUS="分类状态：0正常，1禁用";
    public static final String LOGIN_USER_TYPE_ADMIN="1";
    public static final String PREMISSON_CONTENT_CATEGORY_EXPORT="content:category:export";
    // 七牛云外链域名
    public static final String QINIU_LINK="http://ridz0vduc.bkt.clouddn.com/";
    // 密码校验正则，密码长度至少6位，必须包含数字和字母
    // public static final String REGEX_PASSWORD="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
    // 密码校验正则，密码长度至少8位，必须包含数字、字母、特殊字符
    //public static final String REGEX_PASSWORD="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
    // 密码校验正则，密码长度至少8位，必须包含数字、大写字母、小写字母、特殊字符
    public static final String REGEX_PASSWORD="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}";
    // 邮箱校验正则，名称允许字母、数字，域名只允许英文域名
    public static final String REGEX_EMAIL="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    // 未传入分页参数的提示信息
    public static final String ERROR_PAGE_PARAM="请传入分页参数";
    // 查询文章详情错误:分类id不存在
    public static final String ERROR_CATEGORY_ID_NOT_EXIST="分类id不存在";
    // 登陆失败：用户名或者密码错误
    public static final String ERROR_LOGIN_WRONG="用户名或者密码错误";
    // 登陆失败：用户不存在
    public static final String ERROR_LOGIN_USER_NOT_EXIST="用户不存在";
    // 请求头中的token
    public static final String REQUEST_HEADER_TOKEN="token";
    public static final String ERROR_LOGIN_AUTH="认证或授权失败";
    public static final String ERROR_COMMENT_NULL="你还没有评论!";
    public static final String ERROR_ADMIN_TAG_UPDATE="更新失败，请稍后重试";
    public static final String ERROR_ADMIN_TAG_INSERT="新增失败，请稍后重试";
    public static final String ERROR_ADMIN_TAG_DELETE="删除失败，请稍后重试";
    public static final String ERROR_ADMIN_ARTICLE_UPDATE="文章更新失败，请稍后重试";
    public static final String ERROR_ADMIN_ARTICLE_DELETE="文章删除失败，请稍后重试";
    public static final String ERROR_ADMIN_MENU_UPDATE="更新失败，请稍后重试";
    public static final String ERROR_ADMIN_MENU_UPDATE_PARENT="更新失败，自己不能作为自己的上级菜单";
    public static final String ERROR_ADMIN_MENU_DELETE="删除失败，该菜单存在子菜单";
}
