package com.ynns.config;

import com.ynns.template.PostTemplate;
import com.ynns.template.TimeAgoMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerConfig {
    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    PostTemplate postTemplate;

    @PostConstruct
    public void setUp(){
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("posts", postTemplate);
    }
}
