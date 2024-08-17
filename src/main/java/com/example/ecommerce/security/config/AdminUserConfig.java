package com.example.ecommerce.security.config;

import com.example.ecommerce.security.user.permission.PermissionEntity;
import com.example.ecommerce.security.user.permission.PermissionRepository;
import com.example.ecommerce.security.user.UserEntity;
import com.example.ecommerce.security.user.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AdminUserConfig.class);
    private final String emailAdmin;
    private final String senhaAdmin;

    private final PermissionRepository permissionRepository;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(
            PermissionRepository permissionRepository,
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder
    ){
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        Dotenv dotenv = Dotenv.load();
        this.emailAdmin = dotenv.get("ADMIN_EMAIL");
        this.senhaAdmin = dotenv.get("ADMIN_SENHA");
    }

    @Override
    @Transactional
    public void run(String... args) {
        PermissionEntity adminPermission = permissionRepository.findByDescricao(PermissionEntity.permissions.ADMIN.name());
        Optional<UserEntity> adminUser = userRepository.findByEmail(emailAdmin);

        adminUser.ifPresentOrElse(
                user -> {
                    logger.warn("Email {} já cadastrado", emailAdmin);
                },
                () -> {
                    UserEntity user = new UserEntity(
                            emailAdmin,
                            passwordEncoder.encode(senhaAdmin),
                            Set.of(adminPermission)
                    );

                    userRepository.save(user);
                }
        );
    }


}
