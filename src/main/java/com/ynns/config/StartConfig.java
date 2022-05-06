package com.ynns.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ynns.entity.MCategory;
import com.ynns.service.MCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;

//启动项目自动执行
@Component
public class StartConfig implements ApplicationRunner, ServletContextAware {

    @Autowired
    MCategoryService categoryService;

    @Autowired
    ServletContext serveltContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<MCategory> categoryList = categoryService.list(new QueryWrapper<MCategory>()
                .eq("status", 0) //eq 条件；state:0 上线
        );

        serveltContext.setAttribute("categorys",categoryList);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.serveltContext=serveltContext;
    }
}
