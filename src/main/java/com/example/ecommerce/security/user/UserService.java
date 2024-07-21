package com.example.ecommerce.security.user;

import com.example.ecommerce.security.permission.PermissionEntity;
import com.example.ecommerce.security.permission.PermissonRepository;
import com.example.ecommerce.security.user.dto.UserEntityDto;
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
    private final PermissonRepository permissonRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void create(UserEntityDto dto) {
        this.verifyDto(dto);

        PermissionEntity basicPermission = permissonRepository.findByDescricao(PermissionEntity.permissions.BASIC.name());
        UserEntity user = new UserEntity(
                dto.nome(),
                dto.email(),
                passwordEncoder.encode(dto.senha()),
                Set.of(basicPermission)
        );

        userRepository.save(user);
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
