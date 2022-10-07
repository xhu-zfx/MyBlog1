# 博客搭建文档

# 1、技术架构

## 多模块项目

* blog-framework为公共模块
* blog-user为前台展示模块，blog-framework的子模块
* blog-admin为后台管理模块，blog-framework的子模块

## 技术栈

* SpringBoot项目简化配置
* MyBatisPlus操作数据库
* MySQL数据库存储基本数据
* Redis做缓存，减少重复请求的响应时间
* Maven做依赖控制
* Git版本控制
* SpringSecurity做权限管理
* Swagger生成接口文档
* Postman接口测试
* 前端采用Vue+ElmentUI

# 2、物理模型设计(表结构、实体类)

数据库：db_blog

* blog_article：博客表

  ```sql
  USE `db_blog`;

  /*Table structure for table `sg_article` */

  DROP TABLE IF EXISTS `blog_article`;

  CREATE TABLE `blog_article` (
    `id` bigint(200) NOT NULL AUTO_INCREMENT,
    `title` varchar(256) DEFAULT NULL COMMENT '标题',
    `content` longtext COMMENT '文章内容',
    `summary` varchar(1024) DEFAULT NULL COMMENT '文章摘要',
    `category_id` bigint(20) DEFAULT NULL COMMENT '所属分类id',
    `thumbnail` varchar(256) DEFAULT NULL COMMENT '缩略图',
    `is_top` char(1) DEFAULT '0' COMMENT '是否置顶（0否，1是）',
    `status` char(1) DEFAULT '1' COMMENT '状态（0已发布，1草稿）',
    `view_count` bigint(200) DEFAULT '0' COMMENT '访问量',
    `is_comment` char(1) DEFAULT '1' COMMENT '是否允许评论 1是，0否',
    `create_by` bigint(20) DEFAULT NULL,
    `create_time` datetime DEFAULT NULL,
    `update_by` bigint(20) DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    `del_flag` int(1) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

  /*Data for the table `sg_article` */

  insert  into `blog_article`(`id`,`title`,`content`,`summary`,`category_id`,`thumbnail`,`is_top`,`status`,`view_count`,`is_comment`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`) values (1,'SpringSecurity从入门到精通','## 课程介绍\n![image20211219121555979.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/e7131718e9e64faeaf3fe16404186eb4.png)\n\n## 0. 简介1\n\n​	**Spring Security** 是 Spring 家族中的一个安全管理框架。相比与另外一个安全框架**Shiro**，它提供了更丰富的功能，社区资源也比Shiro丰富。\n\n​	一般来说中大型的项目都是使用**SpringSecurity** 来做安全框架。小项目有Shiro的比较多，因为相比与SpringSecurity，Shiro的上手更加的简单。\n\n​	 一般Web应用的需要进行**认证**和**授权**。\n\n​		**认证：验证当前访问系统的是不是本系统的用户，并且要确认具体是哪个用户**\n\n​		**授权：经过认证后判断当前用户是否有权限进行某个操作**\n\n​	而认证和授权也是SpringSecurity作为安全框架的核心功能。\n\n\n\n## 1. 快速入门\n\n### 1.1 准备工作\n\n​	我们先要搭建一个简单的SpringBoot工程\n\n① 设置父工程 添加依赖\n\n~~~~\n    <parent>\n        <groupId>org.springframework.boot</groupId>\n        <artifactId>spring-boot-starter-parent</artifactId>\n        <version>2.5.0</version>\n    </parent>\n    <dependencies>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-web</artifactId>\n        </dependency>\n        <dependency>\n            <groupId>org.projectlombok</groupId>\n            <artifactId>lombok</artifactId>\n            <optional>true</optional>\n        </dependency>\n    </dependencies>\n~~~~\n\n② 创建启动类\n\n~~~~\n@SpringBootApplication\npublic class SecurityApplication {\n\n    public static void main(String[] args) {\n        SpringApplication.run(SecurityApplication.class,args);\n    }\n}\n\n~~~~\n\n③ 创建Controller\n\n~~~~java\n\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n@RestController\npublic class HelloController {\n\n    @RequestMapping(\"/hello\")\n    public String hello(){\n        return \"hello\";\n    }\n}\n\n~~~~\n\n\n\n### 1.2 引入SpringSecurity\n\n​	在SpringBoot项目中使用SpringSecurity我们只需要引入依赖即可实现入门案例。\n\n~~~~xml\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-security</artifactId>\n        </dependency>\n~~~~\n\n​	引入依赖后我们在尝试去访问之前的接口就会自动跳转到一个SpringSecurity的默认登陆页面，默认用户名是user,密码会输出在控制台。\n\n​	必须登陆之后才能对接口进行访问。\n\n\n\n## 2. 认证\n\n### 2.1 登陆校验流程\n![image20211215094003288.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/414a87eeed344828b5b00ffa80178958.png)','SpringSecurity框架教程-Spring Security+JWT实现项目级前端分离认证授权',1,'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/948597e164614902ab1662ba8452e106.png','1','0',105,'0',NULL,'2022-01-23 23:20:11',NULL,NULL,0),(2,'weq','adadaeqe','adad',2,'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/fd2e9460c58a4af3bbeae5d9ed581688.png','1','0',22,'0',NULL,'2022-01-21 14:58:30',NULL,NULL,1),(3,'dad','asdasda','sadad',1,'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/737a0ed0b8ea430d8700a12e76aa1cd1.png','1','0',33,'0',NULL,'2022-01-18 14:58:34',NULL,NULL,1),(5,'sdad','![Snipaste_20220115_165812.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/1d9d283f5d874b468078b183e4b98b71.png)\r\n\r\n## sda \r\n\r\n222\r\n### sdasd newnewnew',NULL,2,'','1','0',44,'0',NULL,'2022-01-17 14:58:37',NULL,NULL,0);

  ```
* blog_article_tag：博客标签表
* blog_category：分类表
* blog_comment：评论表
* blog_link：友链表
* blog_sys_menu：菜单权限表
* blog_sys_role：角色信息表
* blog_sys_role_menu：角色和菜单关联表
* blog_sys_user：用户表
* blog_sys_user_role：用户和角色关联表
* blog_tag：标签表

使用MyBatisX插件简化重复开发，生成实体类包POJO，Service接口自动继承IService<T>，ServiceImpl实现类

# 3、开发环境

* IDE：IntelliJ IDEA 2022.1.1
* JDK：Java8
* 依赖版本如下pom.xml所示，如出现依赖爆红或依赖冲突可在[阿里云Maven仓库](https://developer.aliyun.com/mvn/search)中选择版本

  todo

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <parent>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-parent</artifactId>
          <version>2.7.3</version>
          <relativePath/> <!-- lookup parent from repository -->
      </parent>
      <groupId>com.panghu</groupId>
      <artifactId>blog-framework</artifactId>
      <version>0.0.1-SNAPSHOT</version>
      <name>blog-framework</name>
      <description>blog-framework</description>
      <properties>
          <java.version>1.8</java.version>
      </properties>
      <packaging>pom</packaging>
      <dependencies>

          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-devtools</artifactId>
              <scope>runtime</scope>
              <optional>true</optional>
          </dependency>
          <dependency>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
              <optional>true</optional>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-test</artifactId>
              <scope>test</scope>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-test</artifactId>
              <scope>test</scope>
          </dependency>
          <dependency>
              <groupId>com.baomidou</groupId>
              <artifactId>mybatis-plus-boot-starter</artifactId>
              <version>3.5.1</version>
          </dependency>
          <dependency>
              <groupId>mysql</groupId>
              <artifactId>mysql-connector-java</artifactId>
              <scope>runtime</scope>
              <version>5.1.39</version>
          </dependency>
          <dependency>
              <groupId>cn.hutool</groupId>
              <artifactId>hutool-all</artifactId>
              <version>5.7.17</version>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-security</artifactId>
          </dependency>
          <!--redis依赖-->
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-redis</artifactId>
          </dependency>
          <!--fastjson依赖-->
          <dependency>
              <groupId>com.alibaba</groupId>
              <artifactId>fastjson</artifactId>
              <version>1.2.4</version>
          </dependency>
          <!--jwt依赖-->
          <dependency>
              <groupId>io.jsonwebtoken</groupId>
              <artifactId>jjwt</artifactId>
              <version>0.9.1</version>
          </dependency>
          <!--阿里云OSS-->
          <dependency>
              <groupId>com.aliyun.oss</groupId>
              <artifactId>aliyun-sdk-oss</artifactId>
              <version>3.10.2</version>
          </dependency>
          <!--AOP-->
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-aop</artifactId>
          </dependency>
          <dependency>
              <groupId>com.alibaba</groupId>
              <artifactId>easyexcel</artifactId>
              <version>3.0.5</version>
          </dependency>
          <dependency>
              <groupId>io.springfox</groupId>
              <artifactId>springfox-swagger2</artifactId>
              <version>2.9.2</version>
          </dependency>
          <dependency>
              <groupId>io.springfox</groupId>
              <artifactId>springfox-swagger-ui</artifactId>
              <version>2.9.2</version>
          </dependency>
      </dependencies>

      <build>
          <plugins>
              <plugin>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-maven-plugin</artifactId>
                  <configuration>
                      <excludes>
                          <exclude>
                              <groupId>org.projectlombok</groupId>
                              <artifactId>lombok</artifactId>
                          </exclude>
                      </excludes>
                  </configuration>
              </plugin>
          </plugins>
      </build>

  </project>
  ```
* 数据库：mysql-5.7.27
* Redis：Redis 6.2.7

# 4、博客前台

## 4.0 前置工作

### 4.0.1 封装常量

本博客开发遵循阿里命名规范

不允许出现魔法值(即未经定义的常量)，所以本项目普通常量均定义于`com.panghu.blog.utils.Constants`下，`redis的key`常量定义于`com.panghu.blog.constant.RedisConsts`

### 4.0.2 统一响应类和响应枚举

```java
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;
    public ResponseResult() {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.msg = AppHttpCodeEnum.SUCCESS.getMsg();
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseResult errorResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.error(code, msg);
    }
    public static ResponseResult okResult() {
        ResponseResult result = new ResponseResult();
        return result;
    }
    public static ResponseResult okResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.ok(code, null, msg);
    }

    public static ResponseResult okResult(Object data) {
        ResponseResult result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS, AppHttpCodeEnum.SUCCESS.getMsg());
        if(data!=null) {
            result.setData(data);
        }
        return result;
    }

    public static ResponseResult errorResult(AppHttpCodeEnum enums){
        return setAppHttpCodeEnum(enums,enums.getMsg());
    }

    public static ResponseResult errorResult(AppHttpCodeEnum enums, String msg){
        return setAppHttpCodeEnum(enums,msg);
    }

    public static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums){
        return okResult(enums.getCode(),enums.getMsg());
    }

    private static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums, String msg){
        return okResult(enums.getCode(),msg);
    }

    public ResponseResult<?> error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        return this;
    }

    public ResponseResult<?> ok(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
```



```java
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
     PHONENUMBER_EXIST(502,"手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
```

### 4.0.3 声明

本项目为Mapper、Service、Controller三层架构

Controller层仅作调用Service与设置请求路径

Service层包含Service接口与ServiceImpl实现类，其中Service接口仅作方法声明，不定义任何变量，ServiceImpl实现类编写具体业务流程

Mapper层包含Mapper接口与Mapper.xml文件

```yaml
server:
  port: 7777
spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
#  数据源
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_blog?characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 134161
  redis:
    host: 192.168.181.130
    port: 6379
    password: 123456
    lettuce:
      pool:
        max-active: 10
        max-idle: 10
        min-idle: 1
        time-between-eviction-runs: 10s
#    文件上传
  servlet:
    multipart:
      max-file-size: 2MB
      max-request-size: 5MB
#  jackson配置类
  jackson:
    default-property-inclusion: non_null
    time-zone: GMT+8
    date-format: yyyy-MM-dd HH:mm:ss
#  mybatis-plus配置
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      logic-delete-field: delFlag
      logic-delete-value: 1
      logic-not-delete-value: 0
      id-type: auto
qiniuoss:
  accessKey: ybw1bQzX0NVR1f7KQo0s3Ims0coQioPenzNATs5-
  secretKey: JUuVDdxLTqdUIwhRCuCo-1ASxvXuujyNyHmf2fC4
  bucket: panghublog

```

### 4.0.4 application.yaml

## 4.1 展示热门文章

接口路径：`[GET] http://localhost:7777/article/hotArticleList`

涉及到分页查询，所以使用MybatisPlus分页插件，添加配置类

```java
@Configuration
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}
```

ArticleServiceImpl具体业务流程代码

```java
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 查询条件：已发布、按照浏览量进行排序、最多查询10条
        queryWrapper.eq(Article::getStatus,0).orderByDesc(Article::getViewCount);
        Page<Article> page = new Page(1,0);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        return ResponseResult.okResult(articles);
    }
```

此时响应的格式不符合要求返回了很多无用字段，前台涉及的字段只有文字标题、观看量，所以考虑使用VO进行响应优化

首先创建`HotArticleVo`类，只包含文章id、标题title、观看量viewCount

在Utils包下创建一个`BeanCopyUtils`类用于拷贝Bean，在类中创建静态方法`copyBeanList`用于拷贝List集合

```java
public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream()
                .map(o -> BeanUtil.copyProperties(o,clazz))
                .collect(Collectors.toList());
    }
```

所以接口优化为

```java
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 查询条件：已发布、按照浏览量进行排序、最多查询10条
        queryWrapper.eq(Article::getStatus,0).orderByDesc(Article::getViewCount);
        Page<Article> page = new Page(1,0);
        page(page,queryWrapper);
        List<HotArticleVo> articleVos = copyBeanList(page.getRecords(), HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }
```


## 4.2 分类列表

### 需求与接口设计

接口路径：`[GET] http://localhost:7777/category/getCategoryList`

注意： ①要求只展示有发布正式文章的分类 ②必须是正常状态的分类

### 代码实现

需要根据blog_category表的id作为category_id来查询blog_article中的数据，采用传统方法会涉及到多表join

根据阿里Java开发手册索引规约，超过三张表禁止join，所以此处不采用join

```java
    @Override
    public ResponseResult getCategoryList() {
        // 查询文章表,状态为已发布的文章
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper.eq(Article::getStatus, Constants.BLOG_STATUS_RELEASE);
        List<Article> articleList = articleService.list(articleQueryWrapper);
        // 获取文章分类id,去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        // 根据categoryIds查询分类表,并且只查询状态正常的
        List<Category> categoryList = listByIds(categoryIds).stream()
                .filter(category -> CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 返回封装vo
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class));
    }
```

## 4.3 分页查询文章列表

接口路径：`[GET] http://localhost:7777/article/articleList`

请求参数：分类id：categoryId、页码：pageNum、每页显示条数：pageSize

返回数据为一个对象，包含rows属性和total属性，所以创建PageVo类用于封装分页查询返回数据


```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo {
    private List rows;
    private Long total;
}
```


```java
    // 查询文章列表
    @Override
    public ResponseResult articleList(Long categoryId, Integer pageSize, Integer pageNum) {
        // 查询条件：categoryId是否传入,传入即根据查询、查询正式发布的文章、置顶的文章优先显示
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                // categoryId是否传入,传入即根据查询
                .eq(ObjectUtil.isNotNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId)
                // 查询正式发布的文章
                .eq(Article::getStatus,Constants.BLOG_STATUS_RELEASE)
                // 根据isTop降序
                .orderByDesc(Article::getIsTop);

        // 分页查询
        if (ObjectUtil.isNull(pageSize)||ObjectUtil.isNull(pageNum)) return ResponseResult.errorResult(SYSTEM_ERROR,Constants.ERROR_PAGE_PARAM);
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page, queryWrapper);
        List<ArticleListVo> articleListVos = copyBeanList(page.getRecords(), ArticleListVo.class);
        return ResponseResult.okResult(new PageVo(articleListVos,page.getTotal()));
    }
```

