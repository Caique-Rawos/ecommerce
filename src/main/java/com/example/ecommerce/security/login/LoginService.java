package com.example.ecommerce.security.login;

import com.example.ecommerce.security.config.JwtConfig;
import com.example.ecommerce.security.login.dto.LoginRequest;
import com.example.ecommerce.security.login.dto.LoginResponse;
import com.example.ecommerce.security.user.permission.PermissionEntity;
import com.example.ecommerce.security.user.UserEntity;
import com.example.ecommerce.security.user.UserRepository;
import com.example.ecommerce.shared.exception.MessageException;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        Optional<UserEntity> user = userRepository.findByEmail(loginRequest.email());
        if(user.isEmpty() || !user.get().isLoginCorrect(loginRequest.toUserValidation(), passwordEncoder)){
            throw new MessageException("MENSAGEM.LOGIN.USUARIO-SENHA-INVALIDO");
        }

        Dotenv dotenv = Dotenv.load();
        Instant now = Instant.now();

        long expiresIn = Long.parseLong(dotenv.get("JWT_EXPIRES_SECONDS", "3600"));

        String scopes = user.get().getPermissons()
                .stream()
                .map(PermissionEntity::getDescricao)
                .collect(Collectors.joining(""));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(dotenv.get("NOME_API"))
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim(JwtConfig.getPermissionClaim(), scopes)
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
}
