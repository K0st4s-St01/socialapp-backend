package com.kstoi.social.mappers.impl;

import com.kstoi.social.models.dtos.PostDto;
import com.kstoi.social.models.entities.Post;
import com.kstoi.social.models.entities.Post;
import com.kstoi.social.models.entities.SUser;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PostMapper {
    public Post toEntity(PostDto dt, SUser user) {
        return Post.builder()
                .id(dt.getId()!=null?dt.getId():null)
                .image(Base64.decodeBase64(dt.getImage()))
                .user(user)
                .text(dt.getText())
                .date(dt.getDate())
                .build();


    }

    public PostDto toDto(Post ent,String user) {
        return PostDto.builder()
                .id(ent.getId()!=null?ent.getId():null)
                .image(Base64.encodeBase64String(ent.getImage()))
                .user(user)
                .text(ent.getText())
                .date(ent.getDate())
                .build();
    }
}
