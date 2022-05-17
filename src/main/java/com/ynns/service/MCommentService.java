package com.ynns.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynns.entity.MComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ynns.entity.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ynns
 * @since 2022-05-05
 */
public interface MCommentService extends IService<MComment> {

    IPage<CommentVo> paging(Page page, Long postId, Long userId, String order);
}