此时返回数据如下：

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "rows": [
            {
                "id": 1,
                "title": "SpringSecurity从入门到精通",
                "summary": "SpringSecurity框架教程-Spring Security+JWT实现项目级前端分离认证授权",
                "categoryName": null,
                "thumbnail": "https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/948597e164614902ab1662ba8452e106.png",
                "viewCount": 105,
                "createTime": "2022-01-23T15:20:11.000+00:00"
            },
            {
                "id": 2,
                "title": "testArticle1",
                "summary": "我是一个测试1",
                "categoryName": null,
                "thumbnail": "https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/fd2e9460c58a4af3bbeae5d9ed581688.png",
                "viewCount": 22,
                "createTime": "2022-01-21T06:58:30.000+00:00"
            },
            {
                "id": 3,
                "title": "testArticle2",
                "summary": "我是一个测试2",
                "categoryName": null,
                "thumbnail": "https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/737a0ed0b8ea430d8700a12e76aa1cd1.png",
                "viewCount": 33,
                "createTime": "2022-01-18T06:58:34.000+00:00"
            },
            {
                "id": 5,
                "title": "testArticle3",
                "summary": "我是一个ceshi3",
                "categoryName": null,
                "thumbnail": "",
                "viewCount": 44,
                "createTime": "2022-01-17T06:58:37.000+00:00"
            }
        ],
        "total": 4
    }
}
```

### 优化响应数据-categoryName

从JOSN数据中可以发现，categoryName始终为null，原因是Article类中仅有categoryId属性，没有categoryName，所以拷贝时categoryId被丢弃，所以考虑怎么实现categoryName的写入

在Article类中添加categoryName属性，由于数据库blog_article表中没有该字段，所以需要为其加上`@TableField(exist = false)`注解，标明该属性非数据库表字段

接下来需要对根据该article对象的categoryId在blog_category表查询对应的categoryName值，所以考虑注入`CategoryService`对象

```java
    // 查询文章列表
    @Override
    public ResponseResult articleList(Long categoryId, Integer pageSize, Integer pageNum) {
        // 查询条件：categoryId是否传入,传入即根据查询、查询正式发布的文章、置顶的文章优先显示
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                // categoryId是否传入,传入即根据查询
                .eq(ObjectUtil.isNotNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId)
                // 查询正式发布的文章
                .eq(Article::getStatus,Constants.BLOG_STATUS_RELEASE)
                // 根据isTop降序
                .orderByDesc(Article::getIsTop);

        // 分页查询
        if (ObjectUtil.isNull(pageSize)||ObjectUtil.isNull(pageNum)) return ResponseResult.errorResult(SYSTEM_ERROR,Constants.ERROR_PAGE_PARAM);
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
//        articles.stream()
//                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
//                .collect(Collectors.toList());
        articles.forEach(article -> {
            article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
        });
        List<ArticleListVo> articleListVos = copyBeanList(articles, ArticleListVo.class);
        return ResponseResult.okResult(new PageVo(articleListVos,page.getTotal()));
    }
```

当我们运行时会发现，启动失败了，并提示以下信息

Description:

The dependencies of some of the beans in the application context form a cycle:

   articleController (field private com.panghu.blog.service.ArticleService com.panghu.blog.controller.ArticleController.articleService)  
┌─────┐  
|  articleServiceImpl  
↑     ↓  
|  categoryServiceImpl  
└─────┘

Action:

Relying upon circular references is discouraged and they are prohibited by default. Update your application to remove the dependency cycle between beans. As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.

解释：应用上下文中一些bean的依赖形成了一个循环：即在articleServiceImpl类中注入了categoryServiceImpl，在categoryServiceImpl类中注入了articleServiceImpl对象，即形成了**循环依赖**问题

分析：其实在Action中已经提供给我们解决方式：As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.

意思是：作为最后的手段，可以通过将 spring.main.allow-circular-references 设置为 true 来自动中断循环。但是作为一名优秀的程序员，怎么能采取这种低级的方式来强行中断呢(我是fw)，所以首选改变bean注入方式，如下分析

我们在articleServiceImpl与categoryServiceImpl都注入对方的原因是什么?我们想要查询对方对应的表的数据，那我们能不能换一种方式来查询对方表数据呢?可以的，注入对方Mapper一样可以查询

解决：在`CategoryServiceImpl`类中重新注入ArticleMapper 依赖

```java
    // 循环依赖
//    @Resource
//    private ArticleService articleService;
    @Resource
    private ArticleMapper articleMapper;

```

并将getCategoryList方法中查询blog_article表部分改为如下

```java
//        List<Article> articleList = articleService.list(articleQueryWrapper);
        List<Article> articleList = articleMapper.selectList(articleQueryWrapper);
```


### 优化响应数据-createTime

从响应回的JSON数据中可以发现，`"createTime": "2022-01-17T06:58:37.000+00:00"`，这并不是我们想要的日期格式，期望日期格式为`"createTime": "2022-01-17 06:58:37"`，所以做json数据转换，可以使用fastjson或者jackson

* 创建MyFastJsonConfig 配置类，添加fastjson配置

```java
//@Configuration
public class MyFastJsonConfig implements WebMvcConfigurer {
//    @Bean//使用@Bean注入fastJsonHttpMessageConvert
    public HttpMessageConverter fastJsonHttpMessageConverters() {
        //1.需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);

        fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverters());
    }
}
```

* 在application.yaml配置jackson

```yaml
spring:
  jackson:
    default-property-inclusion: non_null
    time-zone: GMT+8
    date-format: yyyy-MM-dd HH:mm:ss
```


## 4.4 文章详情

接口路径：`[GET] http://localhost:7777/article/{id}`

`ArticleDetailVo` 类如下

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 所属分类id
     */
    private Long categoryId;

    /**
     * 所属分类名
     */
    private String categoryName;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 访问量
     */
    private Long viewCount;

    /**
     *
     */
    private Date createTime;

}
```

`copyBeanSingle`方法如下


```java
    // 拷贝单个bean
    public static <V> V copyBeanSingle(Object source, Class<V> clazz) {
        return BeanUtil.copyProperties(source,clazz);
    }
```

由于需求接口中需要传递参数时使用`/{id}`进行传参，所以在controller层需要使用`@PathVariable`注解接收参数

`articleController`中方法如下

```java
    @GetMapping("/{id}")
    public ResponseResult getArtivleDetail(@PathVariable("id") Long id){
        return articleService.getgetArtivleDetail(id);
    }
```

查询单个文章Service层业务流程简单，不做解释

```java
    // 查询文章详情
    @Override
    public ResponseResult getgetArtivleDetail(Long id) {
        // 根据id查询
        Article article = getById(id);
        // 转换成vo
        ArticleDetailVo articleDetailVo = copyBeanSingle(article, ArticleDetailVo.class);
        // 根据分类id查询分类名
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if (BeanUtil.isEmpty(category)) return ResponseResult.errorResult(SYSTEM_ERROR,ERROR_CATEGORY_ID_NOT_EXIST);
        articleDetailVo.setCategoryName(category.getName());
        return ResponseResult.okResult(articleDetailVo);
    }
```


创建自定义注解`@SystemLog`

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemLog {
    String businessName() default "";
}
```

创建切面类


```java
@Component
@Aspect
@Slf4j
public class logAspect {

    @Pointcut("@annotation(com.panghu.blog.annotation.SystemLog)")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed ;
        try {
            handleBefore(joinPoint);
            proceed= joinPoint.proceed();
            handleAfter(proceed);
        }finally {
            // 结束后换行
            log.info("=======APO LOG END=======" + System.lineSeparator());
        }
        return proceed;
    }

    private void handleAfter(Object proceed) {
        // 打印出参
        log.info("Response       : {}", JSONUtil.toJsonStr(proceed));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        SystemLog systemLog= getSystemLog(joinPoint);
        log.info("=======APO LOG START=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringType(),((MethodSignature)joinPoint.getSignature()).getName() );
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSONUtil.toJsonStr(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getAnnotation(SystemLog.class);
    }
}
```

## 4.5 友链查询

接口路径`[GET] http://localhost:7777/link/getAllLink`


```java
    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, Constants.LINK_STATUS_NORMAL);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list(queryWrapper), LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }
```

## 4.6 登录功能

### 需求

接口路径：`[POST] http://localhost:7777/login`

请求体：

```json
{
    "userName":"sg",
    "password":"1234"
}
```

响应格式：

```json
{
    "code": 200,
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0ODBmOThmYmJkNmI0NjM0OWUyZjY2NTM0NGNjZWY2NSIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTY0Mzg3NDMxNiwiZXhwIjoxNjQzOTYwNzE2fQ.ldLBUvNIxQCGemkCoMgT_0YsjsWndTg5tqfJb77pabk",
        "userInfo": {
            "avatar": "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi",
            "email": "23412332@qq.com",
            "id": 1,
            "nickName": "sg333",
            "sex": "1"
        }
    },
    "msg": "操作成功"
}
```

### 思路分析

登录

    ①自定义登录接口

        调用ProviderManager的方法进行认证 如果认证通过生成jwt

        把用户信息存入redis中

    ②自定义UserDetailsService

        在这个实现类中去查询数据库

        注意配置passwordEncoder为BCryptPasswordEncoder

校验：

    ①定义Jwt认证过滤器

        获取token

        解析token获取其中的userid

        从redis中获取用户信息

        存入SecurityContextHolder

### 代码实现

```java
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        // authenticationManager的authenticate最终会调用UserDetailsService的loadUserByUsername方法
        // 我们创建UserDetailsServiceImpl对该方法进行实现即可
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (BeanUtil.isEmpty(authenticate)) throw new RuntimeException(Constants.ERROR_LOGIN_WRONG);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        redisCache.setCacheObject(RedisConstants.LOGIN_USER_KEY+userId,loginUser);
        // 将user转换为userInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBeanSingle(loginUser.getUser(), UserInfoVo.class);
        return ResponseResult.okResult(new LoginUserVo(jwt,userInfoVo));
    }
```

authenticationManager的authenticate最终会调用UserDetailsService的loadUserByUsername方法，我们创建UserDetailsServiceImpl对该方法进行实现即可，在该方法中对数据库查询


```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        // 是否查询成功 , 查询不到即抛出异常
        if (BeanUtil.isEmpty(user)) throw new RuntimeException(Constants.ERROR_LOGIN_USER_NOT_EXIST);
        return new LoginUser(user);
    }
}
```

定义Jwt认证过滤器

```java
/**
 * JWT工具类
 */
public class JwtUtil {

    //有效期为
    public static final Long JWT_TTL = 24*60 * 60 *1000L;// 60 * 60 *1000  一个小时
    //设置秘钥明文
    public static final String JWT_KEY = "panghu";

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }
  
    /**
     * 生成jtw
     * @param subject token中要存放的数据（json格式）
     * @return
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成jtw
     * @param subject token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());// 设置过期时间
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if(ttlMillis==null){
            ttlMillis=JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)              //唯一的ID
                .setSubject(subject)   // 主题  可以是JSON数据
                .setIssuer("zfx")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    /**
     * 创建token
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    public static void main(String[] args) throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjYWM2ZDVhZi1mNjVlLTQ0MDAtYjcxMi0zYWEwOGIyOTIwYjQiLCJzdWIiOiJzZyIsImlzcyI6InNnIiwiaWF0IjoxNjM4MTA2NzEyLCJleHAiOjE2MzgxMTAzMTJ9.JVsSbkP94wuczb4QryQbAke3ysBDIL5ou8fWsbt_ebg";
        Claims claims = parseJWT(token);
        System.out.println(claims);
    }

    /**
     * 生成加密后的秘钥 secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
  
    /**
     * 解析
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }


}
```

定义RedisCache

```java
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class RedisCache
{
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key)
    {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public long deleteObject(final Collection collection)
    {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList)
    {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key)
    {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet)
    {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key)
    {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key)
    {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey)
    {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 删除Hash中的数据
     * 
     * @param key
     * @param hkey
     */
    public void delCacheMapValue(final String key, final String hkey)
    {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hkey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys)
    {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern)
    {
        return redisTemplate.keys(pattern);
    }
}
```

分析响应格式可以看出我们需要封装两个Vo类：分别是UserInfoVo类，包含id,nickName,sex,avatar,email、LoginUserVo类包含token和userInfo

```java
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;
}
```

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserVo {
    private String token;
    private UserInfoVo userInfo;
}
```

配置redis

## 4.7 退出登录

接口路径`[POST] http://localhost:7777/logout`

请求头：token

响应格式：

```json
{
    "code": 200,
    "msg": "操作成功"
}
```

`UserServiceImpl` 业务实现

```java
    @Override
    public ResponseResult logout() {
        // 获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        // 获取userId
        String userId = loginUser.getUser().getId().toString();
        // 删除redis中的信息
        redisCache.deleteObject(RedisConstants.LOGIN_USER_KEY+userId);
        return ResponseResult.okResult();
    }
```

## 4.8 查询评论功能

评论分为对友链的评论和对文章的评论，所以type字段用以区别，0代表文章评论，1代表友链评论

所以查询评论也分为查询文章评论和查询友链评论

接口路径分别为`[GET] http://localhost:7777/comment/commentList`   |  `[GET] http://localhost:7777/comment/linkCommentList`

请求参数：

查询文章评论时：articleId：对应文章id

pageNum：分页查询的页码

pageSize：每页数量

响应格式

```json
{
    "code": 200,
    "data": {
        "rows": [
            {
                "articleId": "1",
                "children": [
                    {
                        "articleId": "1",
                        "content": "你说啥？",
                        "createBy": "1",
                        "createTime": "2022-01-30 10:06:21",
                        "id": "20",
                        "rootId": "1",
                        "toCommentId": "1",
                        "toCommentUserId": "1",
                        "toCommentUserName": "sg333",
                        "username": "sg333"
                    }
                ],
                "content": "asS",
                "createBy": "1",
                "createTime": "2022-01-29 07:59:22",
                "id": "1",
                "rootId": "-1",
                "toCommentId": "-1",
                "toCommentUserId": "-1",
                "username": "sg333"
            }
        ],
        "total": "15"
    },
    "msg": "操作成功"
}

```



```java
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.listAllComment(Consts.COMMENT_TYPE_ARTICLE,articleId,pageNum,pageSize);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.listAllComment(Consts.COMMENT_TYPE_LINK,null,pageNum,pageSize);
    }
}
```



```java
    @Resource
    UserService userService;

    @Override
    public ResponseResult listAllComment(String commentType,Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                // 查询要查询的评论类型，文章评论为0，友链评论为1
                .eq(Comment::getType,commentType)
                // 查询对应文章的根评论，即rootId为-1
                .eq(Comment::getRootId, Consts.COMMENT_ROOT)
                // 如果是查询文章评论，则需要文章id
                .eq(commentType.equals(Consts.COMMENT_TYPE_ARTICLE),Comment::getArticleId,articleId)
                // 根据创建时间倒叙
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


    // 获取当前根评论的子评论
    private void getChildren(CommentVo commentVo){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Comment::getRootId,commentVo.getId())
                .orderByAsc(Comment::getCreateTime);
        List<Comment> list = list(queryWrapper);
        commentVo.setChildren(toCommentVoList(list));
    }

    // 将Comment拷贝为CommentVo
    // 并手动添加数据：根据toCommentUserId查询toCommentUserName、根据create_by查询username
    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        commentVoList.forEach(commentVo ->{
            User rootUser = userService.getById(commentVo.getToCommentId());
            if (!BeanUtil.isEmpty(rootUser)) commentVo.setToCommentUserName(rootUser.getUserName());
            commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getUserName());
        });
        return commentVoList;
    }
```

## 4.9 发表评论

接口路径`[POST] http://localhost:7777/comment`

登录后才可发表评论，故需要携带请求头token

评论分为对友链的评论和对文章的评论，所以type字段用以区别，0代表文章评论，1代表友链评论

请求体：

```json
{
    "articleId": 1,
    "type": 0,
    "rootId": -1,
    "toCommentId": -1,
    "content": "回复评论的内容",
}
```

前端传入参数不完整，需要手动封装createTime、createBy、updateTime、updateBy，所以考虑封装工具类，创建Mybatisplus自动填充控制器

```java
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            userId = -1L;//表示是自己创建
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy",userId , metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", SecurityUtils.getUserId(), metaObject);
    }
}
```

创建控制器后，需要在实体类中将需要自动填充的字段用`@TableField`的`fill`属性注解标识，`@TableField(fill = FieldFill.INSERT)`标识在插入数据时自动填充，`@TableField(fill = FieldFill.INSERT_UPDATE)`标识在插入和删除时都会自动给该字段填充



```java
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
```

服务层只需要调用插入方法即可，

```java
    @Override
    public ResponseResult saveComment(Comment comment) {
        // 前端传入参数不完整，需要手动封装
        // 所以考虑封装工具类，创建Mybatisplus自动填充控制器
        if (ObjectUtil.isNull(comment)) return ResponseResult.errorResult(SYSTEM_ERROR,Consts.ERROR_COMMENT_NULL);
        save(comment);
        return ResponseResult.okResult();
    }
```

## 4.10 个人信息查询

接口路径`[GET] http://localhost:7777/user/userInfo`

需要携带token请求头

响应格式

```json
{
    "code": 200,
    "data": {
        "avatar": ,
        "email": ,
        "id": ,
        "nickName": ,
        "sex": 
    },
    "msg": "操作成功"
}
```

`UserServiceImpl`

```java
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
```

附`SecurityUtils`类

```java
public class SecurityUtils {

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
```

## 4.11 头像上传功能

在个人信息界面，点击编辑按钮可以上传个人头像，上传完成后可以用于更新个人信息接口。

### 七牛云OSS测试

官方sdk

