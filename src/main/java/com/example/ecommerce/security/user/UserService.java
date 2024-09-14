package com.example.ecommerce.security.user;

import com.example.ecommerce.security.user.dto.UserEntityDto;
import com.example.ecommerce.security.user.permission.PermissionEntity;
import com.example.ecommerce.security.user.permission.PermissionRepository;
import com.example.ecommerce.shared.exception.MessageException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private void create(UserEntityDto dto, PermissionEntity permission) {
        this.verifyDto(dto);

        UserEntity user = new UserEntity(
                dto.email(),
                passwordEncoder.encode(dto.senha()),
                Set.of(permission)
        );

        userRepository.save(user);
    }

    public UserEntity getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new MessageException("MENSAGEM.USER.NAO-ENCONTRADO"));
    }

    public void createBasic(UserEntityDto dto) {
        PermissionEntity basicPermission = permissionRepository.findByDescricao(PermissionEntity.permissions.BASIC.name());
        this.create(dto, basicPermission);
    }

    public void createAdmin(UserEntityDto dto) {
        PermissionEntity adminPermission = permissionRepository.findByDescricao(PermissionEntity.permissions.ADMIN.name());
        this.create(dto, adminPermission);
    }

    private void verifyDto(UserEntityDto dto) {
        this.verifyEmail(dto.email(), dto.id());
    }

    private void verifyEmail(String email, UUID id) {
        this.verifyEmailIsRegistered(email, id);
        this.verifyEmailIsValid(email);
    }

    private void verifyEmailIsRegistered(String email, UUID id) {
        Optional<UserEntity> userOptional;

        if (id != null) {
            userOptional = userRepository.findByEmailAndIdNot(email, id);
        } else {
            userOptional = userRepository.findByEmail(email);
        }

        if (userOptional.isPresent()) {
            throw new MessageException("MENSAGEM.USER.EMAIL-JA-CADASTRADO");
        }
    }

    private void verifyEmailIsValid(String email) {
        EmailValidator validator = EmailValidator.getInstance();

        if (!validator.isValid(email)) {
            throw new MessageException("MENSAGEM.USER.EMAIL-INVALIDO");
        }
    }
}
