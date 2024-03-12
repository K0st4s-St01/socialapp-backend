package com.kstoi.social.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SUser {
    @Id
    private String username;
    private String password;
    private String email;
    @Column(columnDefinition = "BLOB")
    private byte[] profileImage;
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @Lob
    private List<String> friends;

}