```java
//构造一个带指定 Region 对象的配置类
Configuration cfg = new Configuration(Region.region0());
cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
String accessKey = "your access key";
String secretKey = "your secret key";
String bucket = "your bucket name";

//默认不指定key的情况下，以文件内容的hash值作为文件名
String key = null;

try {
    byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
    ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
    Auth auth = Auth.create(accessKey, secretKey);
    String upToken = auth.uploadToken(bucket);

    try {
        Response response = uploadManager.put(byteInputStream,key,upToken,null, null);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        System.out.println(putRet.key);
        System.out.println(putRet.hash);
    } catch (QiniuException ex) {
        Response r = ex.response;
        System.err.println(r.toString());
        try {
            System.err.println(r.bodyString());
        } catch (QiniuException ex2) {
            //ignore
        }
    }
} catch (UnsupportedEncodingException ex) {
    //ignore
}

```

测试代码


```java
@SpringBootTest
@ConfigurationProperties(prefix = "qiniuoss")
public class OSSTest {

    // 从配置文件中读取密钥与存储空间
    private String accessKey;
    private String secretKey;
    private String bucket;

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @Test
    void testQiniuOSS(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
//        String accessKey = "your access key";
//        String secretKey = "your secret key";
//        String bucket = "your bucket name";

//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);

            InputStream inputStream=new FileInputStream("F:\\1610705821600.jpg");

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }

    }

}
```

### 接口设计

接口路径`[POST] http://localhost:7777/upload`

需要携带token请求头

参数：img，为要上传的图片

请求头：multipart/form-data

响应格式

```json
{   
    "code": 200,
    "data": "上传成功后图片外链",
    "msg": "操作成功"
}
```

### 头像上传代码实现


```java
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
```

`PathUtils `作用是设置文件保存时的名字、后缀名

```java
public class PathUtils {

    public static String generateFilePath(String fileName){
        //根据日期生成路径   2022/1/15/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);
        return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
    }
}
```


根据上面测试demo封装上传工具类，由于有数据需要从配置文件中读取，所以不好封装成静态，此处选择将其注入Bean容器


```java
@ConfigurationProperties(prefix = "qiniuoss")
@Component
@Setter
public class UploadUtils {
    // 从配置文件中读取三个密钥与命名空间
    private String accessKey;
    private String secretKey;
    private String bucket;

    public String UploadToQiniuOSS(MultipartFile img,String filePath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream= img.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return Consts.QINIU_LINK+key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return AppHttpCodeEnum.SYSTEM_ERROR.toString();
    }

}
```

## 4.12 更新个人信息

在修改个人信息后点击保存按钮，完成对修改的保存

接口路径`[PUT] http://localhost:7777/user/userInfo`

需要携带token请求头

请求体

```json
{
    "id": ,
    "avatar": ,
    "email": ,
    "nickName": ,
    "sex": 
}
```

响应格式

```json
{
    "code": 200,
    "msg": "操作成功"
}
```

代码如下

```java
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
```


## 4.13 用户注册功能

用户在注册界面完成zhuce，用户信息中的邮箱、用户名不允许重复，重复即注册失败

密码需要加密存储到数据库

接口路径`[POST] http://localhost:7777/user/register`

请求体

```json
{
    "email": "string",
    "nickName": "",
    "password": "",
    "userName": ""
}
```

响应体

```json
{
    "code":,
    "msg":
}
```

代码实现

主要业务是对数据的校验，以后会使用validation来简化，此处不过多赘述

```java
    @Override
    public ResponseResult register(User user) {
        // 数据校验:格式判断
        if (StrUtil.isBlankIfStr(user.getUserName()))  throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        if (!checkUserPasswordFormat(user)) throw new SystemException(AppHttpCodeEnum.PASSWORD_FORMAT_ERROR);
        if (!checkUserEmailFormat(user)) throw new SystemException(AppHttpCodeEnum.EMAIL_FORMAT_ERROR);
        // 数据校验:查询用户名，邮箱，手机号是否存在
        if (checkUserNameExist(user))    throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        if (checkUserEmailExist(user))    throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        if (checkUserPhonenUMBERExist(user))    throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 存入
        save(user);
        return ResponseResult.okResult();
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
    private boolean checkUserNameExist( User user){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName());
        User user1 = getOne(queryWrapper);
        return !BeanUtil.isEmpty(user1);
    }
    private boolean checkUserEmailExist( User user){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,user.getEmail());
        User user1 = getOne(queryWrapper);
        return !BeanUtil.isEmpty(user1);
    }
    private boolean checkUserPhonenUMBERExist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,user.getPhonenumber());
        User user1 = getOne(queryWrapper);
        return !BeanUtil.isEmpty(user1);
    }
```


## 4.14 AOP实现日志记录A

创建自定义注解`@SystemLog`，注解到方法上可以在访问此接口时打印该接口的访问日志

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemLog {
    String businessName() default "";
}
```

创建切面类，实现具体功能

```java
@Component
@Aspect
@Slf4j
public class logAspect {

    @Pointcut("@annotation(com.panghu.blog.annotation.SystemLog)")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed ;
        try {
            handleBefore(joinPoint);
            proceed= joinPoint.proceed();
            handleAfter(proceed);
        }finally {
            // 结束后换行
            log.info("=======APO LOG END=======" + System.lineSeparator());
        }
        return proceed;
    }

    private void handleAfter(Object proceed) {
        // 打印出参
        log.info("Response       : {}", JSONUtil.toJsonStr(proceed));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        SystemLog systemLog= getSystemLog(joinPoint);
        log.info("=======APO LOG START=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringType(),((MethodSignature)joinPoint.getSignature()).getName() );
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSONUtil.toJsonStr(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getAnnotation(SystemLog.class);
    }
}
```

## 4.15 更新博客文章浏览量

在用户点击'阅读全文'后会增加文章阅读量

接口路径`[PUT] http://localhost:7777/article/updateViewCount/{id}`

### 思路

1. 在应用启动时将数据库所有文章的浏览量存入redis

    **CommandLineRunner实现启动项目时预处理**：

    创建runner类实现CommandLineRunner接口，在run方法中编写需要处理的逻辑，并将其注入到容器中，在所有的bean初始化后会执行此方法
2. 实时更新浏览量去redis更新
3. 每隔一段时间将redis数据更新到数据库中

    **定时任务**：

    在配置类上使用@EnableScheduling注解开启定时任务，

    使用@Scheduled(cron="")注解表示需要定时执行的代码，参数cron表示一个[cron表达式](https://cron.qqe2.com/)

    cron表达式语法：`[秒] [分] [小时] [日] [月] [周] [年]`

    注：[年]不是必须的域，可以省略[年]，则一共6个域，spring3.x过后默认六位

    1

    1
4. 读取浏览量从redis实时读取

**通配符说明:**

* `*` 表示所有值。 例如:在分的字段上设置 *,表示每一分钟都会触发。
* `?` 表示不指定值。使用的场景为不需要关心当前设置这个字段的值。例如:要在每月的10号触发一个操作，但不关心是周几，所以需要周位置的那个字段设置为”?” 具体设置为 0 0 0 10 * ?
* `-` 表示区间。例如 在小时上设置 “10-12”,表示 10,11,12点都会触发。
* `,` 表示指定多个值，例如在周字段上设置 “MON,WED,FRI” 表示周一，周三和周五触发
* `/` 用于递增触发。如在秒上面设置”5/15” 表示从5秒开始，每增15秒触发(5,20,35,50)。 在日字段上设置’1/3’所示每月1号开始，每隔三天触发一次。
* `L` 表示最后的意思。在日字段设置上，表示当月的最后一天(依据当前月份，如果是二月还会依据是否是润年[leap]), 在周字段上表示星期六，相当于”7”或”SAT”。如果在”L”前加上数字，则表示该数据的最后一个。例如在周字段上设置”6L”这样的格式,则表示“本月最后一个星期五”
* `W` 表示离指定日期的最近那个工作日(周一至周五). 例如在日字段上置”15W”，表示离每月15号最近的那个工作日触发。如果15号正好是周六，则找最近的周五(14号)触发, 如果15号是周未，则找最近的下周一(16号)触发.如果15号正好在工作日(周一至周五)，则就在该天触发。如果指定格式为 “1W”,它则表示每月1号往后最近的工作日触发。如果1号正是周六，则将在3号下周一触发。(注，”W”前只能设置具体的数字,不允许区间”-“)。
* `#` 序号(表示每月的第几个周几)，例如在周字段上设置”6#3”表示在每月的第三个周六.注意如果指定”#5”,正好第五周没有周六，则不会触发该配置(用在母亲节和父亲节再合适不过了) ；小提示：’L’和 ‘W’可以一组合使用。如果在日字段上设置”LW”,则表示在本月的最后一个工作日触发；周字段的设置，若使用英文字母是不区分大小写的，即MON与mon相同。

### 代码实现

1. 创建启动时预处理类`ViewCountRunner `并将其注入Bean容器，在启动项目时将所有文章的id做key，viewCount做value存入redis

```java
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Resource
    ArticleService articleService;

    @Resource
    RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询博客信息
        List<Article> articleList = articleService.list(null);
        Map<String, Integer> viewCountMap = articleList
                .stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();
                }));
        redisCache.setCacheMap(RedisConsts.ARTICLE_VIEW_COUNT_KEY,viewCountMap);
    }
}
```

2. 实时更新浏览量到redis中

在RedisCache类中封装对应自增Hash的方法

```java
    public void incrementCacheMapValue(final String key, final String hKey, int value){
        redisTemplate.opsForHash().increment(key,hKey,value);
    }
```


```java
    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(RedisConsts.BLOG_PREFIX+RedisConsts.ARTICLE_VIEW_COUNT_KEY,id.toString(),1);
        return ResponseResult.okResult();
    }
```

3. 每隔一段时间将redis数据更新到数据库中

使用`@EnableScheduling`注解开启定时任务，该注解可加在任意配置类上，该处新建了一个配置类专门开启，也可加在启动类上

```java
@Configuration
@EnableScheduling
public class TimeTaskConfig {
}
```

创建定时任务类，并将其注入到Bean容器中，在需要定时执行的方法上使用`@Scheduled(cron= )`注解，并在cron属性中写cron表达式声明其定时执行的周期

```java
@Component
public class UpdataViewCountJob {

    @Resource
    RedisCache redisCache;

    @Resource
    ArticleService articleService;

    // 每隔10分钟执行一次
     @Scheduled(cron = "0 */10 * * * ?")
    // 每隔5秒执行一次
    // @Scheduled(cron = "0/5 * * * * ?")
    public void UpdataViewCount(){
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(RedisConsts.BLOG_PREFIX + RedisConsts.ARTICLE_VIEW_COUNT_KEY);
        List<Article> articleList = viewCountMap
                .entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        articleService.updateBatchById(articleList);
    }
}
```

4. 读取浏览量从redis实时读取

由于该代码块需要多次调用，所以将其封装

```java
    // 从redis中读取实时viewcount
    Long getViewCountFromRedis(Long id){
        Integer articleViewCount = redisCache.getCacheMapValue(RedisConsts.BLOG_PREFIX + RedisConsts.ARTICLE_VIEW_COUNT_KEY, id.toString());
        return articleViewCount.longValue();
    }
```

在`getArtivleDetail、listArticle`、`listHotArticle`方法中从数据库查询`article`后都为其手动`setViewCount`

此处附`ArticleServiceImpl`完整代码


```java
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Resource
    private CategoryService categoryService;

    @Resource
    private RedisCache redisCache;

    // 查询前十的热门文章
    @Override
    public ResponseResult listHotArticle() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 查询条件：已发布、按照浏览量进行排序、最多查询10条
        queryWrapper.eq(Article::getStatus, Consts.BLOG_STATUS_RELEASE).orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);
        List<Article> articleList = page.getRecords();
        articleList.forEach(article -> {
            // 从redis中读取实时viewcount
            article.setViewCount(getViewCountFromRedis(article.getId()));
        });
        List<HotArticleVO> articleVos = copyBeanList(articleList, HotArticleVO.class);
        return ResponseResult.okResult(articleVos);
    }
    // 查询文章列表
    @Override
    public ResponseResult listArticle(Long categoryId, Integer pageSize, Integer pageNum) {
        // 查询条件：categoryId是否传入,传入即根据查询、查询正式发布的文章、置顶的文章优先显示
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                // categoryId是否传入,传入即根据查询
                .eq(ObjectUtil.isNotNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId)
                // 查询正式发布的文章
                .eq(Article::getStatus, Consts.BLOG_STATUS_RELEASE)
                // 根据isTop降序
                .orderByDesc(Article::getIsTop)
                // 根据CreateTime排序
                .orderByDesc(Article::getCreateTime);
        // 分页查询
        if (ObjectUtil.isNull(pageSize)||ObjectUtil.isNull(pageNum)) return ResponseResult.errorResult(SYSTEM_ERROR, Consts.ERROR_PAGE_PARAM);
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
//        articles.stream()
//                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
//                .collect(Collectors.toList());
        articles.forEach(article -> {
            article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
            // 从redis中读取实时viewcount
            article.setViewCount(getViewCountFromRedis(article.getId()));
        });
        List<ArticleListVO> articleListVOS = copyBeanList(articles, ArticleListVO.class);
        return ResponseResult.okResult(new PageVO(articleListVOS,page.getTotal()));
    }

    // 查询文章详情
    @Override
    public ResponseResult getArtivleDetail(Long id) {
        // 根据id查询
        Article article = getById(id);
        // 从redis中读取实时viewcount
        article.setViewCount(getViewCountFromRedis(id));
        // 转换成vo
        ArticleDetailVO articleDetailVo = copyBeanSingle(article, ArticleDetailVO.class);
        // 根据分类id查询分类名
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if (BeanUtil.isEmpty(category)) return ResponseResult.errorResult(SYSTEM_ERROR,ERROR_CATEGORY_ID_NOT_EXIST);
        articleDetailVo.setCategoryName(category.getName());
        return ResponseResult.okResult(articleDetailVo);
    }

    // 实时更新viewcount到redis
    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(RedisConsts.BLOG_PREFIX+RedisConsts.ARTICLE_VIEW_COUNT_KEY,id.toString(),1);
        return ResponseResult.okResult();
    }

    // 从redis中读取实时viewcount
    Long getViewCountFromRedis(Long id){
        Integer articleViewCount = redisCache.getCacheMapValue(RedisConsts.BLOG_PREFIX + RedisConsts.ARTICLE_VIEW_COUNT_KEY, id.toString());
        return articleViewCount.longValue();
    }
}
```


## 4.16 Swagger2

Swagger的作用：自动在线生成接口的文档，功能测试

依赖

```xml
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```

创建配置类

```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.panghu.blog.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("xhu-zfx", "http://www.panghu.com", "756867768@qq.com");
        return new ApiInfoBuilder()
                .title("文档标题")
                .description("文档描述")
                .contact(contact)   // 联系方式
                .version("1.1.1")  // 版本
                .build();
    }
}
```

在controller类上使用`@Api(tags = "文章控制器",description = "文章接口")`

在方法上使用

```java
    @ApiOperation(value = "分类查询文章接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类id", required = false),
            @ApiImplicitParam(name = "pageNum", value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
```

来对方法进行描述，以及对相应的参数进行描述

配置完成后访问`http://localhost:7777/swagger-ui.html`即可看到自动生成的接口文档


# 5、博客后台

## 5.0 前置工作

### 5.0.1 application.yaml

```yaml
server:
  port: 8989
spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
  #  数据源
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_blog?characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 134161
  redis:
    host: 192.168.181.130
    port: 6379
    password: 123456
    lettuce:
      pool:
        max-active: 10
        max-idle: 10
        min-idle: 1
        time-between-eviction-runs: 10s
  #    文件上传
  servlet:
    multipart:
      max-file-size: 2MB
      max-request-size: 5MB
  #  jackson配置类
  jackson:
    default-property-inclusion: non_null
    time-zone: GMT+8
    date-format: yyyy-MM-dd HH:mm:ss
#  mybatis-plus配置
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      logic-delete-field: delFlag
      logic-delete-value: 1
      logic-not-delete-value: 0
      id-type: auto
  mapper-locations: classpath*:mapper/*.xml
```

后台除登录接口外的所有接口，访问均需携带token请求头

## 5.1 后台登录

接口路径`[POST] http://localhost:8989/user/login`

请求体：

```json
{
    "userName": ,
    "password":
}
```

响应格式

```json
{
    "code": 200,
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0ODBmOThmYmJkNmI0NjM0OWUyZjY2NTM0NGNjZWY2NSIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTY0Mzg3NDMxNiwiZXhwIjoxNjQzOTYwNzE2fQ.ldLBUvNIxQCGemkCoMgT_0YsjsWndTg5tqfJb77pabk"
    },
    "msg": "操作成功"
}
```

代码实现

Security配置类`SecurityConfig`，对所有非登录接口均进行认证管理

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Resource
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭csrf
                .csrf().disable()
                // 不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/admin/login").anonymous()
//                .antMatchers("/logout").authenticated()
                // TODO: 2022/9/18 所有代码完成后添加对该处的权限设置
                // .antMatchers("/user/userInfo").authenticated()
                // .antMatchers("/upload").authenticated()
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().authenticated();
        // 配置异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        http.logout().disable();
        // 添加检查登录过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 允许跨域
        http.cors();
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
```

服务层`AdminServiceImpl`、过滤器`JwtAuthenticationTokenFilter`跟前台登录一样，仅在存入redis的key有所区分



## 5.2 权限查询

本项目是基于RBAC模型进行权限控制

> RBAC（Role-Based Access Control）即：基于角色的权限控制。通过角色关联用户，角色关联权限的方式间接赋予用户权限。
>

接口路径`[GET] http://localhost:8989/getInfo`，需要携带token请求头

响应格式：

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "permissions": [
            "system:user:list",
            "system:role:list",
            .
            .
            .
            "content:link:query",
            "content:category:export"
        ],
        "roles": [
            "admin"
        ],
        "user": {
            "id": 1,
            "nickName": "sg333",
            "avatar": "http://ridz0vduc.bkt.clouddn.com/Fl9G6S83Z2gBRXghACidVWbUTcr5",
            "sex": "1",
            "email": "23412332@qq.com"
        }
    }
}
```

如果用户id为1代表管理员，roles 中只需要有admin，permissions中需要有所有菜单类型为C或者F的，状态为正常的，未被删除的权限

Controller代码，需要调用`menuService`和`roleService`查询对应的权限信息及角色信息

```java
    @Resource
    MenuService menuService;

    @Resource
    RoleService roleService;

    @GetMapping("/getInfo")
    ResponseResult getInfo(){
        // 查询当前用户
        User loginAdmin = SecurityUtils.getLoginUser().getUser();
        // 根据用户id查询权限信息
        List<String> permissions=menuService.listPermissionsById(loginAdmin.getId());
        // 根据用户id查询角色信息
        List<String> roles=roleService.listRolesById(loginAdmin.getId());
        // 根据用户id查询用户信息
        UserInfoVO userInfoVO= BeanCopyUtils.copyBeanSingle(loginAdmin, UserInfoVO.class);
        // 返回数据
        return ResponseResult.okResult(new AdminUserInfoVO(permissions,roles,userInfoVO));
    }
