package com.ynns.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynns.service.MCommentService;
import com.ynns.service.MPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    MPostService postService;

    @Autowired
    MCommentService commentService;

    public Page getPage(){
        //分页
        int pageCurrent = ServletRequestUtils.getIntParameter(httpServletRequest, "page", 1);
        int pageSize = ServletRequestUtils.getIntParameter(httpServletRequest, "size", 2);
//        int total = 6;
        return new Page(pageCurrent,pageSize);
    }
}
