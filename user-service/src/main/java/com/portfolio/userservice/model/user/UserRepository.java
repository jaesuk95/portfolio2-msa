package com.portfolio.userservice.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where u.name =: username")
    Optional<UserEntity> findByUsername(@Param("username") String username);

    Optional<UserEntity> findByEmail(String username);
}