```

MenuServiceImpl 代码，如果为超级管理员，则查询所有正常的权限信息，方便查看调用

```java
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Override
    public List<String> listPermissionsById(Long id) {
        if (id==1L){
            // 如果为超级管理员，则返回所有状态为正常的权限
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(Menu::getStatus, Consts.MENU_STATUS_NORMAL);
            queryWrapper
                    .eq(Menu::getMenuType, Consts.MENU_TYPE_MENU)
                    .or()
                    .eq(Menu::getMenuType, Consts.MENU_TYPE_BUTTON);
            List<String> permissonList = list(queryWrapper)
                    .stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return permissonList;
        }
        // 非超级管理员需要根据id查询role，再根据role查询对应的menu
        return getBaseMapper().listPermissonByUserId(id);
    }
}
```

MenuMapper相关sql语句，实现多表联查

```sql
    <select id="listPermissonByUserId" resultType="java.lang.String">
        select
            bsm.perms
        from
            blog_sys_user_role bsur
                left join blog_sys_role_menu bsrm on bsur.role_id = bsrm.role_id
                left join blog_sys_menu bsm on bsrm.menu_id = bsm.id
        where
              user_id = #{userId}
          and bsm.menu_type in ('C','F')
          and bsm.status = 0
          and bsm.del_flag = 0
    </select>

```

`RoleServiceImpl `调用mapper层

```java
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Override
    public List<String> listRolesById(Long id) {
        return getBaseMapper().listRolesByUserId(id);
    }
}

```

`listRolesByUserId`方法sql语句

```sql
    <select id="listRolesByUserId" resultType="java.lang.String">
        select
            bsr.role_key
        from
            blog_sys_user_role bsur
            left join blog_sys_role bsr on bsur.role_id = bsr.id
        where
              bsur.user_id = #{userId}
          and bsr.status=0
          and bsr.del_flag=0
    </select>
```

## 5.3 动态路由

前端需要实现左侧菜单栏根据用户角色而显示不同的，所以需要后端返回当前用户所能访问的菜单数据

接口路径`[GET] http://localhost:8989/getRouters`，需要携带token请求头

响应格式

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "menus": [
            {
                "id": 2023,
                "menuName": "写博文",
                "parentId": 0,
                "orderNum": 0,
                "path": "write",
                "component": "content/article/write/index",
                "menuType": "C",
                "visible": "0",
                "status": "0",
                "perms": "content:article:writer",
                "icon": "build",
                "createTime": "2022-01-08 03:39:58",
                "children": []
            },
            {
                "id": 1,
                "menuName": "系统管理",
                "parentId": 0,
                "orderNum": 1,
                "path": "system",
                "menuType": "M",
                "visible": "0",
                "status": "0",
                "perms": "",
                "icon": "system",
                "createTime": "2021-11-12 10:46:19",
                "children": [
                    {
                        "id": 100,
                        "menuName": "用户管理",
                        "parentId": 1,
                        "orderNum": 1,
                        "path": "user",
                        "component": "system/user/index",
                        "menuType": "C",
                        "visible": "0",
                        "status": "0",
                        "perms": "system:user:list",
                        "icon": "user",
                        "createTime": "2021-11-12 10:46:19"
                    },
                    {
                        "id": 101,
                        "menuName": "角色管理",
                        "parentId": 1,
                        "orderNum": 2,
                        "path": "role",
                        "component": "system/role/index",
                        "menuType": "C",
                        "visible": "0",
                        "status": "0",
                        "perms": "system:role:list",
                        "icon": "peoples",
                        "createTime": "2021-11-12 10:46:19"
                    },
                    {
                        "id": 102,
                        "menuName": "菜单管理",
                        "parentId": 1,
                        "orderNum": 3,
                        "path": "menu",
                        "component": "system/menu/index",
                        "menuType": "C",
                        "visible": "0",
                        "status": "0",
                        "perms": "system:menu:list",
                        "icon": "tree-table",
                        "createTime": "2021-11-12 10:46:19"
                    }
                ]
            },
            {
                "id": 2017,
                "menuName": "内容管理",
                "parentId": 0,
                "orderNum": 4,
                "path": "content",
                "menuType": "M",
                "visible": "0",
                "status": "0",
                "perms": "",
                "icon": "table",
                "createTime": "2022-01-08 02:44:38",
                "children": [
                    {
                        "id": 2019,
                        "menuName": "文章管理",
                        "parentId": 2017,
                        "orderNum": 0,
                        "path": "article",
                        "component": "content/article/index",
                        "menuType": "C",
                        "visible": "0",
                        "status": "0",
                        "perms": "content:article:list",
                        "icon": "build",
                        "createTime": "2022-01-08 02:53:10"
                    },
                    {
                        "id": 2018,
                        "menuName": "分类管理",
                        "parentId": 2017,
                        "orderNum": 1,
                        "path": "category",
                        "component": "content/category/index",
                        "menuType": "C",
                        "visible": "0",
                        "status": "0",
                        "perms": "content:category:list",
                        "icon": "example",
                        "createTime": "2022-01-08 02:51:45"
                    },
                    {
                        "id": 2022,
                        "menuName": "友链管理",
                        "parentId": 2017,
                        "orderNum": 4,
                        "path": "link",
                        "component": "content/link/index",
                        "menuType": "C",
                        "visible": "0",
                        "status": "0",
                        "perms": "content:link:list",
                        "icon": "404",
                        "createTime": "2022-01-08 02:56:50"
                    },
                    {
                        "id": 2021,
                        "menuName": "标签管理",
                        "parentId": 2017,
                        "orderNum": 6,
                        "path": "tag",
                        "component": "content/tag/index",
                        "menuType": "C",
                        "visible": "0",
                        "status": "0",
                        "perms": "content:tag:index",
                        "icon": "button",
                        "createTime": "2022-01-08 02:55:37"
                    }
                ]
            }
        ]
    }
}
```

### 响应格式分析

meuns集合，存储若干个MenuVO对象，为什么是MenuVO而不是直接使用Menu呢，其关键在于字段，每个MenuVO中的字段是Menu的子集，除了children字段，children字段是用于存储该对象的子对象，该子对象的parentId是父对象的id

所以我们先查出没有父对象的，即parentId字段为0的对象，再根据这个字段的id到数据库中查询哪些数据的parentId=id，就可以将这条数据存入父对象的children中，得到children集合

### 代码实现

**实体类**

由于响应的格式要求，我们需要封装两个VO，分别是最终返回menus集合对象的`RoutersVO`和返回元对象的`MenuVO`

`RoutersVO`类

```java
@Data
@AllArgsConstructor
public class RoutersVO {
    List<MenuVO> menus;
}
```

`MenuVO`类

对相应代码进行说明：

@Accessors(chain = true)[^1]

@JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)

排除了children字段的构造器[^2]

```java
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class MenuVO {

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long id;

    private String menuName;

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long parentId;

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Integer orderNum;

    private String path;

    private String component;

    private String menuType;

    private String visible;

    private String status;

    private String perms;

    private String icon;

    private Date createTime;

    @TableField(exist = false)
    private List<MenuVO> children;

    public MenuVO(Long id, String menuName, Long parentId, Integer orderNum, String path, String component, String menuType, String visible, String status, String perms, String icon, Date createTime) {
        this.id = id;
        this.menuName = menuName;
        this.parentId = parentId;
        this.orderNum = orderNum;
        this.path = path;
        this.component = component;
        this.menuType = menuType;
        this.visible = visible;
        this.status = status;
        this.perms = perms;
        this.icon = icon;
        this.createTime = createTime;
    }
}
```

接口映射controller类，查询当前用户，调用service方法并返回

```java
    @GetMapping("/getRouters")
    ResponseResult getRouters(){
        // 查询当前用户id
        User loginAdmin = SecurityUtils.getLoginUser().getUser();
        List<MenuVO> menuVOTree=menuService.listMenuTreeByUserId(loginAdmin.getId());
        return ResponseResult.okResult(new RoutersVO(menuVOTree));
    }
```

**业务层MenuServiceImpl实现类**

父对象查询方法`listMenuTreeByUserId(Long id)`：传入当前用户id，针对是否管理员进行不同的查询，管理员：查询所有菜单，非管理员：根据其id查询对应的菜单，注意，根据上面的接口分析，这里的`listAllMenuVO`和`listMenuVOByUserId`均只对父对象进即parentId=0进行查询，最后再`treeMenuVOList`根据当前查询出的父对象进行子对象的查询

设置子对象方法`treeMenuVOList(List<MenuVO> menuVOList)`：传入父对象集合，转换为stream流，map中进行操作，调用`listChildren`根据父集合的id进行查询

由于map必须要返回值，该处进行的又是为属性赋值set的操作，Lombok生成的set没有返回值，所以上面在MenuVO类上添加了`@Accessors(chain = true)`，为所有set方法设置了返回值，就是这个对象本身

```java
    @Override
    public List<MenuVO> listMenuTreeByUserId(Long id) {
        List<MenuVO> menuVOList = null;
        // 如果是管理员，返回所有菜单
        if (id.equals(1L)){
            menuVOList = getBaseMapper().listAllMenuVO();
        } else {
            menuVOList = getBaseMapper().listMenuVOByUserId(id);
        }
        return treeMenuVOList(menuVOList);
    }

    // 根据menuVO对象的id，查询所有parent_id为它的对象，将这些对象存入父对象的children中
    // 注意 ! 父子关系最多仅存在二级，不涉及递归
    private List<MenuVO> treeMenuVOList(List<MenuVO> menuVOList) {
        List<MenuVO> menuVOTree = menuVOList
                .stream()
                .map(menuVO -> menuVO.setChildren(getBaseMapper().listChildren(menuVO.getId())))
                .collect(Collectors.toList());
        return menuVOTree;
    }

```

**持久层MenuMapper.xml**

`listAllMenuVO`方法，对所有状态正常的、未被删除的、为父对象的、非按钮类型的MenuVO相应字段查询

```sql
    <select id="listAllMenuVO" resultType="com.panghu.blog.domain.vo.MenuVO">
        select
            distinct bsm.id,bsm.menu_name,bsm.parent_id ,
                     bsm.order_num,bsm.path,bsm.component,
                     bsm.menu_type,bsm.visible,bsm.status,
                     IFNULL(bsm.perms,'') as perms,bsm.icon,bsm.create_time
        from
            blog_sys_menu bsm
        where
            bsm.menu_type in ('C','M')
            and bsm.parent_id = 0
            and bsm.status = 0
            and bsm.del_flag = 0
        order by
            bsm.parent_id,bsm.order_num
    </select>
```

`listMenuVOByUserId`方法

```sql
    <select id="listMenuVOByUserId" resultType="com.panghu.blog.domain.vo.MenuVO">
        select
            distinct bsm.id,bsm.menu_name as menuName,bsm.parent_id as parentId,
                     bsm.order_num as orderNum,bsm.path,bsm.component,
                     bsm.menu_type as menuType,bsm.visible,bsm.status,
                     nullif(bsm.perms,'') as perms,bsm.icon,bsm.create_time as createTime
        from
                      blog_sys_user_role bsur
            left join blog_sys_role_menu bsrm on bsur.role_id = bsrm.role_id
            left join blog_sys_menu bsm on bsrm.menu_id = bsm.id
        where
                bsur.user_id = #{userId}
            and bsm.parent_id = 0
            and bsm.menu_type in ('C','M')
            and bsm.status = 0
            and bsm.del_flag = 0
        order by
            bsm.parent_id,bsm.order_num
    </select>
```

`listChildren`方法

```sql
    <select id="listChildren" resultType="com.panghu.blog.domain.vo.MenuVO">
        select
            distinct bsm.id,bsm.menu_name as menuName,bsm.parent_id as parentId,
                     bsm.order_num as orderNum,bsm.path,bsm.component,
                     bsm.menu_type as menuType,bsm.visible,bsm.status,
                     nullif(bsm.perms,'') as perms,bsm.icon,bsm.create_time as createTime
        from
            blog_sys_menu bsm
        where
            parent_id = #{parentId}
            and bsm.menu_type in ('C','M')
        order by
            bsm.parent_id,bsm.order_num
    </select>
```

当我们写出如此优秀的一段代码时，信心满满的Run起来并用postman嚣张的点击Send按钮时，却迎来了当头一棒，不对!是当头几棒，我们的代码，报错了，赶紧打开控制台，小心翼翼的看着报错信息

```txt
org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.PersistenceException: 
### Error querying database.  Cause: java.lang.IndexOutOfBoundsException: Index: 12, Size: 12
### The error may exist in file [F:\idea-workspace\MyBlog1\blog-framework\target\classes\mapper\MenuMapper.xml]
### The error may involve com.panghu.blog.mapper.MenuMapper.listMenuVOByUserId
### The error occurred while handling results
### SQL: select             distinct bsm.id,bsm.menu_name as menuName,bsm.parent_id as parentId,                      bsm.order_num as orderNum,bsm.path,bsm.component,                      bsm.menu_type as menuType,bsm.visible,bsm.status,                      nullif(bsm.perms,'') as perms,bsm.icon,bsm.create_time as createTime         from                       blog_sys_user_role bsur             left join blog_sys_role_menu bsrm on bsur.role_id = bsrm.role_id             left join blog_sys_menu bsm on bsrm.menu_id = bsm.id         where                 bsur.user_id = ?             and bsm.parent_id = 0             and bsm.menu_type in ('C','M')             and bsm.status = 0             and bsm.del_flag = 0         order by             bsm.parent_id,bsm.order_num
### Cause: java.lang.IndexOutOfBoundsException: Index: 12, Size: 12
```

睁眼一看`Error querying database`说明是sql语句出问题了，再睁眼一看，发现是老朋友`IndexOutOfBoundsException`，心里立马想，这我知道：数组越界嘛，但是我们看看我们的代码，通篇没有出现自己定义数组的影子，`Index: 12, Size: 12`表示数组size最大12，但是此时索引却来到了12，即size变成13了，定睛一看，我们写的sql语句查询的字段数量就为12，mybatis查询时会调用相关实体类的构造函数，所以是执行查询的过程中给我们查询了children字段，导致数组越界，我们使用Lombok生成的构造器只有全参和无参，所以我们需要在menuVO添加部分构造器，排除children字段，至此，该bug解决

长舒一口气后，再次send请求，发现没有报错，数据也没问题，心中些窃喜，打开前端文件夹，npm run dev，打开浏览器，我去，又报错了，看请求，没错啊，前端就是渲染不出来，打开浏览器控制台，16个错误

```txt
Error in render: "RangeError: Maximum call stack size exceeded"
Maximum call stack size exceeded
```

我靠，这也看不懂的，赶紧百度，发现我前端在动态渲染时时id=""，这不字符串类型嘛，我设置了jackson序列化，Jackson对long型的转换是没有问题的。只不过前端js有个问题，java的long型，在转换后，js中展示会损失精度。如：1500829886697496578，在前端使用js数字类型展示是可能就变成了1500829886697496600。为了解决这个问题，一般情况下我们会将后端的Long型转换为字符串类型，所以在MenuVO类中的Long类型的属性上加上<br />`@JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)`，将这个字段转化为字符串类型即可，至此，该bug解决

## 5.4 退出登录

接口路径`[POST] http://localhost:8989/admin/logout` 需要携带token请求头

