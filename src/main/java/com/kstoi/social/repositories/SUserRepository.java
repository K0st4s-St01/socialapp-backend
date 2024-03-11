package com.kstoi.social.repositories;

import com.kstoi.social.models.entities.SUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SUserRepository extends JpaRepository<SUser, String> {
}