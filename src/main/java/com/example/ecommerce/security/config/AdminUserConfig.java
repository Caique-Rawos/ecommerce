package com.example.ecommerce.security.config;

import com.example.ecommerce.security.permission.PermissionEntity;
import com.example.ecommerce.security.permission.PermissonRepository;
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
    private final String nomeAdmin;
    private final String emailAdmin;
    private final String senhaAdmin;

    private final PermissonRepository permissonRepository;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(
            PermissonRepository permissonRepository,
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder
    ){
        this.permissonRepository = permissonRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        Dotenv dotenv = Dotenv.load();
        this.nomeAdmin = dotenv.get("ADMINISTRADOR_NOME", "admin");
        this.emailAdmin = dotenv.get("ADMINISTRADOR_EMAIL", "admin@gmail.com");
        this.senhaAdmin = dotenv.get("ADMINISTRADOR_SENHA", "Admin123!");
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        PermissionEntity adminPermission = permissonRepository.findByDescricao(PermissionEntity.permissions.ADMIN.name());
        Optional<UserEntity> adminUser = userRepository.findByEmail(emailAdmin);

        adminUser.ifPresentOrElse(
                user -> {
                    logger.warn("Usuário {} já existe", nomeAdmin);
                },
                () -> {
                    UserEntity user = new UserEntity(
                            nomeAdmin,
                            emailAdmin,
                            passwordEncoder.encode(senhaAdmin),
                            Set.of(adminPermission)
                    );

                    userRepository.save(user);
                }
        );
    }


}
