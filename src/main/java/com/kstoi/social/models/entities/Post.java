package com.kstoi.social.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    private Long id;
    @Column(columnDefinition = "BLOB")
    private byte[] image;
    private Date date;
    @Column(columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user")
    private SUser user;
}
