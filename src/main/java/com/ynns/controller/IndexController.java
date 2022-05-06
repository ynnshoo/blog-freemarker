package com.ynns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{
    @RequestMapping({"", "/", "index"})
    public String index(){
        httpServletRequest.setAttribute("currentCategoryId",0);
        return "index";
    }
}