代码与前台项目一样，都是获取token请求头，解析获得LoginUser对象，在redis中将其删除

服务层`AdminServiceImpl`实现类代码：

```java
    @Override
    public ResponseResult logout() {
        // 获取请求头并解密，已封装到SecurityUtils.getLoginUser方法中
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 获取userId
        String userId = loginUser.getUser().getId().toString();
        // 删除redis中的信息
        redisCache.deleteObject(RedisConsts.BLOG_ADMIN_PREFIX + RedisConsts.LOGIN_USER_KEY + userId);
        return ResponseResult.okResult();
    }
```

## 5.5 查询标签列表

### 5.4.0 需求

为了方便后期对文章进行管理，需要提供标签的功能，一个文章可以有多个标签。

在后台需要分页查询标签功能，要求能根据标签名进行分页查询。**后期可能会增加备注查询等需求**。

注意：不能把删除了的标签查询出来。

### 5.5.1 接口设计

接口路径`[GET] http://localhost:8989/content/tag/list` 需要携带token请求头

请求参数：

pageNum：分页参数，当前页号

pageSize：分页参数，每页数据个数

name：标签名

remark：备注

响应格式：

`http://localhost:8989/content/tag/list?pageNum=1&pageSize=5&name=p&remark=持久层`

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "rows": [
            {
                "id": 2,
                "name": "MybatisPlus",
                "remark": "持久层框架，减少sql编写"
            }
        ],
        "total": 1
    }
}
```

### 5.5.2 代码实现

**pojo层**

封装`TagListDTO` 类用于接收数据，仅接收标签名name和标签描述remark用于模糊查询

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDTO {
    private String name;
    private String remark;
}
```

封装`TagVO`类用于返回Tag的部分字段，包括id，名字name，描述remark

```java
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TagVO {
    private Long id;

    private String name;

    private String remark;
}
```

**控制层**`TagController`

```java
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Resource
    TagService tagService;

    @GetMapping("/list")
    ResponseResult list(Integer pageNum , Integer pageSize , TagListDTO tagListDTO){
        return tagService.listTag(tagListDTO,pageNum,pageSize);
    }
}
```

**服务层**`TagServiceImpl`实现类

```java
    @Override
    public ResponseResult listTag(TagListDTO tagListDTO, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        // 条件查询：根据name、remark模糊查询，
        queryWrapper
                .like(!StrUtil.isBlankIfStr(tagListDTO.getName()),Tag::getName,tagListDTO.getName())
                .like(!StrUtil.isBlankIfStr(tagListDTO.getRemark()),Tag::getRemark,tagListDTO.getRemark())
                .orderByDesc(Tag::getCreateTime);
        Page<Tag> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<TagListVO> tagVOList = BeanCopyUtils.copyBeanList(page.getRecords(), TagListVO.class);
        return ResponseResult.okResult(new PageVO(tagVOList,page.getTotal()));
    }
```


## 5.6 新增标签

接口路径`[POST] http://localhost:8989/content/tag/insertTag` ，需要携带token请求头

请求体：

```json
{
    "name":"GoLanguage",
    "remark":"一门由Google开发并开源的一门新兴的高级语言，由于其天然支持高并发的特性，收到众多大型互联网公司的青睐"
}
```

响应格式

```json
{
    "code":200,
    "msg":"操作成功"
}
```

**控制层**`TagController`

```java
    // 新增Tag
    @PostMapping("/insertTag")
    ResponseResult insertTag(@RequestBody TagListDTO tagListDTO){
        return tagService.insertTag(tagListDTO);
    }
```

**服务层**`TagServiceImpl`实现类

```java
    @Override
    public ResponseResult insertTag(TagDTO tagDTO) {
        // 获取tagListDTO中的 name 和 remark 字段，手动封装 创建时间、更新时间、创建人、更新人 字段
        Tag tag = BeanCopyUtils.copyBeanSingle(tagDTO, Tag.class);
        tag.setCreateTime(new Date());
        tag.setUpdateTime(new Date());
        tag.setCreateBy(SecurityUtils.getUserId());
        tag.setUpdateBy(SecurityUtils.getUserId());
        boolean updateSuccess = saveOrUpdate(tag);
        if (!updateSuccess) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_TAG_INSERT);
        return ResponseResult.okResult();
    }

```

## 5.7 删除标签

接口路径`[DELETE] http://localhost:8989/content/tag/deleteTag/{id}`，需要携带token请求头

响应格式

```json
{
    "code":200,
    "msg":"操作成功"
}
```

**控制层**`TagController`

```java
// 删除Tag
    @DeleteMapping("/deleteTag/{ids}")
    ResponseResult deleteTag(@PathVariable Long[] ids){
        return tagService.deleteTag(ids);
    }
```

**服务层**`TagServiceImpl`实现类

```java
    @Override
    public ResponseResult deleteTag(Long[] ids) {
        int updateSuccess = getBaseMapper().deleteBatchIds(Arrays.asList(ids));
        if (updateSuccess<=0) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_TAG_DELETE);
        return ResponseResult.okResult();
    }

```

## 5.8 修改标签

### 5.8.1 数据回显

接口路径`[GET] http://localhost:8989/content/tag/detailTag/{id}`，需要携带token请求头

数据响应格式

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "id": 1,
        "name": "Mybatis",
        "remark": "持久层框架，在xml文件中编写sql语句"
    }
}
```

**控制层**`TagController`

```java
    // 查询Tag详情
    @GetMapping("/detailTag/{id}")
    ResponseResult detailTag(@PathVariable("id") Long id){
        return tagService.detailTag(id);
    }
```

**服务层**`TagServiceImpl`实现类

```java
    @Override
    public ResponseResult detailTag(Long id) {
        Tag tag = getById(id);
        TagVO tagVO = BeanCopyUtils.copyBeanSingle(tag, TagVO.class);
        return ResponseResult.okResult(tagVO);
    }

```

### 5.8.2 修改标签

接口路径`[PUT] http://localhost:8989/content/tag/updateTag`，需要携带token请求头

请求体

```json
{
    "id": 1,
    "name": "Mybatis",
    "remark": "持久层框架，在xml文件中编写sql语句"
}
```

**控制层**`TagController`

```java
    // 修改Tag
    @PutMapping("/updateTag")
    ResponseResult updateTag(@RequestBody TagUpdateDTO tagUpdateDTO){
        return tagService.updateTag(tagUpdateDTO);
    }
```

**服务层**`TagServiceImpl`实现类


```java
    @Override
    public ResponseResult updateTag(TagUpdateDTO tagUpdateDTO) {
        // 将 name 和 remark 赋值给tag，并手动封装 更新时间、更新人 字段
        Tag tag = BeanCopyUtils.copyBeanSingle(tagUpdateDTO, Tag.class);
        tag.setUpdateTime(new Date());
        tag.setUpdateBy(SecurityUtils.getUserId());
        boolean updateSuccess = updateById(tag);
        if (!updateSuccess) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_TAG_UPDATE);
        return ResponseResult.okResult();
    }

```

## 5.9 写博文

### 需求分析

用户在该页面可以编写博文，该博文支持markdown语法，同时可以设置该博文属于哪个分类，关联上哪些标签

### 接口设计

#### 查询所有分类

接口路径：`[GET] http://localhost:8989/content/category/listAllCategory`，需要携带token请求头

无请求参数

响应格式

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": [
        {
            "id": 1,
            "name": "java",
            "description": "一门高级编程语言"
        },
        {
            "id": 2,
            "name": "PHP",
            "description": "是在服务器端执行的脚本语言"
        },
        {
            "id": 15,
            "name": "SpringBoot",
            "description": "一种Java开发框架"
        }
    ]
}
```

#### 查询所有标签

接口路径：`[GET] http://localhost:8989/content/tag/listAllTag`，需要携带token请求头


无请求参数

响应格式

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": [
        {
            "id": 1,
            "name": "Mybatis",
            "remark": "持久层框架，在xml文件中编写sql语句"
        },
        {
            "id": 3,
            "name": "SpringBoot",
            "remark": "简化开发"
        },
        {
            "id": 4,
            "name": "JavaSE",
            "remark": "基础"
        },
        {
            "id": 5,
            "name": "Redis",
            "remark": "缓存中间件"
        },
        {
            "id": 7,
            "name": "GoLanguage",
            "remark": "一门由Google开发并开源的一门新兴的高级语言，由于其天然支持高并发的特性，受到众多大型互联网公司的青睐"
        }
    ]
}
```

#### 上传图片

接口路径：`[GET] http://localhost:8989/upload`，需要携带token请求头

请求头：Content-Type ：multipart/form-data;

响应格式

```json
{
    "code": 200,
    "data": "文件访问链接",
    "msg": "操作成功"
}

```

#### 新增博文

接口路径：`[POST] http://localhost:8989/content/article`，需要携带token请求头

请求体

```json
{
    "title":"测试新增博文",
    "thumbnail":"http://ridz0vduc.bkt.clouddn.com/2022/09/18/e30ee5703a2440aca39419ecf5799245.jpg",
    "isTop":"0",
    "isComment":"0",
    "content":"# 一级标题\n## 二级标题\n![Snipaste_20220228_224837.png](http://ridz0vduc.bkt.clouddn.com/2022/09/18/e30ee5703a2440aca39419ecf5799245.jpg)\n正文",
    "tags":[
        1,
        4
    ],
    "categoryId":1,
    "summary":"哈哈",
    "status":"1"
}

```

响应格式

```json
{
	"code":200,
	"msg":"操作成功"
}
```

### 代码实现

#### 查询所有分类

**控制层**`CategoryController`

```java
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Resource
    CategoryService categoryService;

    @GetMapping("/listAllCategory")
    ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }
}
```

**服务层**`CategoryServiceImpl`实现类

```java
    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, CATEGORY_STATUS_NORMAL);
        List<CategoryVO> categoryVOList = BeanCopyUtils.copyBeanList(list(queryWrapper), CategoryVO.class);
        return ResponseResult.okResult(categoryVOList);
    }
```

#### 查询所有标签

**控制层**`TagController`

```java
    @GetMapping("/listAllTag")
    ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
```

**服务层**`TagServiceImpl`实现类

```java
    @Override
    public ResponseResult listAllTag() {
        List<TagVO> tagVOList = BeanCopyUtils.copyBeanList(list(), TagVO.class);
        return ResponseResult.okResult(tagVOList);
    }
```


#### 上传图片

**控制层**`UploadController `

```java
@RestController
@RequestMapping
public class UploadController {

    @Resource
    UploadService uploadService;

    @PostMapping("/upload")
    ResponseResult uploadImg(@RequestParam("img") MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
```

**服务层**`UploadServiceImpl`实现类复用前台页面的4.11头像上传接口[^3]

#### 新增博文

**控制层**`ArticleController`

```java
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @PostMapping
    ResponseResult insertArticle(@RequestBody ArticleDTO articleDTO){
        return articleService.insertArticle(articleDTO);
    }
}
```

**服务层**`ArticleServiceImpl`实现类，

先把博文数据插入到blog_article表中，再将文章id和tagId集合插入到blog_article_tag表中

在调用mybatisplus的save方法后，生成的id会自动反向赋值到该对象中，所以此时的article 对象的id属性以及有mybatisplus为其自动生成的值了，所以在对象中拿到值并存入ArticleTag对象中

```java
    @Override
    @Transactional
    public ResponseResult insertArticle(ArticleDTO articleDTO) {
        Article article = copyBeanSingle(articleDTO, Article.class);
        save(article);
        List<ArticleTag> articleTagList = articleDTO.getTags()
                .stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTagList);
        return ResponseResult.okResult();
    }
```

## 5.10 导出分类到excel

使用EasyExcel

### 接口设计

接口路径`[GET] http://localhost:8989/content/category/export`，需要携带token请求头

无请求参数

响应格式：

成功则直接导出一个excel文件

失败则返回

```json
{
    "code": 500,
    "msg": "导出失败，请稍后重试"
}
```

### 代码实现

封装头信息

```java
    public static void setDownLoadHeader(String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fname= URLEncoder.encode(filename,"UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition","attachment; filename="+fname);
    }

```


```java
    @GetMapping("/export")
    void export(HttpServletResponse response){
        try {
            // 设置下载文件的请求头
            WebUtils.setDownLoadHeader(Consts.EXPORT_CATEGORY,response);
            // 获取数据
            List<Category> categoryList = categoryService.list();
            List<ExcelCategoryVO> excelCategoryVOList = BeanCopyUtils.copyBeanList(categoryList, ExcelCategoryVO.class);
            // 写入到excel文件中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVO.class).autoCloseStream(Boolean.FALSE).sheet(Consts.EXPORT_CATEGORY_SHEET)
                    .doWrite(excelCategoryVOList);
        } catch (Exception e) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response,JSONUtil.toJsonStr(result));
        }
    }
```

## 5.11 接口权限控制

此时虽然我们对后台不同角色进行了动态路由，但是对接口并没有实现不同角色的权限控制，下面就来实现它

前面在`UserDetailsServiceImpl`封装了`LoginUser`，但是其中并没有该用户对应的权限信息，需要封装权限信息方便后续权限控制的查询

代码实现

在`loadUserByUsername`方法中添加对后台用户的权限信息封装

`LoginUser`需要添加`List<String> permissons`属性用于存储权限信息

```java
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        // 是否查询成功 , 查询不到即抛出异常
        if (BeanUtil.isEmpty(user)) throw new RuntimeException(Consts.ERROR_LOGIN_USER_NOT_EXIST);
        // 如果是后台用户才需要封装权限信息
        if (user.getType().equals(Consts.LOGIN_USER_TYPE_ADMIN)){
            List<String> permissonList = menuMapper.listPermissonByUserId(user.getId());
            return new LoginUser(user,permissonList);
        }
        return new LoginUser(user);
    }
```

定义一个service类，用于判断当前用户是否具有该权限

```java
@Service("ps")
public class PermissionService {
    /**
     * 判断当前用户是否具有该权限
     * @param permission 要判断的权限
     * @return true：有该权限
     */
    public boolean hasPermission(String permission){
        // 如果是超级管理员，返回true
        if (SecurityUtils.isAdmin()) return true;
        // 获取当前用户的权限列表并校验
        List<String> permissonList = SecurityUtils.getLoginUser().getPermissons();
        return permissonList.contains(permission);

    }
}
```

在导入分类接口上加上如下@PreAuthorize注解，ps代表Service别名，调用其hasPermission方法，传入需要判断的权限，返回true则标明该接口可以访问

```java
    @PreAuthorize("@ps.hasPermission('content:category:export')")
```

==todo：==当我使用外部定义的常量时，该处会报错，如我已经定义常量PREMISSON_CONTENT_CATEGORY_EXPORT

`public static final String PREMISSON_CONTENT_CATEGORY_EXPORT="content:category:export";`

`@PreAuthorize("@ps.hasPermission(Consts.PREMISSON_CONTENT_CATEGORY_EXPORT)")`


## 5.12 查询文章列表

### 接口设计

接口路径`[GET] http://localhost:8989/content/article/list`，需要携带token请求头

Query格式请求参数：

pageNum：页码，pageSize：每页显示条数，title：文章标题，summary：文章摘要

相应格式：

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "rows": [
            {
                "id": 2,
                "title": "testArticle1",
                "content": "test1",
                "summary": "我是一个测试1",
                "categoryId": 2,
                "thumbnail": "https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/fd2e9460c58a4af3bbeae5d9ed581688.png",
                "isTop": "1",
                "status": "0",
                "viewCount": 22,
                "isComment": "0",
                "createTime": "2022-01-21 14:58:30",
                "delFlag": 0
            },
            {
                "id": 3,
                "title": "testArticle2",
                "content": "test2",
                "summary": "我是一个测试2",
                "categoryId": 1,
                "thumbnail": "https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/737a0ed0b8ea430d8700a12e76aa1cd1.png",
                "isTop": "1",
                "status": "0",
                "viewCount": 33,
                "isComment": "0",
                "createTime": "2022-01-18 14:58:34",
                "delFlag": 0
            }
        ],
        "total": 2
    }
}
```

### 代码实现

创建`ArticleListDTO`类，用于接收接收查询所有博文的DTO，包含属性title、summary，用于模糊查询

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDTO {
    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;
}
```

**控制层**`ArticleController`

```java
    @GetMapping("/list")
    ResponseResult listArticle(Integer pageNum , Integer pageSize ,ArticleListDTO articleListDTO){
        return articleService.listArticle(pageNum,pageSize,articleListDTO);
    }
```

