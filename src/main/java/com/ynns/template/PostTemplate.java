package com.ynns.template;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynns.common.templates.DirectiveHandler;
import com.ynns.common.templates.TemplateDirective;
import com.ynns.entity.vo.PostVO;
import com.ynns.service.MPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostTemplate extends TemplateDirective {
    @Autowired
    MPostService postService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer level = handler.getInteger("level");
        Integer page = handler.getInteger("page",1);
        Integer size = handler.getInteger("size",2);
        Long categoryId = handler.getLong("categoryId");

        IPage<PostVO> pageInfo = postService.paging(new Page(page, size), categoryId, null, level, null, "created");

        handler.put(RESULTS,pageInfo).render();
    }
}
