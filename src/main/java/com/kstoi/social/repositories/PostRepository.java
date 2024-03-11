package com.kstoi.social.repositories;

import com.kstoi.social.models.entities.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser_UsernameIn(Collection<String> usernames, PageRequest pageRequest);
}