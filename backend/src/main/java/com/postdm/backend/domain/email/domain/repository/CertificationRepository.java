package com.postdm.backend.domain.email.domain.repository;

import com.postdm.backend.domain.email.domain.entity.CertificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<CertificationEntity, String> {

    CertificationEntity findByUsername(String username);

}
