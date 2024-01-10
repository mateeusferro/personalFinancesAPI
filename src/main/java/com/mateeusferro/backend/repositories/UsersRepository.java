package com.mateeusferro.backend.repositories;

import com.mateeusferro.backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsersRepository extends JpaRepository<Users, Long> {
    UserDetails findByEmail(String email);
}
