package com.ynns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("post")
@Controller
public class PostController extends BaseController{
    @RequestMapping("category/{id:\\d*}")
    public String category(@PathVariable("id") Long id){
        httpServletRequest.setAttribute("currentCategoryId",id);
        return "post/category";
    }

    @RequestMapping("detail/{id:\\d*}")
    public String detail(@PathVariable("id") Long id){
        return "post/detail";
    }
}
