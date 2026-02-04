package com.gonet.api_validator.repository;

import com.gonet.api_validator.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsuario(String usuario);
}