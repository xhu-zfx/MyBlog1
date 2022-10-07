package com.panghu.blog.job;

import com.panghu.blog.constant.RedisConsts;
import com.panghu.blog.domain.entity.Article;
import com.panghu.blog.service.ArticleService;
import com.panghu.blog.util.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/22 12:08
 * @description
 */
@Component
public class UpdataViewCountJob {

    @Resource
    RedisCache redisCache;

    @Resource
    ArticleService articleService;

    // 每隔10分钟执行一次
    @Scheduled(cron = "0 */10 * * * ?")
    // 每隔5秒执行一次
//    @Scheduled(cron = "0/5 * * * * ?")
    public void UpdataViewCount(){
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(RedisConsts.BLOG_USER_PREFIX + RedisConsts.ARTICLE_VIEW_COUNT_KEY);
        List<Article> articleList = viewCountMap
                .entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        articleService.updateBatchById(articleList);
    }
}
