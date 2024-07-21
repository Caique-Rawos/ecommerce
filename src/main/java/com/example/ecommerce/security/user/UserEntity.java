package com.example.ecommerce.security.user;

import com.example.ecommerce.security.permission.PermissionEntity;
import com.example.ecommerce.security.user.dto.UserEntityDto;
import com.example.ecommerce.security.user.dto.UserValidation;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_permissions",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_permission")
    )
    private Set<PermissionEntity> permissons;

    public boolean isLoginCorrect(UserValidation userValidation, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(userValidation.senha(), this.senha);
    }

    public UserEntity() {
    }

    public UserEntity(String nome, String email, String senha, Set<PermissionEntity> permissons) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.permissons = permissons;
    }

    public UserEntity(UserEntityDto dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.senha = dto.senha();
    }
}
