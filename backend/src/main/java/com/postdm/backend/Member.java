package com.postdm.backend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder
    public Member(String username, String password, String email, MemberRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
