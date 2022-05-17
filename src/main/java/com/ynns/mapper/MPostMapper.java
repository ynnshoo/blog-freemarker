package com.ynns.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynns.entity.MPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ynns.entity.vo.PostVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ynns
 * @since 2022-05-05
 */
@Repository
public interface MPostMapper extends BaseMapper<MPost> {

    IPage<PostVO> selectPosts(Page page, @Param(Constants.WRAPPER) QueryWrapper wrapper);

    MPost selectOnePost(@Param(Constants.WRAPPER) QueryWrapper<MPost> wrapper);
}
