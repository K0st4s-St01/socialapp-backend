package com.kstoi.social.models.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SUserDto {
    private String username;
    private String password;
    private String email;
    private String profileImage;
    private List<Long> posts = new ArrayList<>();

    private List<String> friends;

}
