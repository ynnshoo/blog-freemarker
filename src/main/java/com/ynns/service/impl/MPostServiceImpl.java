package com.ynns.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.DatePrinter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynns.entity.MPost;
import com.ynns.entity.vo.PostVO;
import com.ynns.mapper.MPostMapper;
import com.ynns.service.MPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ynns.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.repository.query.ExampleQueryMapper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ynns
 * @since 2022-05-05
 */
@Service
public class MPostServiceImpl extends ServiceImpl<MPostMapper, MPost> implements MPostService {
    @Autowired
    MPostMapper postMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public IPage<PostVO> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order) {
        if (level == null) level = -1;

        QueryWrapper wrapper = new QueryWrapper<MPost>()
                .eq(categoryId != null, "p.category_id", categoryId)
                .eq(userId != null, "p.user_id", userId)
                .eq(level == 0, "p.level", 0)
                .gt(level >0, "p.level", 0)
                .orderByDesc(order != null, order);

        return postMapper.selectPosts(page,wrapper);

    }

    @Override
    public MPost selectOnePost(QueryWrapper<MPost> wrapper) {
        return postMapper.selectOnePost(wrapper);
    }

    /**
     * 本周热议初始化
     */
    @Override
    public void initWeekRank() {
        //获取七天的发表文章
        List<MPost> mPosts = this.list(new QueryWrapper<MPost>()
                .ge("created", DateUtil.offsetDay(new Date(), -7))
                .select("id, title, user_id, comment_count, view_count, created")
        );

        //初始化文章总评论量
        for (MPost mPost : mPosts) {
            String mPostKey = "day:rank:"+DateUtil.format(mPost.getCreated(), DatePattern.PURE_DATE_FORMAT);//day:rank:yyyyMMdd

            redisUtil.zSet(mPostKey, mPost.getId(), mPost.getCommentCount());

            //7天过期时间 7-（今-发表）
            long between = DateUtil.between(new Date(), mPost.getCreated(), DateUnit.DAY);
            long expireTime = (7 - between) * 24 * 60 * 60;

            redisUtil.expire(mPostKey, expireTime);

            //缓存文章基本信息
            this.hashCachePostIdAndTitle(mPost,expireTime);
        }

        //并集
        this.zUnionAndStoreLast7DayForWeekRank();
    }

    @Override
    public void incrCommentCountAndUnionForWeekRank(long postId, boolean isIncr) {
        String  currentKey = "day:rank:" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
        redisUtil.zIncrementScore(currentKey, postId, isIncr? 1: -1);

        MPost post = this.getById(postId);

        // 7天后自动过期(15号发表，7-（18-15）=4)
        long between = DateUtil.between(new Date(), post.getCreated(), DateUnit.DAY);
        long expireTime = (7 - between) * 24 * 60 * 60; // 有效时间

        // 缓存这篇文章的基本信息
        this.hashCachePostIdAndTitle(post, expireTime);

        // 重新做并集
        this.zUnionAndStoreLast7DayForWeekRank();
    }

    /**
     * 本周合并每日评论数量并集操作
     */
    private void zUnionAndStoreLast7DayForWeekRank() {
        String currentKey = "day:rank:"+DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
        String destKey = "week:rank";
        List<String> otherKeys = new ArrayList<>();

        for (int i = -6; i < 0; i++){
            String temp = "day:rank:" +
                    DateUtil.format(DateUtil.offsetDay(new Date(), i), DatePattern.PURE_DATE_FORMAT);

            otherKeys.add(temp);
        }
        //zet合并操作
        redisUtil.zUnionAndStore(currentKey, otherKeys, destKey);
    }

    private void hashCachePostIdAndTitle(MPost mPost, long expireTime) {
        String cacheKey = "rank:post:" + mPost.getId();//缓存据今七天post部分内容数据

        boolean hashKey = redisUtil.hasKey(cacheKey);

        if (!hashKey){
            redisUtil.hset(cacheKey, "post:id", mPost.getId(), expireTime);
            redisUtil.hset(cacheKey, "post:title", mPost.getTitle(), expireTime);
            redisUtil.hset(cacheKey, "post:commentCount", mPost.getCommentCount(), expireTime);
        }
    }
}
