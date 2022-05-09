package com.ynns.controller;

import com.ynns.service.MPostService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    MPostService postService;
}
