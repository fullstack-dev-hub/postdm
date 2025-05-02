package com.postdm.backend.domain.auth.repository;

import com.postdm.backend.domain.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}