package com.kstoi.social.mappers.impl;

import com.kstoi.social.models.dtos.SUserDto;
import com.kstoi.social.models.entities.Post;
import com.kstoi.social.models.entities.SUser;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SUserMapper  {
    public SUser toEntity(SUserDto dt, List<Post> posts) {
        return SUser.builder()
                .username(dt.getUsername())
                .email(dt.getEmail())
                .friends(dt.getFriends())
                .profileImage(Base64.decodeBase64(dt.getProfileImage()))
                .password(dt.getPassword())
                .posts(posts)
                .build();


    }

    public SUserDto toDto(SUser ent,List<Long> posts) {
        return SUserDto.builder()
                .username(ent.getUsername())
                .email(ent.getEmail())
                .friends(ent.getFriends())
                .profileImage(Base64.encodeBase64String(ent.getProfileImage()))
                .password(ent.getPassword())
                .posts(posts)
                .build();
    }
}