**服务层**`ArticleServiceImpl`实现类


```java
    @Override
    public ResponseResult listArticle(Integer pageNum, Integer pageSize, ArticleListDTO articleListDTO) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 传入即根据 Title、Summary模糊查询
        queryWrapper
                .like(!StrUtil.isBlankIfStr(articleListDTO.getTitle()),Article::getTitle,articleListDTO.getTitle())
                .like(!StrUtil.isBlankIfStr(articleListDTO.getSummary()),Article::getSummary,articleListDTO.getSummary())
                .orderByDesc(Article::getCreateTime);
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Article> articleList = copyBeanList(page.getRecords(), Article.class);
        return ResponseResult.okResult(new PageVO(articleList,page.getTotal()));
    }
```


## 5.13 修改文章

### 接口设计

在查询文章页面，用户点击任一文章的修改按钮即可跳转到写博文页面，在写博文页面需要回显该文章的具体信息，修改完成后，点击更新按钮即可保存修改

#### 数据回显

接口路径：`[GET] http://localhost:8989/content/article/{id}`，需要携带token请求头

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "id": 7,
        "title": "2022Java从入门到入土",
        "content": "11"
        "summary": "2022Java学习路线，大概为期5个月，涉及算法，Java基础，SSM，SpringBoot2，分布式，微服务，MySQL，Redis等中间件，并发编程，JVM调优，八股文",
        "categoryId": 1,
        "thumbnail": "http://ridz0vduc.bkt.clouddn.com/2022/09/29/85cb754d5a6343eaae6fdadaf9717827.png",
        "isTop": "0",
        "status": "0",
        "viewCount": 0,
        "isComment": "1",
        "createBy": 1,
        "createTime": "2022-09-29 17:09:16",
        "updateBy": 1,
        "updateTime": "2022-09-29 17:09:16",
        "delFlag": 0,
        "tags": [
            1,
            2,
            3,
            4,
            5,
            9,
            15,
            16,
            17
        ]
    }
}
```

#### 文章更新

接口路径`[PUT] http://localhost:8989/content/article`，需要携带token请求头

请求体

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "id": 7,
        "title": "2022Java从入门到入土",
        "content": "11"
        "summary": "2022Java学习路线，大概为期5个月，涉及算法，Java基础，SSM，SpringBoot2，分布式，微服务，MySQL，Redis等中间件，并发编程，JVM调优，八股文",
        "categoryId": 1,
        "thumbnail": "http://ridz0vduc.bkt.clouddn.com/2022/09/29/85cb754d5a6343eaae6fdadaf9717827.png",
        "isTop": "0",
        "status": "0",
        "viewCount": 0,
        "isComment": "1",
        "createBy": 1,
        "createTime": "2022-09-29 17:09:16",
        "updateBy": 1,
        "updateTime": "2022-09-29 17:09:16",
        "delFlag": 0,
        "tags": [
            1,
            2,
            3,
            4,
            5,
            9,
            15,
            16,
            17
        ]
    }
}
```

### 代码实现

#### 数据回显

创建`ArticleUpdateVO`类，用于返回回显响应数据，比`Article`类增加一个tag属性，表示所关联标签id数组

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleUpdateVO {
    /**
     *
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 所属分类id
     */
    private Long categoryId;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 是否置顶（0否，1是）
     */
    private String isTop;

    /**
     * 状态（0已发布，1草稿）
     */
    private String status;

    /**
     * 访问量
     */
    private Long viewCount;

    /**
     * 是否允许评论 1是，0否
     */
    private String isComment;

    /**
     *
     */
    private Long createBy;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Long updateBy;

    /**
     *
     */
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

    /**
     * 所关联标签id数组
     */
    private List<Long> tags;
}
```

**控制层**`ArticleController`

```java
    @GetMapping("/{id}")
    ResponseResult getArticleById(@PathVariable("id") Long id){
        return articleService.getArticleById(id);
    }
```

**服务层**`ArticleServiceImpl`实现类

```java
    @Override
    public ResponseResult getArticleById(Long id) {
        Article article = getById(id);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> articleTagList = articleTagService.list(queryWrapper);
        List<Long> tagList = articleTagList
                .stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        ArticleUpdateVO articleUpdateVO = copyBeanSingle(article, ArticleUpdateVO.class);
        articleUpdateVO.setTags(tagList);
        return ResponseResult.okResult(articleUpdateVO);
    }
```

#### 文章更新

创建`ArticleUpdateDTO`类，用于接收更新数据，比`Article`类增加一个tag属性，表示所关联标签id数组

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleUpdateDTO {
    /**
     *
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 所属分类id
     */
    private Long categoryId;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 是否置顶（0否，1是）
     */
    private String isTop;

    /**
     * 状态（0已发布，1草稿）
     */
    private String status;

    /**
     * 访问量
     */
    private Long viewCount;

    /**
     * 是否允许评论 1是，0否
     */
    private String isComment;

    /**
     *
     */
    private Long createBy;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Long updateBy;

    /**
     *
     */
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

    /**
     * 所关联标签id数组
     */
    private List<Long> tags;

}
```

**控制层**`ArticleController`

```java
    @PutMapping
    ResponseResult updateArticle(@RequestBody ArticleUpdateDTO articleUpdateDTO){
        return articleService.updateArticle(articleUpdateDTO);
    }
```

**服务层**`ArticleServiceImpl`实现类

```java
    @Override
    public ResponseResult updateArticle(ArticleUpdateDTO articleUpdateDTO) {
        // 更新数据到Article表
        Article article = copyBeanSingle(articleUpdateDTO, Article.class);
        updateById(article);
        // 更新数据到ArticleTag表
        // 1. ArticleTag表中有相关数据的话，将ArticleTag表中articleId为该id的数据全部删除
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,articleUpdateDTO.getId());
        articleTagService.getBaseMapper().delete(queryWrapper);
        // 2. 插入字段
        List<ArticleTag> articleTagList = articleUpdateDTO.getTags()
                .stream()
                .map(tagId -> new ArticleTag(articleUpdateDTO.getId(), tagId))
                .collect(Collectors.toList());
        if (articleTagList.size() > 0) articleTagService.saveBatch(articleTagList);
        return ResponseResult.okResult();
    }
```

## 5.14 删除文章

在文章管理页面，管理员可以点击文章后的删除按钮或者选中多个文章对文章进行删除

接口路径`[DELETE] http://localhost:8989/content/article/{ids}`，需要携带token请求头

**控制层**`ArticleController`

```java
    // 删除Tag
    @DeleteMapping("/{ids}")
    ResponseResult deleteTag(@PathVariable Long[] ids){
        return articleService.deleteArticle(ids);
    }
```

**服务层**`ArticleServiceImpl`实现类


```java
    @Override
    public ResponseResult deleteArticle(Long[] ids) {
        int deleteRows = getBaseMapper().deleteBatchIds(Arrays.asList(ids));
        if (deleteRows <= 0) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_ARTICLE_DELETE);
        return ResponseResult.okResult();
    }
```

## 5.15 查询菜单列表

### 接口设计

在菜单栏点击菜单管理可以查询所有菜单，不分页，并且菜单呈树形结构，可以根据菜单名进行模糊查询，也可以根据菜单状态查询，菜单要求按照父菜单id和ordernum进行排序

接口路径`[GET] http://localhost:8989/system/menu/list`，需要携带token请求头<br />

Query请求参数

status：状态

menuName：菜单名

响应格式

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": [
        {
            "component": "content/category/index",
            "icon": "example",
            "id": 2018,
            "isFrame": 1,
            "menuName": "分类管理",
            "menuType": "C",
            "orderNum": 1,
            "parentId": 2017,
            "path": "category",
            "perms": "content:category:list",
            "remark": "",
            "status": "0",
            "visible": "0"
        },
        {
            "icon": "#",
            "id": 2028,
            "isFrame": 1,
            "menuName": "导出分类",
            "menuType": "F",
            "orderNum": 1,
            "parentId": 2018,
            "path": "",
            "perms": "content:category:export",
            "remark": "",
            "status": "0",
            "visible": "0"
        }
    ]
}
```

### 代码实现

创建`MenuListVO `用于响应数据

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuListVO {

    /**
     * 组件路径
     */
    private String component;

    /**
     * 菜单图标
     */
    private String icon;

    private Long id;

    /**
     * 是否为外链（0是 1否）
     */
    private Integer isFrame;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    private String visible;

}
```

创建**控制层**`MenuController`

```java
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Resource
    MenuService menuService;

    @GetMapping("/list")
    public ResponseResult list(String status,String menuName){
        return menuService.listMenu(status,menuName);
    }
}
```

**服务层**`MenuServiceImpl`实现类

```java
    @Override
    public ResponseResult listMenu(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(!StrUtil.isBlankIfStr(status),Menu::getStatus,status)
                .like(!StrUtil.isBlankIfStr(menuName),Menu::getMenuName,menuName)
                .orderByAsc(Menu::getParentId)
                .orderByAsc(Menu::getOrderNum);

        List<Menu> menuList = list(queryWrapper);
        List<MenuListVO> menuListVOS = BeanCopyUtils.copyBeanList(menuList, MenuListVO.class);
        return ResponseResult.okResult(menuListVOS);
    }
```

## 5.16 新增菜单

接口路径`[POST] http://localhost:8989/system/menu/list`，需要携带token请求头<br />

请求体属性跟Menu类对应的json数据一样，不再定义DTO

**控制层**`MenuController`

```java
    @PostMapping
    ResponseResult insertMenu(@RequestBody Menu menu){
        return menuService.insertMenu(menu);
    }
```

**服务层**`MenuServiceImpl`实现类

```java
    @Override
    public ResponseResult insertMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }
```

## 5.17 修改菜单

### 接口设计

#### 数据回显

接口路径`[GET] http://localhost:8989/system/menu/{id}`，需要携带token请求头

响应格式

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "component": "content/article/write/index",
        "icon": "build",
        "id": 2023,
        "isFrame": 1,
        "menuName": "写博文",
        "menuType": "C",
        "orderNum": 0,
        "parentId": 0,
        "path": "write",
        "perms": "content:article:writer",
        "remark": "",
        "status": "0",
        "visible": "0"
    }
}
```

#### 保存修改

接口路径`[PUT] http://localhost:8989/system/menu`，需要携带token请求头

不能将当前菜单设为自己的父菜单，如果这样做，则响应错误信息

```json
{
    "code": 500,
    "msg": "更新失败，自己不能作为自己的上级菜单"
}
```

### 代码实现

#### 数据回显

**控制层**`MenuController`


```java
    @GetMapping("/{id}")
    ResponseResult getMenuById(@PathVariable Long id){
        return menuService.getMenuById(id);
    }
```

**服务层**`MenuServiceImpl`实现类


```java
    @Override
    public ResponseResult getMenuById(Long id) {
        Menu menu = getById(id);
        MenuListVO menuListVO = BeanCopyUtils.copyBeanSingle(menu, MenuListVO.class);
        return ResponseResult.okResult(menuListVO);
    }
```

#### 保存修改

**控制层**`MenuController`


```java
    @PutMapping
    ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }
```

**服务层**`MenuServiceImpl`实现类


```java
    @Override
    public ResponseResult updateMenu(Menu menu) {
        if (menu.getParentId().equals(menu.getId())) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_MENU_UPDATE_PARENT);
        boolean updateSuccess = updateById(menu);
        if (!updateSuccess) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_MENU_UPDATE);
        return ResponseResult.okResult();
    }
```

## 5.18 删除菜单

接口路径`[DELETE] http://localhost:8989/system/menu/{id}`，需要携带token请求头<br />

当要删除的菜单有子菜单时，不允许删除，响应

```json
{
    "code":500,
    "msg":"删除失败，该菜单存在子菜单"
}
```

**控制层**`MenuController`

```java
    @DeleteMapping("/{id}")
    ResponseResult deleteMenu(@PathVariable Long id){
        return menuService.deleteMenu(id);
    }
```

**服务层**`MenuServiceImpl`实现类

```java
    @Override
    public ResponseResult deleteMenu(Long id) {
        // 查询有没有菜单的父菜单为它，如果有，不能删除
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,id);
        List<Menu> menuList = list(queryWrapper);
        if (menuList.size() > 0) return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, Consts.ERROR_ADMIN_MENU_DELETE);
        getBaseMapper().deleteById(id);
        return ResponseResult.okResult();
    }
```

## 5.19 查询角色列表

### 接口设计

接口路径`[GET] http://localhost:8989/system/role/list`，需要携带token请求头<br />

分页查询，可以对角色状态进行查询，可以对角色名称模糊查询，按照role_sort升序

Query请求参数：

pageNum：页码

pageSize：每页显示条数

roleName：角色名称

status：角色状态

响应格式：

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "rows": [
            {
                "id": 1,
                "roleName": "超级管理员",
                "roleKey": "admin",
                "roleSort": 1,
                "status": "0",
                "createTime": "2021-11-12 10:46:19"
            },
            {
                "id": 12,
                "roleName": "友链审核员",
                "roleKey": "link",
                "roleSort": 1,
                "status": "0",
                "createTime": "2022-01-16 06:49:30"
            },
            {
                "id": 2,
                "roleName": "普通角色",
                "roleKey": "common",
                "roleSort": 2,
                "status": "0",
                "createTime": "2021-11-12 10:46:19"
            }
        ],
        "total": 4
    }
}
```

### 代码实现

创建`RoleListVO `类用于封装响应字段

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleListVO {
    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;
}
```

创建**控制层**`RoleController `

```java
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Resource
    RoleService roleService;

    @GetMapping("/list")
    ResponseResult listRole(String status,String roleName,Integer pageNum,Integer pageSize){
        return roleService.listPageRole(status,roleName,pageNum,pageSize);
    }
}
```

**服务层**`RoleServiceImpl`实现类

```java
    @Override
    public ResponseResult listPageRole(String status, String roleName, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(!StrUtil.isBlankIfStr(status), Role::getStatus, status)
                .like(!StrUtil.isBlankIfStr(roleName), Role::getRoleName, roleName)
                .orderByAsc(Role::getRoleSort);
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<RoleListVO> roleVOList = BeanCopyUtils.copyBeanList(page.getRecords(), RoleListVO.class);
        return ResponseResult.okResult(new PageVO(roleVOList,page.getTotal()));
    }
```

## 5.20 改变角色状态

### 接口设计

在查询到角色列表后，所有角色右方均有可调节是否禁用的开关

接口路径`[PUT] http://localhost:8989/system/role/changeStatus`，需要携带token请求头<br />

请求体

```json
{
    "roleId":11,
    "status":"1"
}
```

最初的思想是`changeStatus(@RequestBody Long id, String status)`直接接收id和status，发现会反序列化失败，所以考虑创建`RoleChangeStatusDTO `用于接收数据

### 代码实现

`RoleChangeStatusDTO `类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleChangeStatusDTO {

    Long id;

    String status;
}
```

**控制层**`RoleController `

```java
    @PutMapping("/changeStatus")
    ResponseResult changeStatus(@RequestBody RoleChangeStatusDTO roleChangeStatusDTO){
        return roleService.changeStatus(roleChangeStatusDTO);
    }
```

**服务层**`RoleServiceImpl`实现类

```java
    @Override
    public ResponseResult changeStatus(RoleChangeStatusDTO roleChangeStatusDTO) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(Role::getId,roleChangeStatusDTO.getId())
                .set(!StrUtil.isBlankIfStr(roleChangeStatusDTO.getStatus()), Role::getStatus, roleChangeStatusDTO.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult();
    }
```

## 5.21 新增角色

### 接口设计

#### 菜单数据回显

接口路径`[GET] http://localhost:8989/system/menu/treeselect`，需要携带token请求头<br />

在点击新增角色按钮后，弹窗内需要展示所有菜单信息供选择，用于该新角色的权限管理

我们无法复用前面的接口，因为前面的需要根据id查询，此处是查询所有的

