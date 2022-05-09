package com.ynns.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynns.entity.vo.PostVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{
    @RequestMapping({"", "/", "index"})
    public String index(){
        //分页
        int pageCurrent = ServletRequestUtils.getIntParameter(httpServletRequest, "page", 1);
        int pageSize = ServletRequestUtils.getIntParameter(httpServletRequest, "size", 2);
        Page page = new Page(pageCurrent,pageSize);
        //参数：1 分页信息 2 分类 3 用户 4 置顶 5 精选 6 排序
        IPage<PostVO> results = postService.paging(page,null,null,null,null,"created");

        //首页为高亮
        httpServletRequest.setAttribute("currentCategoryId",0);
        httpServletRequest.setAttribute("pageDetail", results);
        return "index";
    }
}
