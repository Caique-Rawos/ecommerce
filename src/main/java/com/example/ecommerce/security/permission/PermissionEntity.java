package com.example.ecommerce.security.permission;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "permission")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

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