响应格式

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": [
        {
            "id": "1",
            "menuName": "系统管理",
            "parentId": "0",
            "children": [
                {
                    "id": "100",
                    "menuName": "用户管理",
                    "parentId": "1",
                    "children": [
                        {
                            "id": "1001",
                            "menuName": "用户查询",
                            "parentId": "100",
                            "children": []
                        },
                        {
                            "id": "1002",
                            "menuName": "用户新增",
                            "parentId": "100",
                            "children": []
                        },
                        {
                            "id": "1003",
                            "menuName": "用户修改",
                            "parentId": "100",
                            "children": []
                        },
                        {
                            "id": "1004",
                            "menuName": "用户删除",
                            "parentId": "100",
                            "children": []
                        },
                        {
                            "id": "1005",
                            "menuName": "用户导出",
                            "parentId": "100",
                            "children": []
                        },
                        {
                            "id": "1006",
                            "menuName": "用户导入",
                            "parentId": "100",
                            "children": []
                        },
                        {
                            "id": "1007",
                            "menuName": "重置密码",
                            "parentId": "100",
                            "children": []
                        }
                    ]
                },
                {
                    "id": "101",
                    "menuName": "角色管理",
                    "parentId": "1",
                    "children": [
                        {
                            "id": "1008",
                            "menuName": "角色查询",
                            "parentId": "101",
                            "children": []
                        },
                        {
                            "id": "1009",
                            "menuName": "角色新增",
                            "parentId": "101",
                            "children": []
                        },
                        {
                            "id": "1010",
                            "menuName": "角色修改",
                            "parentId": "101",
                            "children": []
                        },
                        {
                            "id": "1011",
                            "menuName": "角色删除",
                            "parentId": "101",
                            "children": []
                        },
                        {
                            "id": "1012",
                            "menuName": "角色导出",
                            "parentId": "101",
                            "children": []
                        }
                    ]
                },
                {
                    "id": "102",
                    "menuName": "菜单管理",
                    "parentId": "1",
                    "children": [
                        {
                            "id": "1013",
                            "menuName": "菜单查询",
                            "parentId": "102",
                            "children": []
                        },
                        {
                            "id": "1014",
                            "menuName": "菜单新增",
                            "parentId": "102",
                            "children": []
                        },
                        {
                            "id": "1015",
                            "menuName": "菜单修改",
                            "parentId": "102",
                            "children": []
                        },
                        {
                            "id": "1016",
                            "menuName": "菜单删除",
                            "parentId": "102",
                            "children": []
                        }
                    ]
                }
            ]
        },
        {
            "id": "2017",
            "menuName": "内容管理",
            "parentId": "0",
            "children": [
                {
                    "id": "2018",
                    "menuName": "分类管理",
                    "parentId": "2017",
                    "children": [
                        {
                            "id": "2028",
                            "menuName": "导出分类",
                            "parentId": "2018",
                            "children": []
                        }
                    ]
                },
                {
                    "id": "2019",
                    "menuName": "文章管理",
                    "parentId": "2017",
                    "children": []
                },
                {
                    "id": "2021",
                    "menuName": "标签管理",
                    "parentId": "2017",
                    "children": []
                },
                {
                    "id": "2022",
                    "menuName": "友链管理",
                    "parentId": "2017",
                    "children": [
                        {
                            "id": "2024",
                            "menuName": "友链新增",
                            "parentId": "2022",
                            "children": []
                        },
                        {
                            "id": "2025",
                            "menuName": "友链修改",
                            "parentId": "2022",
                            "children": []
                        },
                        {
                            "id": "2026",
                            "menuName": "友链删除",
                            "parentId": "2022",
                            "children": []
                        },
                        {
                            "id": "2027",
                            "menuName": "友链查询",
                            "parentId": "2022",
                            "children": []
                        }
                    ]
                }
            ]
        },
        {
            "id": "2023",
            "menuName": "写博文",
            "parentId": "0",
            "children": [
                {
                    "id": "2029",
                    "menuName": "写博文标题",
                    "parentId": "2023",
                    "children": []
                }
            ]
        }
    ]
}
```

#### 添加角色

接口路径`[POST] http://localhost:8989/system/role`，需要携带token请求头

请求体

```json
{
    "roleName":"测试",
    "roleKey":"sss",
    "roleSort":1,
    "status":"0",
    "menuIds": [
        "2023",
        "2030"
    ]
}
```

### 代码实现

#### 菜单数据回显

查看响应格式可知，需要新建VO用于封装id，menuName，parentId，children属性

新建`MenuTreeVO `类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuTreeVO {

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    Long id;

    String menuName;

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    Long parentId;

    List<MenuTreeVO> children;

    public MenuTreeVO(Long id, String menuName, Long parentId) {
        this.id = id;
        this.menuName= label;
        this.parentId = parentId;
    }
}
```

**控制层**`MenuController`

```java
    @GetMapping("/treeselect")
    ResponseResult treeselect(){
        List<MenuTreeVO> menuTreeVOS = BeanCopyUtils.copyBeanList(menuService.listAllMenuTree(), MenuTreeVO.class);
        return ResponseResult.okResult(menuTreeVOS);
    }

```

**服务层**`MenuServiceImpl`实现类

`listAllMenuTree`方法首先需要查询出所有父菜单，再调用`treeAllMenu`传入父菜单集合，设置子菜单

```java
    @Override
    public List<Menu> listAllMenuTree() {
        // 查询所有数据
        // 获得一级数据：parentId==0
        // 获得二级数据：menuType in 'C,M' 并且 parentId
        // 获得三级数据：menuType = F 并且 parentId

        // 查询所有数据
        List<Menu> menuList = list();
        List<Menu> parentMenuList = builderMenuTree(menuList, 0L);
        return parentMenuList;
    }
```

`treeAllMenu`方法，对每一个父菜单调用Mapper层`listMenuTreeVOChildren`方法，查询其对应的子元素


```java

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
```

#### 添加角色

创建`RoleInsertDTO `类用于接收数据，包括`List<Long> menuIds`用于接收其含有的菜单权限信息

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleInsertDTO {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 菜单权限集合
     */
    private List<Long> menuIds;

    /**
     * 备注
     */
    private String remark;
}
```

**控制层**`RoleController`

```java
    @PostMapping
    ResponseResult insertRole(@RequestBody RoleInsertDTO roleInsertDTO){
        return roleService.insertRole(roleInsertDTO);
    }
```

**服务层**`MenuServiceImpl`实现类

设计到对role、role_menu两个表操作，先插入基本数据到role表，再插入菜单权限集合到role_menu表，注入`RoleMenuService `用于操作role_menu表

```java
    @Resource
    RoleMenuService roleMenuService;

    @Override
    public ResponseResult insertRole(RoleInsertDTO roleInsertDTO) {
        // 插入基本数据到role表
        Role role = BeanCopyUtils.copyBeanSingle(roleInsertDTO, Role.class);
        save(role);
        // 插入菜单权限集合到role_menu表
        List<RoleMenu> roleMenuList = roleInsertDTO.getMenuIds()
                .stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
        return ResponseResult.okResult();
    }
```

## 5.22 删除角色

接口路径`[GET] http://localhost:8989/system/role/{id}`，需要携带token请求头

**控制层**`RoleController`，没有提供删除多条，所以只有在删除一条记录后才是正常

```java
    @DeleteMapping("/{id}")
    ResponseResult deleteRole(@PathVariable Long id){
        return roleService.deleteRole(id) == 1 ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);
    }
```

**服务层**`RoleServiceImpl`实现类

```java
 @Override
    public int deleteRole(Long id) {
        return getBaseMapper().deleteById(id);
    }
```

## 5.23 修改角色

### 接口设计

#### 角色信息回显

接口路径`[GET] http://localhost:8989/system/role/{id}`，需要携带token请求头

响应格式与查询角色列表类似，所以我们复用`RoleListVO `类，并添加一个remark字段

响应格式

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "id": 11,
        "roleName": "嘎嘎嘎",
        "roleKey": "aggag",
        "roleSort": 5,
        "status": "0",
        "createTime": "2022-01-06 14:07:40",
        "remark": "嘎嘎嘎"
    }
}
```

#### 根据角色ID查询菜单下拉树结构

接口路径`[GET] http://localhost:8989/system/menu/roleMenuTreeselect/{id}`，需要携带token请求头响应格式

响应格式

menus：菜单树

checkedKeys：角色所关联的菜单权限id列表

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "menus": [
            {
                "id": "1",
                "menuName": "系统管理",
                "parentId": "0",
                "children": [
                    {
                        "id": "100",
                        "menuName": "用户管理",
                        "parentId": "1",
                        "children": [
                            {
                                "id": "1001",
                                "menuName": "用户查询",
                                "parentId": "100",
                                "children": []
                            },
                            {
                                "id": "1002",
                                "menuName": "用户新增",
                                "parentId": "100",
                                "children": []
                            },
                            {
                                "id": "1003",
                                "menuName": "用户修改",
                                "parentId": "100",
                                "children": []
                            },
                            {
                                "id": "1004",
                                "menuName": "用户删除",
                                "parentId": "100",
                                "children": []
                            },
                            {
                                "id": "1005",
                                "menuName": "用户导出",
                                "parentId": "100",
                                "children": []
                            },
                            {
                                "id": "1006",
                                "menuName": "用户导入",
                                "parentId": "100",
                                "children": []
                            },
                            {
                                "id": "1007",
                                "menuName": "重置密码",
                                "parentId": "100",
                                "children": []
                            }
                        ]
                    },
                    {
                        "id": "101",
                        "menuName": "角色管理",
                        "parentId": "1",
                        "children": [
                            {
                                "id": "1008",
                                "menuName": "角色查询",
                                "parentId": "101",
                                "children": []
                            },
                            {
                                "id": "1009",
                                "menuName": "角色新增",
                                "parentId": "101",
                                "children": []
                            },
                            {
                                "id": "1010",
                                "menuName": "角色修改",
                                "parentId": "101",
                                "children": []
                            },
                            {
                                "id": "1011",
                                "menuName": "角色删除",
                                "parentId": "101",
                                "children": []
                            },
                            {
                                "id": "1012",
                                "menuName": "角色导出",
                                "parentId": "101",
                                "children": []
                            }
                        ]
                    },
                    {
                        "id": "102",
                        "menuName": "菜单管理",
                        "parentId": "1",
                        "children": [
                            {
                                "id": "1013",
                                "menuName": "菜单查询",
                                "parentId": "102",
                                "children": []
                            },
                            {
                                "id": "1014",
                                "menuName": "菜单新增",
                                "parentId": "102",
                                "children": []
                            },
                            {
                                "id": "1015",
                                "menuName": "菜单修改",
                                "parentId": "102",
                                "children": []
                            },
                            {
                                "id": "1016",
                                "menuName": "菜单删除",
                                "parentId": "102",
                                "children": []
                            }
                        ]
                    }
                ]
            },
            {
                "id": "2017",
                "menuName": "内容管理",
                "parentId": "0",
                "children": [
                    {
                        "id": "2018",
                        "menuName": "分类管理",
                        "parentId": "2017",
                        "children": [
                            {
                                "id": "2028",
                                "menuName": "导出分类",
                                "parentId": "2018",
                                "children": []
                            }
                        ]
                    },
                    {
                        "id": "2019",
                        "menuName": "文章管理",
                        "parentId": "2017",
                        "children": []
                    },
                    {
                        "id": "2021",
                        "menuName": "标签管理",
                        "parentId": "2017",
                        "children": []
                    },
                    {
                        "id": "2022",
                        "menuName": "友链管理",
                        "parentId": "2017",
                        "children": [
                            {
                                "id": "2024",
                                "menuName": "友链新增",
                                "parentId": "2022",
                                "children": []
                            },
                            {
                                "id": "2025",
                                "menuName": "友链修改",
                                "parentId": "2022",
                                "children": []
                            },
                            {
                                "id": "2026",
                                "menuName": "友链删除",
                                "parentId": "2022",
                                "children": []
                            },
                            {
                                "id": "2027",
                                "menuName": "友链查询",
                                "parentId": "2022",
                                "children": []
                            }
                        ]
                    }
                ]
            },
            {
                "id": "2023",
                "menuName": "写博文",
                "parentId": "0",
                "children": [
                    {
                        "id": "2029",
                        "menuName": "写博文标题",
                        "parentId": "2023",
                        "children": []
                    }
                ]
            }
        ],
        "checkedKeys": [
            2017,
            2022,
            2024,
            2025,
            2026,
            2027
        ]
    }
}
```

#### 保存修改角色

接口路径`[PUT] http://localhost:8989/system/system/role`，需要携带token请求头

请求体

```json
{
    "id":16,
    "roleName":"11nihao测试机",
    "roleKey":"22222属实是",
    "roleSort":1,
    "status":"0",
    "remark":"测试啊",
    "menuIds": [
        "2023",
        "2029"
    ]
}
```

### 代码实现

#### 角色信息回显

**控制层**`RoleController`

```java
    @GetMapping("/{id}")
    ResponseResult getRoleById(@PathVariable Long id){
        return roleService.getRoleById(id);
    }
```

**服务层**`MenuServiceImpl`实现类

```java
    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = getById(id);
        RoleListVO roleVO = BeanCopyUtils.copyBeanSingle(role, RoleListVO.class);
        return ResponseResult.okResult(roleVO);
    }
```

#### 根据角色ID查询菜单下拉树结构

**控制层**`MenuController`

```java
    @GetMapping("/roleMenuTreeselect/{id}")
    ResponseResult roleMenuTreeselect(@PathVariable Long id){
        // 查询所有菜单树
        List<MenuTreeVO> menuTreeVOS = BeanCopyUtils.copyBeanList(menuService.listAllMenuTree(), MenuTreeVO.class);
        // 查询角色所关联的菜单权限id列表
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        List<Long> listMenuByRoleId = roleMenuService.list(queryWrapper)
                .stream()
                .map(roleMenu -> roleMenu.getMenuId())
                .collect(Collectors.toList());
        return ResponseResult.okResult(new RoleMenuTreeVO(menuTreeVOS,listMenuByRoleId));
    }
```

服务层复用前面的**查询全部菜单树**的方法

#### 保存修改角色

**控制层**`RoleController`

```java
    @PutMapping
    ResponseResult updateRole(@RequestBody RoleUpdateDTO roleUpdateDTO){
         return roleService.updateRole(roleUpdateDTO) ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);
    }
```

**服务层**`RoleServiceImpl`实现类

```java
    @Override
    @Transactional
    public boolean updateRole(RoleUpdateDTO roleUpdateDTO) {
        // 更新基本信息到role表
        Role role = BeanCopyUtils.copyBeanSingle(roleUpdateDTO, Role.class);
        updateById(role);
        // 1. ArticleTag表中有相关数据的话，将ArticleTag表中articleId为该id的数据全部删除
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,roleUpdateDTO.getId());
        roleMenuService.getBaseMapper().delete(queryWrapper);
        // menuIds到role_menu表
        List<RoleMenu> roleMenuList = roleUpdateDTO.getMenuIds()
                .stream()
                .map(menuId -> new RoleMenu(roleUpdateDTO.getId(), menuId))
                .collect(Collectors.toList());
        if (roleMenuList.size() > 0) return roleMenuService.saveBatch(roleMenuList);
        return false;
    }
```

## 5.24 查询用户列表

### 接口设计

接口路径`[GET] http://localhost:8989/system/user/list`，需要携带token请求头<br />

分页查询，可以对角色状态进行查询，可以对角色名称模糊查询，按照role_sort升序

Query请求参数：

pageNum：页码

pageSize：每页显示条数

userName：用户名

phonenumber：手机号

status：状态

响应格式

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "rows": [
            {
                "avatar": "http://ridz0vduc.bkt.clouddn.com/Fl9G6S83Z2gBRXghACidVWbUTcr5",
                "createTime": "2022-01-05 09:01:56",
                "email": "23412332@qq.com",
                "id": 1,
                "nickName": "sg333",
                "phonenumber": "18888888888",
                "sex": "1",
                "status": "0",
                "userName": "sg"
            },
            {
                "createTime": "2022-01-05 13:28:43",
                "id": 3,
                "nickName": "weqe",
                "sex": "0",
                "status": "0",
                "userName": "sg3"
            },
            {
                "email": "23412332@qq.com",
                "id": 4,
                "nickName": "dsadd",
                "phonenumber": "19098790742",
                "sex": "0",
                "status": "0",
                "userName": "sg2"
            },
            {
                "createTime": "2022-01-06 03:51:13",
                "id": 5,
                "nickName": "tteqe",
                "phonenumber": "18246845873",
                "sex": "1",
                "status": "0",
                "userName": "sg2233"
            }
        ],
        "total": 4
    }
}
```

### 代码实现

根据接口响应格式要求创建VO用于封装响应数据

```java
@Data
@AllArgsConstructor
public class UserListVO {
    /**
     * 头像
     */
    private String avatar;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 邮箱
     */
    private String email;

    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phonenumber;


    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;
    /**
     * 账号状态（0正常 1停用）
     */
    private String status;

    /**
     * 用户名
     */
    private String userName;
}
```

创建**控制层**`UserController `

```java
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/list")
    ResponseResult listUser(Integer pageNum,Integer pageSize, String userName,String phonenumber,String status){
        PageVO pageVO=userService.listPageUser(pageNum,pageSize,userName,phonenumber,status);
        return ResponseResult.okResult(pageVO);
    }
}
```

**服务层**`UserServiceImpl`实现类

```java
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
```

## 5.25 新增用户

### 接口设计

#### 角色列表回显

接口路径`[GET] http://localhost:8989/system/role/listAllRole`，需要携带token请求头


