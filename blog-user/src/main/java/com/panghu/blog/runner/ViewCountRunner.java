package com.panghu.blog.runner;

import com.panghu.blog.constant.RedisConsts;
import com.panghu.blog.domain.entity.Article;
import com.panghu.blog.service.ArticleService;
import com.panghu.blog.util.RedisCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/21 19:30
 * @description
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Resource
    ArticleService articleService;

    @Resource
    RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 重启项目会清空缓存
        // redisCache.deleteObject(RedisConsts.ARTICLE_VIEW_COUNT_KEY);
        // 查询博客信息
        List<Article> articleList = articleService.list(null);
        Map<String, Integer> viewCountMap = articleList
                .stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();
                }));
        redisCache.setCacheMap(RedisConsts.BLOG_USER_PREFIX +RedisConsts.ARTICLE_VIEW_COUNT_KEY,viewCountMap);
    }
}
