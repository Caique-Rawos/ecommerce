package com.example.ecommerce.security.user;

import com.example.ecommerce.security.user.dto.UserEntityDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Tag(name = "Usuario")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<Void> createBasicUser(@RequestBody @Valid UserEntityDto dto) {
         userService.createBasic(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin")
    @PreAuthorize("hasAuthority(T(com.example.ecommerce.security.config.JwtConfig).getAdminScope())")
    public ResponseEntity<Void> createAdminUser(@RequestBody @Valid UserEntityDto dto) {
        userService.createAdmin(dto);
        return ResponseEntity.ok().build();
    }
}