响应格式

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": [
        {
            "id": 1,
            "roleName": "超级管理员",
            "roleKey": "admin",
            "roleSort": 1,
            "status": "0",
            "createTime": "2021-11-12 10:46:19",
            "remark": "超级管理员"
        },
        {
            "id": 2,
            "roleName": "普通角色",
            "roleKey": "common",
            "roleSort": 2,
            "status": "0",
            "createTime": "2021-11-12 10:46:19",
            "remark": "普通角色"
        },
        {
            "id": 11,
            "roleName": "嘎嘎嘎",
            "roleKey": "aggag",
            "roleSort": 5,
            "status": "0",
            "createTime": "2022-01-06 14:07:40",
            "remark": "嘎嘎嘎"
        },
        {
            "id": 12,
            "roleName": "友链审核员",
            "roleKey": "link",
            "roleSort": 1,
            "status": "0",
            "createTime": "2022-01-16 06:49:30"
        }
    ]
}
```

#### 添加用户

接口路径`[POST] http://localhost:8989/system/user`，需要携带token请求头

需要对password加密存储、检查userName、email、phonenumber是否存在

请求体

```json
{
    "userName":"hh",
    "nickName":"你好",
    "password":"123456",
    "phonenumber":"13888888888",
    "email":"123@qq.com",
    "sex":"2",
    "status":"0",
    "roleIds":[11,2,12]
}
```


### 代码实现

#### 角色列表回显

**控制层**`RoleController`

```java
    @GetMapping("/listAllRole")
    ResponseResult listAllRole(){
        List<Role> roleList = roleService.listAllRole();
        List<RoleListVO> roleListVOS = BeanCopyUtils.copyBeanList(roleList, RoleListVO.class);
        return ResponseResult.okResult(roleListVOS);
    }
```

**服务层**`RoleServiceImpl`实现类

```java
    @Override
    public List<Role> listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus,Consts.ROLE_STATUS_NORMAL);
        return list(queryWrapper);
    }
```

#### 添加用户

**控制层**`UserController`

```java
    @PostMapping
    ResponseResult insertUser(@RequestBody UserInsertDTO userInsertDTO){
        if (StrUtil.isBlankIfStr(userInsertDTO.getUserName())) throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        // 数据校验:查询用户名，邮箱，手机号是否存在
        if (userService.checkUserNameExist(userInsertDTO.getUserName()))    throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        if (userService.checkUserEmailExist(userInsertDTO.getEmail()))    throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        if (userService.checkUserPhonenumberExist(userInsertDTO.getPhonenumber()))    throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);

        return userService.insertUser(userInsertDTO) ?  ResponseResult.okResult(): ResponseResult.errorResult(SYSTEM_ERROR);

    }
```

**服务层**`UserServiceImpl`

```java
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
```

## 5.26 删除用户

接口路径`[DELETE] http://localhost:8989/system/user/{ids}`，需要携带token请求头

**控制层**`UserController`

```java
    @DeleteMapping("/{ids}")
    ResponseResult deleteUser(@PathVariable Long[] id){
        return userService.deleteUser(id) == 1 ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);

    }
```

**服务层**`UserServiceImpl`

```java
    @Override
    public int deleteUser(Long id) {
        int delete = getBaseMapper().deleteById(id);
        return delete;
    }
```

## 5.27 修改用户

### 接口设计

#### 根据用户id回显用户信息

接口路径`[GET] http://localhost:8989/system/user/{id}`，需要携带token请求头

响应格式

roleIds用户所关联的角色id集合

roles所有角色的id集合

user用户信息

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "roleIds": [
            2
        ],
        "roles": [
            {
                "id": 1,
                "roleName": "超级管理员",
                "roleKey": "admin",
                "roleSort": 1,
                "status": "0",
                "createTime": "2021-11-12 10:46:19",
                "remark": "超级管理员"
            },
            {
                "id": 2,
                "roleName": "普通角色",
                "roleKey": "common",
                "roleSort": 2,
                "status": "0",
                "createTime": "2021-11-12 10:46:19",
                "remark": "普通角色"
            },
            {
                "id": 11,
                "roleName": "嘎嘎嘎",
                "roleKey": "aggag",
                "roleSort": 5,
                "status": "0",
                "createTime": "2022-01-06 14:07:40",
                "remark": "嘎嘎嘎"
            },
            {
                "id": 12,
                "roleName": "友链审核员",
                "roleKey": "link",
                "roleSort": 1,
                "status": "0",
                "createTime": "2022-01-16 06:49:30"
            }
        ],
        "user": {
            "avatar": "http://ridz0vduc.bkt.clouddn.com/2022/09/29/e580790f6fcf4d7695d05cf27513a8ad.jpg",
            "email": "123@qq.com",
            "id": 14787164048665,
            "nickName": "胖虎123456@Aa",
            "phonenumber": "1399999999",
            "sex": "2",
            "status": "0",
            "userName": "panghu"
        }
    }
}
```

#### 修改用户

接口路径`[POST] http://localhost:8989/system/user`，需要携带token请求头

请求体

```json

{
    "createTime":"2022-10-05 16:59:07",
    "email":"1455@qq.com",
    "id":14787164048668,
    "nickName":"测试",
    "phonenumber":"13888888888",
    "sex":"0",
    "status":"0",
    "userName":"nishi",
    "roleIds":[1,11,12]
}
```

### 代码实现

#### 根据用户id回显用户信息

根据响应格式创建VO

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailVO {

    List<Long> roleIds;

    List<RoleListVO> roles;

    UserListVO user;
}
```

**控制层**`UserController`

```java
    @Resource
    UserService userService;

    @Resource
    UserRoleService userRoleService;

    @Resource
    RoleService roleService;   

    @GetMapping("/{id}")
    ResponseResult getUserDetailById(@PathVariable Long id){
        UserDetailVO userDetailVO=new UserDetailVO();
        // 查询 roleIds用户所关联的角色id集合
        userDetailVO.setRoleIds(userRoleService.listRoleById(id));
        // 查询 user用户信息
        userDetailVO.setUser(BeanCopyUtils.copyBeanSingle(userService.getById(id), UserListVO.class));
        // 查询 roles所有角色的id集合
        userDetailVO.setRoles(BeanCopyUtils.copyBeanList(roleService.listAllRole(), RoleListVO.class));
        return ResponseResult.okResult(userDetailVO);
    }
```

**服务层**

`UserRoleServiceImpl`实现类

```java
    @Override
    public List<Long> listRoleById(Long id) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        List<Long> roleIdList = list(queryWrapper)
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        return roleIdList;
    }
```

查询 user用户信息直接使用iService自带的方法

查询 roles所有角色的id集合复用前面的不分页查询所有role的`listAllRole`方法

#### 修改用户

**控制层**`UserController`

```java
    @PutMapping
    ResponseResult updateUser(@RequestBody UserUpdateDTO userUpdateDTO){
        boolean updateUser = userService.updateUser(userUpdateDTO);
        return updateUser  ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);

    }
```

**服务层**

`UserServiceImpl`实现类

```java
    @Transactional
    @Override
    public boolean updateUser(UserUpdateDTO userUpdateDTO) {
        // 更新基本信息到user表
        boolean updateUser = updateById(BeanCopyUtils.copyBeanSingle(userUpdateDTO, User.class));
        // 更新用户角色关系到user_role表
        boolean updateUserRole = userRoleService.updateUserRole(userUpdateDTO.getId(),userUpdateDTO.getRoleIds());
        return updateUser && updateUserRole;
    }
```


`UserRoleServiceImpl`实现类

```java
    @Transactional
    @Override
    public boolean updateUserRole(Long userId, List<Long> roleIds) {
        // 先把原数据全部删除
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,userId);
        getBaseMapper().delete(queryWrapper);
        // 如果修改后无role，直接返回
        if (roleIds.size() == 0) return true;
        // 插入新关系到user_role表
        List<UserRole> userRoleList = roleIds
                .stream()
                .map(roleId -> new UserRole(userId, roleId))
                .collect(Collectors.toList());
        boolean saveBatch = saveBatch(userRoleList);
        return saveBatch;
    }
```

## 5.28 查询分类列表

接口路径`[GET] http://localhost:8989/content/category/list`，需要携带token请求头

Query请求参数

pageNum：页码

pageSize：每页显示条数

name：分类名

status：分类状态

响应格式

```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "rows": [
            {
                "id": 2,
                "name": "PHP",
                "description": "是在服务器端执行的脚本语言",
                "status": "0"
            },
            {
                "id": 15,
                "name": "SpringBoot",
                "description": "一种Java开发框架",
                "status": "0"
            }
        ],
        "total": 2
    }
}
```

**控制层**`CategoryController`

```java
    @GetMapping("/list")
    ResponseResult listCategory(String name, String status, Integer pageNum , Integer pageSize){
        PageVO pageVO  = categoryService.listPageCategory(name,status,pageNum,pageSize);
        return ResponseResult.okResult(pageVO);
    }
```

**服务层**`CategoryServiceImpl`实现类

```java
    @Override
    public PageVO listPageCategory(String name, String status, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(!StrUtil.isBlankIfStr(status),Category::getStatus, status)
                .like(!StrUtil.isBlankIfStr(name),Category::getName, name);
        Page<Category> page=new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<CategoryListVO> categoryListVOS = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryListVO.class);
        return new PageVO(categoryListVOS,page.getTotal());
    }
```

## 5.29 新增分类

接口路径`[POST] http://localhost:8989/content/category/list`，需要携带token请求头

请求体

```json
{
    "name":"Redis",
    "description":"缓存中间件",
    "status":0
}
```

创建DTO用于接收参数

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInsertDTO {
    String name;
    String description;
    String status;
}
```

**控制层**`CategoryController`

```java
    @PostMapping
    ResponseResult insertCategory(@RequestBody CategoryInsertDTO categoryInsertDTO){
        Category category = BeanCopyUtils.copyBeanSingle(categoryInsertDTO, Category.class);
        return categoryService.save(category) ?  ResponseResult.okResult(): ResponseResult.errorResult(SYSTEM_ERROR);
    }
```

## 5.30 删除分类

接口路径`[DELETE] http://localhost:8989/content/category/list/{id}`，需要携带token请求头

可以批量删除

```java
    @DeleteMapping("/{id}")
    ResponseResult deleteCategoryByIds(@PathVariable("id") Long[] ids){
        return categoryService.deleteCategoryByIds(ids) >= 1 ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);
    }
```

## 5.31 修改分类

### 接口设计

#### 分类信息回显

接口路径`[GET] http://localhost:8989/content/category/{id}`，需要携带token请求头

#### 修改分类

接口路径`[PUT] http://localhost:8989/content/category`，需要携带token请求头

请求体

```json
{
    "id":2,
    "name":"PHP1",
    "description":"是在服务器端执行的脚本语言1",
    "status":"1"
}
```

### 代码实现

#### 分类信息回显

```java
    @GetMapping("/{id}")
    ResponseResult getCategoryById(@PathVariable Long id){
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBeanSingle(category, CategoryListVO.class));
    }
```

#### 修改分类

```java
    @PutMapping
    ResponseResult updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO){
        Category category = BeanCopyUtils.copyBeanSingle(categoryUpdateDTO, Category.class);
        return categoryService.updateById(category) ? ResponseResult.okResult(): ResponseResult.errorResult(SYSTEM_ERROR);
    }
```

## 5.32 查询友链列表

接口路径`[GET] http://localhost:8989/content/link/list`，需要携带token请求头

创建**控制层**`LinkController`

```java
@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Resource
    LinkService linkService;

    @GetMapping("/list")
    ResponseResult listLink(String name, String status, Integer pageNum , Integer pageSize){
        PageVO pageVO  = linkService.listPageLink(name,status,pageNum,pageSize);
        return ResponseResult.okResult(pageVO);
    }
}
```

**服务层**`LinkServiceImpl`实现类

```java
    @Override
    public PageVO listPageLink(String name, String status, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(!StrUtil.isBlankIfStr(status),Link::getStatus,status)
                .like(!StrUtil.isBlankIfStr(name),Link::getStatus,name);
        Page<Link> page=new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<LinkListVO> linkListVOS = BeanCopyUtils.copyBeanList(page.getRecords(), LinkListVO.class);
        return new PageVO(linkListVOS,page.getTotal());
    }
```

## 5.33 新增友链

接口路径`[POST] http://localhost:8989/content/link`，需要携带token请求头

请求体

```json
{
    "name":"测试1",
    "description":"测试2",
    "address":"https://github.com/xhu-zfx",
    "logo":"http://ridz0vduc.bkt.clouddn.com/2022/09/29/e580790f6fcf4d7695d05cf27513a8ad.jpg",
    "status":"2"
}
```

创建DTO用于接收插入数据

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkInsertDTO {
    /**
     *
     */
    private String name;

    /**
     *
     */
    private String logo;

    /**
     *
     */
    private String description;

    /**
     * 网站地址
     */
    private String address;

    /**
     * 审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
     */
    private String status;

}
```

**控制层**`LinkController`

```java
    @PostMapping
    ResponseResult insertLink(@RequestBody LinkInsertDTO linkInsertDTO){
        Link link = BeanCopyUtils.copyBeanSingle(linkInsertDTO, Link.class);
        return linkService.save(link) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
```

## 5.34 删除友链

接口路径`[GET] http://localhost:8989/content/link/{ids}`，需要携带token请求头

**控制层**`LinkController`

```java
    @DeleteMapping("/{ids}")
    ResponseResult deleteLinkByIds(@PathVariable Long[] ids){
        return linkService.deleteLinkByIds(ids) >= 1 ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);

    }
```

**服务层**`LinkServiceImpl`

```java
    @Override
    public int deleteLinkByIds(Long[] ids) {
        int deleteBatch = getBaseMapper().deleteBatchIds(Arrays.asList(ids));
        return deleteBatch;
    }
```

## 5.35 修改友链

### 接口设计

#### 友链信息回显

接口路径`[GET] http://localhost:8989/content/link/{id}`，需要携带token请求头

#### 修改友链

接口路径`[PUT] http://localhost:8989/content/link`，需要携带token请求头

请求体

```json
{
    "id":4,
    "name":"PANGHU1",
    "logo":"http://ridz0vduc.bkt.clouddn.com/2022/09/29/e580790f6fcf4d7695d05cf27513a8ad.jpg",
    "description":"panghu1",
    "address":"https://github.com/xhu-zfx",
    "status":"0"
}
```

### 代码实现

#### 友链信息回显

```java
    @GetMapping("/{id}")
    ResponseResult getLinkById(@PathVariable Long id){
        Link link = linkService.getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBeanSingle(link, LinkListVO.class));
    }
```

#### 修改友链

```java
    @PutMapping
    ResponseResult updateLink(@RequestBody LinkUpdateDTO linkUpdateDTO){
        Link link = BeanCopyUtils.copyBeanSingle(linkUpdateDTO, Link.class);
        return linkService.updateById(link) ? ResponseResult.okResult(): ResponseResult.errorResult(SYSTEM_ERROR);
    }
```

[^1]: 由于map必须要返回值，该处进行的又是为属性赋值set的操作，Lombok生成的set没有返回值，所以上面在MenuVO类上添加...


[^2]: 睁眼一看Error querying database说明是sql语句出问题了，再睁眼一看，发现是老朋友IndexOutOfBo...


[^3]: ### 头像上传代码实现


    ```java
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
    ```

    `PathUtils `作用是设置文件保存时的名字、后缀名

    ```java
    public class PathUtils {

        public static String generateFilePath(String fileName){
            //根据日期生成路径   2022/1/15/
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            String datePath = sdf.format(new Date());
            //uuid作为文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //后缀和文件后缀一致
            int index = fileName.lastIndexOf(".");
            // test.jpg -> .jpg
            String fileType = fileName.substring(index);
            return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
        }
    }
    ```


    根据上面测试demo封装上传工具类，由于有数据需要从配置文件中读取，所以不好封装成静态，此处选择将其注入Bean容器


    ```java
    @ConfigurationProperties(prefix = "qiniuoss")
    @Component
    @Setter
    public class UploadUtils {
        // 从配置文件中读取三个密钥与命名空间
        private String accessKey;
        private String secretKey;
        private String bucket;

        public String UploadToQiniuOSS(MultipartFile img,String filePath){
            //构造一个带指定 Region 对象的配置类
            Configuration cfg = new Configuration(Region.autoRegion());
            cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
            UploadManager uploadManager = new UploadManager(cfg);
    //默认不指定key的情况下，以文件内容的hash值作为文件名
            String key = filePath;
            try {
                InputStream inputStream= img.getInputStream();
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                try {
                    Response response = uploadManager.put(inputStream,key,upToken,null, null);
                    //解析上传成功的结果
                    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                    return Consts.QINIU_LINK+key;
                } catch (QiniuException ex) {
                    Response r = ex.response;
                    System.err.println(r.toString());
                    try {
                        System.err.println(r.bodyString());
                    } catch (QiniuException ex2) {
                        //ignore
                    }
                }
            } catch (Exception ex) {
                //ignore
            }
            return AppHttpCodeEnum.SYSTEM_ERROR.toString();
        }

    }
    ```
