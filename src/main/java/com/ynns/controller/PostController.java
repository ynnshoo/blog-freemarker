package com.ynns.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ynns.entity.MPost;
import com.ynns.entity.vo.CommentVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("post")
@Controller
public class PostController extends BaseController{
    @RequestMapping("category/{id:\\d*}")
    public String category(@PathVariable("id") Long id){
        int pageCurrent = ServletRequestUtils.getIntParameter(httpServletRequest, "page", 1);

        httpServletRequest.setAttribute("currentCategoryId",id);
        httpServletRequest.setAttribute("page",pageCurrent);
        return "post/category";
    }

    @RequestMapping("detail/{id:\\d*}")
    public String detail(@PathVariable("id") Long id){
        MPost postVo= postService.selectOnePost(new QueryWrapper<MPost>().eq("p.id",id));

        Assert.notNull(postVo,"文章不存在！！！");

        //1分页 2文章ID 3用户ID 4排序
        IPage<CommentVo> results = commentService.paging(getPage(),postVo.getId(),null,"created");

        httpServletRequest.setAttribute("currentCategoryId", postVo.getCategoryId());
        httpServletRequest.setAttribute("post", postVo);
        httpServletRequest.setAttribute("pagePostData", results);

        return "post/detail";
    }
}
