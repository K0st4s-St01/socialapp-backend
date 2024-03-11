package com.kstoi.social.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
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
    @Lob
    private byte[] profileImage;
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @Lob
    private List<String> friends;

}
