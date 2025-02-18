package com.postdm.backend.domain.email.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CertificationEntity { // 인증 메일 엔티티

    @Id
    private String username;

    private String email;

    private String certificationNumber;
}
