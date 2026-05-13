package com.mamokey.yeoun.domain.user.entity;

import com.mamokey.yeoun.global.entity.GlobalEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends GlobalEntity {

    @Column(name = "email" , nullable = false)
    private String email;

    @Column(name = "password_hash" , nullable = false)
    private String password;
}
