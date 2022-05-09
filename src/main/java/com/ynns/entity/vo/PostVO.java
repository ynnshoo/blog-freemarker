package com.ynns.entity.vo;

import com.ynns.entity.MPost;
import lombok.Data;

@Data
public class PostVO extends MPost {
    private Long authorId;
    private String authorName;
    private String authorAvatar;

    private String categoryName;
}
