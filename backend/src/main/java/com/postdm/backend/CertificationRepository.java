package com.postdm.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<CertificationEntity, String> {

    CertificationEntity findByUsername(String username);

}
