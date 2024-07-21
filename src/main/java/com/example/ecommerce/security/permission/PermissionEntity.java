package com.example.ecommerce.security.permission;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "permissions")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    public enum permissions {
        ADMIN(1L),
        BASIC(2L);

        long idPermission;

        permissions( long idPermission){
            this.idPermission = idPermission;
        }

        public long getIdPermission() {
            return idPermission;
        }
    }
}
