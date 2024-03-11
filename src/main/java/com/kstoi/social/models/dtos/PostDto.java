package com.kstoi.social.models.dtos;

import lombok.*;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private String image;
    private Date date;
    private String text;

    private String user;
}
