package com.example.ecommerce.security.user;

import com.example.ecommerce.cliente.ClienteEntity;
import com.example.ecommerce.security.user.dto.UserValidation;
import com.example.ecommerce.security.user.permission.PermissionEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ClienteEntity cliente;

    public UserEntity() {
    }

    public UserEntity(String email, String senha, Set<PermissionEntity> permissons) {
        this.email = email;
        this.senha = senha;
        this.permissons = permissons;
    }

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public boolean isLoginCorrect(UserValidation userValidation, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(userValidation.senha(), this.senha);
    }
}
