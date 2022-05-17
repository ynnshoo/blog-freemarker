package com.ynns.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynns.entity.MComment;
import com.ynns.entity.vo.CommentVo;
import com.ynns.mapper.MCommentMapper;
import com.ynns.service.MCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ynns
 * @since 2022-05-05
 */
@Service
public class MCommentServiceImpl extends ServiceImpl<MCommentMapper, MComment> implements MCommentService {
    @Autowired
    MCommentMapper commentMapper;

    @Override
    public IPage<CommentVo> paging(Page page, Long postId, Long userId, String order) {
        return commentMapper.selectComments(page, new QueryWrapper<MComment>()
                .eq(postId != null, "c.post_id", postId)
                .eq(userId != null, "c.user_id", userId)
                .orderByDesc(order != null, order)
        );
    }
}
