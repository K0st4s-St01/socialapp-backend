package com.kstoi.social.repositories;

import com.kstoi.social.models.entities.SUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SUserRepository extends JpaRepository<SUser, String> {
    List<SUser> findByUsernameNotIn(Collection<String> usernames);
}