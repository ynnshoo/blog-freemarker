package com.ynns.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynns.entity.MPost;
import com.ynns.entity.vo.PostVO;
import com.ynns.mapper.MPostMapper;
import com.ynns.service.MPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.repository.query.ExampleQueryMapper;
import org.springframework.stereotype.Service;

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
}
