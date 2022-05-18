package com.ynns.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynns.entity.MPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ynns.entity.vo.PostVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ynns
 * @since 2022-05-05
 */
public interface MPostService extends IService<MPost> {

    /**
     * @param page 分页信息
     * @param categoryId 分类
     * @param userId 用户
     * @param level 置顶
     * @param recommend 精选
     * @param order 排序:日期
     * @return
     */
    IPage<PostVO> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order);

    MPost selectOnePost(QueryWrapper<MPost> wrapper);

    //缓存本周热议
    void initWeekRank();

    //更新本周热议
    void incrCommentCountAndUnionForWeekRank(long postId, boolean isIncr);
}
